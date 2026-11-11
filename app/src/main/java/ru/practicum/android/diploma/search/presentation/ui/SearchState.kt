package ru.practicum.android.diploma.search.presentation.ui

import ru.practicum.android.diploma.core.common.result.ErrorType
import ru.practicum.android.diploma.core.domain.model.VacancyShort

data class SearchState(
    val query: String = "",
    val items: List<VacancyShort> = emptyList(),
    val found: Int = 0,
    val isLoading: Boolean = false,
    val isNextPageLoading: Boolean = false,
    /**
     * placeholder:
     *  - null        — данные есть или идёт загрузка;
     *  - NotFound    — успешный ответ с пустым списком (только при items.isEmpty());
     *  - NoInternet/Server — если items непусты, это ошибка загрузки СЛЕДУЮЩЕЙ страницы (показывать внизу списка, не вместо него).
     */
    val placeholder: ErrorType? = null
)
