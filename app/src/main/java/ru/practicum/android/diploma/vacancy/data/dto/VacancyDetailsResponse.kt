package ru.practicum.android.diploma.vacancy.data.dto

import ru.practicum.android.diploma.core.network.dto.AreaDto
import ru.practicum.android.diploma.core.network.dto.IndustryDto
import ru.practicum.android.diploma.core.network.dto.SalaryDto

data class VacancyDetailsResponse(
    val id: String,
    val name: String,
    val description: String,
    val salary: SalaryDto?,
    val address: AddressDto?,
    val experience: ExperienceDto?,
    val schedule: ScheduleDto?,
    val employment: EmploymentDto?,
    val contacts: ContactsDto?,
    val employer: EmployerDto,
    val area: AreaDto,
    val skills: List<String>,
    val url: String,
    val industry: IndustryDto
)
