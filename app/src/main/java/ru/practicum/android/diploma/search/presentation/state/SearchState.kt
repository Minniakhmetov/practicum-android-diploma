package ru.practicum.android.diploma.search.presentation.state

import ru.practicum.android.diploma.core.common.result.ErrorType
import ru.practicum.android.diploma.core.domain.model.VacancyShort

data class SearchState(
    val query: String = "",
    val result: SearchResultState = SearchResultState.Initial,
)

sealed interface SearchResultState {
    // Ещё не искали (стартовый экран)
    data object Initial : SearchResultState

    // Первичная загрузка: список пуст, показываем полноэкранную крутилку
    data object Loading : SearchResultState

    // Есть данные
    data class Content(
        val vacancies: List<VacancyShortItem>,
        val found: Int,
        val isNextPageLoading: Boolean = false,
        val hasMore: Boolean = true
    ) : SearchResultState

    // Пустой результат запроса
    data object Empty : SearchResultState

    // Полноэкранная ошибка: списка нет
    data class Error(val type: ErrorType) : SearchResultState
}

// salary - готовая строка из SalaryFormatter: экран не форматирует данные сам
data class VacancyShortItem(
    val vacancy: VacancyShort,
    val salary: String
)
