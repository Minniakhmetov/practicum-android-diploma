package ru.practicum.android.diploma.search.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

    // Отдельные Job: первая страница/новый query vs следующая страница.
    private var searchJob: Job? = null
    private var nextPageJob: Job? = null
    private var currentPage: Int = FIRST_PAGE

    // Фильтр будет реализовать в Epic_4
    private var currentSettings: FilterSettings = FilterSettings()

    private val searchDebounce = Debounce<String>(
        delayMillis = SEARCH_DEBOUNCE_DELAY,
        scope = viewModelScope
    ) { query ->
        performSearch(query, currentSettings, FIRST_PAGE)
    }

    // Ввод текста
    fun onQueryChanged(query: String) {
        _state.update { it.copy(query = query) }
        if (query.isBlank()) {
            searchDebounce.cancel()
            cancelAllJobs()
            resetPaging()
            _state.value = SearchState(query = "")
            return
        }
        cancelAllJobs()
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

    // Для пагинации (реализация в #54)
    fun onLoadNextPage() {
        val s = _state.value
        val content = s.result as? SearchResultState.Content
        val canLoadNext = content != null &&
            s.query.isNotBlank() &&
            !content.isNextPageLoading &&
            content.hasMore

        if (canLoadNext) {
            performSearch(s.query, currentSettings, currentPage + 1)
        }
    }

    // Перезапуск
    private fun restartSearch(query: String) {
        searchDebounce.cancel()
        cancelAllJobs()
        // resetPaging выполнится внутри performSearch для page == FIRST_PAGE
        performSearch(query, currentSettings, FIRST_PAGE)
    }

    private fun performSearch(query: String, settings: FilterSettings, page: Int) {
        if (page == FIRST_PAGE) {
            resetPaging()
            searchJob?.cancel()
            searchJob = viewModelScope.launch {
                setFirstPageLoading()
                val result = searchInteractor.searchVacancies(query, settings, page)
                handleResult(result, page)
            }
        } else {
            nextPageJob?.cancel()
            nextPageJob = viewModelScope.launch {
                setNextPageLoading()
                val result = searchInteractor.searchVacancies(query, settings, page)
                handleResult(result, page)
            }
        }
    }

    private fun handleResult(result: Resource<VacanciesPage>, page: Int) {
        when (result) {
            is Resource.Success -> handleSuccess(result.data, page)
            is Resource.Error -> handleError(result.type, page)
        }
    }

    private fun handleSuccess(pageData: VacanciesPage, page: Int) {
        currentPage = page
        val newHasMore = computeHasMore(pageData, page)
        val uiItems = pageData.items.map(::toUiItem)
        updateContentState(pageData, page, uiItems, newHasMore)
    }

    private fun computeHasMore(pageData: VacanciesPage, page: Int): Boolean =
        pageData.page < pageData.pages &&
            !(pageData.items.isEmpty() && page > FIRST_PAGE)

    private fun updateContentState(
        pageData: VacanciesPage,
        page: Int,
        uiItems: List<VacancyShortItem>,
        newHasMore: Boolean,
    ) {
        _state.update { state ->
            val result = state.result
            val newResult = if (result is SearchResultState.Content) {
                result.copy(
                    vacancies = mergeItems(result.vacancies, uiItems, page),
                    found = pageData.found,
                    isNextPageLoading = false,
                    hasMore = newHasMore,
                )
            } else {
                createInitialContent(uiItems, pageData.found, newHasMore)
            }
            state.copy(result = newResult)
        }
    }

    private fun mergeItems(
        existing: List<VacancyShortItem>,
        new: List<VacancyShortItem>,
        page: Int,
    ): List<VacancyShortItem> =
        if (page == FIRST_PAGE) new else existing + new

    private fun createInitialContent(
        uiItems: List<VacancyShortItem>,
        found: Int,
        hasMore: Boolean,
    ): SearchResultState =
        if (uiItems.isEmpty()) {
            SearchResultState.Error(ErrorType.NotFound)
        } else {
            SearchResultState.Content(
                vacancies = uiItems,
                found = found,
                isNextPageLoading = false,
                hasMore = hasMore,
            )
        }

    private fun handleError(error: ErrorType, page: Int) {
        if (page == FIRST_PAGE) {
            _state.update {
                it.copy(result = SearchResultState.Error(error))
            }
        } else {
            // Пагинация: ошибку не показываем, просто убираем футер-лоадер.
            // Ошибка в Toast будет добавлена в #82
            _state.update { state ->
                val result = state.result
                if (result is SearchResultState.Content) {
                    state.copy(result = result.copy(isNextPageLoading = false))
                } else {
                    state
                }
            }
        }
    }

    private fun setFirstPageLoading() {
        _state.update {
            it.copy(result = SearchResultState.Loading)
        }
    }

    private fun setNextPageLoading() {
        _state.update { state ->
            val result = state.result
            if (result is SearchResultState.Content) {
                state.copy(result = result.copy(isNextPageLoading = true))
            } else {
                state
            }
        }
    }

    private fun cancelAllJobs() {
        searchJob?.cancel()
        nextPageJob?.cancel()
    }

    private fun resetPaging() {
        currentPage = FIRST_PAGE
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
    }
}
