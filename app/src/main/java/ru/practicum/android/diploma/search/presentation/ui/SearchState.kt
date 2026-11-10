package com.example.sprint28.app

import ru.practicum.android.diploma.core.common.result.ErrorType
import ru.practicum.android.diploma.core.domain.model.VacancyShort

data class SearchState(
    val query: String = "",
    val items: List<VacancyShort> = emptyList(),
    val found: Int = 0,
    val isLoading: Boolean = false,
    val isNextPageLoading: Boolean = false,
    val placeholder: ErrorType? = null
)
