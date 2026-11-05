package ru.practicum.android.diploma.core.domain.model

data class VacancyShort(
    val id: String,
    val name: String,
    val company: String?,
    val city: String?,
    val logoUrl: String?,
    val salary: Salary?
)
