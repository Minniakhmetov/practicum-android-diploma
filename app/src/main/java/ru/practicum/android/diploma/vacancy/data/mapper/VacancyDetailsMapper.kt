package ru.practicum.android.diploma.vacancy.data.mapper

import ru.practicum.android.diploma.core.domain.model.Address
import ru.practicum.android.diploma.core.domain.model.Contacts
import ru.practicum.android.diploma.core.domain.model.Employer
import ru.practicum.android.diploma.core.domain.model.Employment
import ru.practicum.android.diploma.core.domain.model.Experience
import ru.practicum.android.diploma.core.domain.model.Phone
import ru.practicum.android.diploma.core.domain.model.Schedule
import ru.practicum.android.diploma.core.domain.model.VacancyDetails
import ru.practicum.android.diploma.core.network.mapper.toDomain
import ru.practicum.android.diploma.vacancy.data.dto.AddressDto
import ru.practicum.android.diploma.vacancy.data.dto.ContactsDto
import ru.practicum.android.diploma.vacancy.data.dto.EmployerDto
import ru.practicum.android.diploma.vacancy.data.dto.EmploymentDto
import ru.practicum.android.diploma.vacancy.data.dto.ExperienceDto
import ru.practicum.android.diploma.vacancy.data.dto.PhoneDto
import ru.practicum.android.diploma.vacancy.data.dto.ScheduleDto
import ru.practicum.android.diploma.vacancy.data.dto.VacancyDetailsResponse

internal fun VacancyDetailsResponse.toDomain(): VacancyDetails =
    VacancyDetails(
        id = id,
        name = name,
        description = description,
        salary = salary?.toDomain(),
        address = address?.toDomain(),
        experience = experience?.toDomain(),
        schedule = schedule?.toDomain(),
        employment = employment?.toDomain(),
        contacts = contacts?.toDomain(),
        employer = employer.toDomain(),
        area = area.name,
        skills = skills,
        shareUrl = "https://$url",
        industryName = industry.name
    )

private fun AddressDto.toDomain(): Address =
    Address(
        id = id,
        city = city,
        street = street,
        building = building,
        raw = raw
    )

private fun ExperienceDto.toDomain(): Experience =
    Experience(
        id = id,
        name = name
    )

private fun ScheduleDto.toDomain(): Schedule =
    Schedule(
        id = id,
        name = name
    )

private fun EmploymentDto.toDomain(): Employment =
    Employment(
        id = id,
        name = name
    )

private fun ContactsDto.toDomain(): Contacts =
    Contacts(
        id = id,
        name = name,
        email = email,
        phones = phones.toDomain()
    )

private fun List<PhoneDto>.toDomain(): List<Phone> =
    map { it.toDomain() }

private fun PhoneDto.toDomain(): Phone =
    Phone(
        comment = comment,
        formatted = formatted
    )

private fun EmployerDto.toDomain(): Employer =
    Employer(
        id = id,
        name = name,
        logoUrl = logo
    )
