package ru.practicum.android.diploma.search.data.mapper

import ru.practicum.android.diploma.search.data.dto.VacancyResponseDto
import ru.practicum.android.diploma.search.domain.model.VacanciesPage

internal fun VacancyResponseDto.toDomain(): VacanciesPage {
    return VacanciesPage(
        items = items.map { it.toDomain() },
        page = page,
        pages = pages,
        found = found
    )
}
