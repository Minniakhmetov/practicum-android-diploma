package ru.practicum.android.diploma.search.domain.model

import ru.practicum.android.diploma.core.domain.model.VacancyShort

data class VacanciesPage(
    val items: List<VacancyShort>,
    val page: Int,
    val pages: Int,
    val found: Int
)
