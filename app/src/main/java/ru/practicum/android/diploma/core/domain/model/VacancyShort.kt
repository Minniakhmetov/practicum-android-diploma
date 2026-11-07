package ru.practicum.android.diploma.core.domain.model

data class VacancyShort(
    val id: String,
    val name: String,
    val employerName: String?,
    val area: String?,
    val logoUrl: String?,
    val salary: Salary?
)
