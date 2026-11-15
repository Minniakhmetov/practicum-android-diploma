package ru.practicum.android.diploma.search.data.dto

data class VacancyResponseDto(
    val found: Int,
    val pages: Int,
    val page: Int,
    val items: List<VacancyDto>
)
