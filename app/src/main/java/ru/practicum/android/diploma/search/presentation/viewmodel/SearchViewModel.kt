package ru.practicum.android.diploma.search.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.core.common.result.ErrorType
import ru.practicum.android.diploma.core.common.result.Resource
import ru.practicum.android.diploma.core.common.util.Debounce
import ru.practicum.android.diploma.core.domain.model.FilterSettings
import ru.practicum.android.diploma.search.domain.interactor.SearchInteractor
import ru.practicum.android.diploma.search.domain.model.VacanciesPage
import ru.practicum.android.diploma.search.presentation.ui.SearchState

class SearchViewModel(
    private val searchInteractor: SearchInteractor,
) : ViewModel() {
    private val _state = MutableStateFlow(SearchState())
    val state: StateFlow<SearchState> = _state.asStateFlow()

    // Отдельные Job: первая страница/новый query vs следующая страница.
    private var searchJob: Job? = null
    private var nextPageJob: Job? = null
    private var currentPage: Int = FIRST_PAGE
    private var hasMore: Boolean = false

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
            _state.update { SearchState() }
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
        val state = _state.value
        val canLoadNext =
            state.query.isNotBlank() &&
                !state.isLoading &&
                !state.isNextPageLoading &&
                hasMore

        if (canLoadNext) {
            performSearch(state.query, currentSettings, currentPage + 1)
        }
    }

    // Перезапуск
    private fun restartSearch(query: String) {
        searchDebounce.cancel()
        cancelAllJobs()
        // resetPaging выполнится внутри performSearch для page == FIRST_PAGE
        performSearch(query, currentSettings, FIRST_PAGE)
    }

    // Основная фуекция для поиска
    private fun performSearch(query: String, settings: FilterSettings, page: Int) {
        if (page == FIRST_PAGE) {
            resetPaging()
            searchJob?.cancel()
            searchJob = viewModelScope.launch {
                setLoadingState()
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

    private fun handleSuccess(
        result: VacanciesPage,
        page: Int
    ) {
        currentPage = page
        hasMore = result.page < result.pages
        if (result.items.isEmpty() && page > FIRST_PAGE) {
            hasMore = false
        }

        _state.update {
            val newItems = if (page == FIRST_PAGE) {
                // первая страница заменяет
                result.items
            } else {
                // следующая дополняет
                it.items + result.items
            }
            it.copy(
                items = newItems,
                found = result.found,
                isLoading = false,
                isNextPageLoading = false,
                placeholder = if (page == FIRST_PAGE && newItems.isEmpty()) {
                    ErrorType.NotFound
                } else {
                    null
                }
            )
        }
    }

    private fun handleError(
        error: ErrorType,
        page: Int
    ) {
        if (page == FIRST_PAGE) {
            _state.update {
                it.copy(
                    items = emptyList(),
                    found = 0,
                    isLoading = false,
                    isNextPageLoading = false,
                    placeholder = error
                )
            }
        } else {
            // Если следующая страница, то список не чистим
            _state.update {
                it.copy(
                    isNextPageLoading = false,
                    placeholder = error
                )
            }
        }
    }

    private fun setLoadingState() {
        _state.update {
            it.copy(
                items = emptyList(),
                found = 0,
                isLoading = true,
                isNextPageLoading = false,
                placeholder = null
            )
        }
    }

    private fun setNextPageLoading() {
        _state.update {
            it.copy(isNextPageLoading = true, placeholder = null)
        }
    }

    private fun cancelAllJobs() {
        searchJob?.cancel()
        nextPageJob?.cancel()
    }

    private fun resetPaging() {
        currentPage = FIRST_PAGE
        hasMore = false
    }

    override fun onCleared() {
        searchDebounce.cancel()
        cancelAllJobs()
        super.onCleared()
    }

    private companion object {
        const val SEARCH_DEBOUNCE_DELAY = 2_000L
        const val FIRST_PAGE = 1
    }
}
