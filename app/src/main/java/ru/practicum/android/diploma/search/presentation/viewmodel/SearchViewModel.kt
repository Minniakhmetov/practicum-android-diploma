package ru.practicum.android.diploma.search.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.common.format.SalaryFormatter
import ru.practicum.android.diploma.core.common.result.ErrorType
import ru.practicum.android.diploma.core.common.result.Resource
import ru.practicum.android.diploma.core.common.util.Debounce
import ru.practicum.android.diploma.core.domain.model.FilterSettings
import ru.practicum.android.diploma.core.domain.model.VacancyShort
import ru.practicum.android.diploma.search.domain.interactor.SearchInteractor
import ru.practicum.android.diploma.search.domain.model.VacanciesPage
import ru.practicum.android.diploma.search.presentation.state.SearchResultState
import ru.practicum.android.diploma.search.presentation.state.SearchState
import ru.practicum.android.diploma.search.presentation.state.VacancyShortItem

class SearchViewModel(
    private val searchInteractor: SearchInteractor,
    private val salaryFormatter: SalaryFormatter
) : ViewModel() {
    private val _state = MutableStateFlow(SearchState())
    val state: StateFlow<SearchState> = _state.asStateFlow()

    // Одноразовое событие ошибки следующей страницы (Toast на экране)
    private val _paginationError = MutableSharedFlow<ErrorType>(extraBufferCapacity = 1)
    val paginationError: SharedFlow<ErrorType> = _paginationError.asSharedFlow()
    private var searchJob: Job? = null

    // Фильтр будет реализовать в Epic_4
    private var currentSettings: FilterSettings = FilterSettings()
    private var currentPage: Int = FIRST_PAGE
    private var maxPages: Int = DEFAULT_MAX_PAGES

    private val searchDebounce = Debounce<String>(
        delayMillis = SEARCH_DEBOUNCE_DELAY,
        scope = viewModelScope
    ) { query ->
        performSearch(query, currentSettings, FIRST_PAGE)
    }

    // Ввод текста
    fun onQueryChanged(query: String) {
        cancelAllJobs()
        _state.update { it.copy(query = query) }
        resetPagination()
        if (query.isBlank()) {
            searchDebounce.cancel()
            _state.update {
                it.copy(query = query, result = SearchResultState.Initial)
            }
            return
        }
        searchDebounce.start(query)
    }

    // Кнопка поиска
    fun onSearchClicked() {
        val query = _state.value.query
        if (query.isBlank()) return
        restartSearch(query)
    }

    // Применены фильтры. Если запрос пустой то не ищем
    fun onFiltersApplied() {
        val query = _state.value.query
        if (query.isBlank()) return
        restartSearch(query)
    }

    // Доскролл до последнего элемента списка. Многократные вызовы защищены флагом isNextPageLoading
    fun onLastItemReached() {
        val content = _state.value.result as? SearchResultState.Content
        val query = _state.value.query
        val canLoadNextPage = content != null &&
            !content.isNextPageLoading &&
            content.hasMore &&
            currentPage < maxPages &&
            query.isNotBlank()

        if (canLoadNextPage) {
            loadNextPage(query, currentSettings)
        }
    }

    // Перезапуск
    private fun restartSearch(query: String) {
        searchDebounce.cancel()
        cancelAllJobs()
        resetPagination()
        performSearch(query, currentSettings, FIRST_PAGE)
    }

    private fun performSearch(query: String, settings: FilterSettings, page: Int) {
        cancelAllJobs()
        setFirstPageLoading()
        searchJob = viewModelScope.launch {
            val result = searchInteractor.searchVacancies(query, settings, page)
            if (isQueryStillActual(query)) {
                handleResult(result, page)
            }
        }
    }

    // Загрузка следующей страницы: список не сбрасываем, показываем индикатор внизу
    private fun loadNextPage(query: String, settings: FilterSettings) {
        val nextPage = currentPage + 1
        setNextPageLoading(true)
        searchJob = viewModelScope.launch {
            val result = searchInteractor.searchVacancies(query, settings, nextPage)
            if (isQueryStillActual(query)) {
                when (result) {
                    is Resource.Success -> handleSuccess(result.data, nextPage)
                    is Resource.Error -> handleNextPageError(result.type)
                }
            }
        }
    }

    private fun isQueryStillActual(query: String): Boolean = query == _state.value.query

    private fun handleResult(result: Resource<VacanciesPage>, page: Int) {
        when (result) {
            is Resource.Success -> handleSuccess(result.data, page)
            is Resource.Error -> handleError(result.type)
        }
    }

    private fun handleSuccess(pageData: VacanciesPage, page: Int) {
        currentPage = page
        maxPages = pageData.pages

        val uiItems = pageData.items.map(::toUiItem)
        val newHasMore = computeHasMore(pageData, page)

        _state.update { state ->
            val content = state.result as? SearchResultState.Content
            val newResult = if (page == FIRST_PAGE || content == null) {
                createInitialContent(uiItems, pageData.found, newHasMore)
            } else {
                content.copy(
                    vacancies = (content.vacancies + uiItems).distinctBy { it.vacancy.id },
                    found = pageData.found,
                    isNextPageLoading = false,
                    hasMore = newHasMore,
                )
            }
            state.copy(result = newResult)
        }
    }

    private fun handleNextPageError(error: ErrorType) {
        // Снимаем индикатор, currentPage/maxPages не трогаем — при следующем доскролле
        // та же страница будет запрошена снова. Список остаётся на экране.
        setNextPageLoading(false)
        _paginationError.tryEmit(error)
    }

    private fun computeHasMore(pageData: VacanciesPage, page: Int): Boolean =
        pageData.page < pageData.pages &&
            !(pageData.items.isEmpty() && page > FIRST_PAGE)

    private fun createInitialContent(
        uiItems: List<VacancyShortItem>,
        found: Int,
        hasMore: Boolean,
    ): SearchResultState =
        if (uiItems.isEmpty()) {
            SearchResultState.Empty
        } else {
            SearchResultState.Content(
                vacancies = uiItems,
                found = found,
                isNextPageLoading = false,
                hasMore = hasMore,
            )
        }

    private fun handleError(error: ErrorType) {
        _state.update {
            it.copy(result = SearchResultState.Error(error))
        }
    }

    private fun setFirstPageLoading() {
        _state.update {
            it.copy(result = SearchResultState.Loading)
        }
    }

    private fun setNextPageLoading(loading: Boolean) {
        _state.update { state ->
            val content = state.result as? SearchResultState.Content
            if (content == null) {
                state
            } else {
                state.copy(result = content.copy(isNextPageLoading = loading))
            }
        }
    }

    private fun resetPagination() {
        currentPage = FIRST_PAGE
        maxPages = DEFAULT_MAX_PAGES
    }

    private fun cancelAllJobs() {
        searchJob?.cancel()
    }

    override fun onCleared() {
        searchDebounce.cancel()
        cancelAllJobs()
        super.onCleared()
    }

    private fun toUiItem(vacancy: VacancyShort): VacancyShortItem =
        VacancyShortItem(
            vacancy = vacancy,
            salary = salaryFormatter.format(vacancy.salary, R.string.salary_not_specified),
        )

    private companion object {
        const val SEARCH_DEBOUNCE_DELAY = 2_000L
        const val FIRST_PAGE = 1
        const val DEFAULT_MAX_PAGES = 1
    }
}
