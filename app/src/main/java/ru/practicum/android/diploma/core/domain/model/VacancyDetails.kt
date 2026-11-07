package ru.practicum.android.diploma.core.domain.model

data class VacancyDetails(
    val id: String,
    val name: String,
    val description: String,
    val salary: Salary?,
    val address: Address?,
    val experience: Experience?,
    val schedule: Schedule?,
    val employment: Employment?,
    val contacts: Contacts?,
    val employer: Employer,
    val area: String,
    val skills: List<String>,
    val shareUrl: String,
    val industryName: String
)
