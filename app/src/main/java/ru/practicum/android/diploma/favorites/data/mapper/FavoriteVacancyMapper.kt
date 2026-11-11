package ru.practicum.android.diploma.favorites.data.mapper

import ru.practicum.android.diploma.core.database.entity.FavoriteVacancyEntity
import ru.practicum.android.diploma.core.database.entity.FavoriteVacancyShortDb
import ru.practicum.android.diploma.core.database.entity.model.AddressEntity
import ru.practicum.android.diploma.core.database.entity.model.ContactsEntity
import ru.practicum.android.diploma.core.database.entity.model.EmployerEntity
import ru.practicum.android.diploma.core.database.entity.model.EmploymentEntity
import ru.practicum.android.diploma.core.database.entity.model.ExperienceEntity
import ru.practicum.android.diploma.core.database.entity.model.PhoneEntity
import ru.practicum.android.diploma.core.database.entity.model.SalaryEntity
import ru.practicum.android.diploma.core.database.entity.model.ScheduleEntity
import ru.practicum.android.diploma.core.domain.model.Address
import ru.practicum.android.diploma.core.domain.model.Contacts
import ru.practicum.android.diploma.core.domain.model.Employer
import ru.practicum.android.diploma.core.domain.model.Employment
import ru.practicum.android.diploma.core.domain.model.Experience
import ru.practicum.android.diploma.core.domain.model.Phone
import ru.practicum.android.diploma.core.domain.model.Salary
import ru.practicum.android.diploma.core.domain.model.Schedule
import ru.practicum.android.diploma.core.domain.model.VacancyDetails
import ru.practicum.android.diploma.core.domain.model.VacancyShort

// VacancyDetails -> Entity: addedAt не передаём, он заполняется временем сохранения
internal fun VacancyDetails.toEntity(): FavoriteVacancyEntity =
    FavoriteVacancyEntity(
        id = id,
        name = name,
        description = description,
        salary = salary?.toEntity(),
        address = address?.toEntity(),
        experience = experience?.let { ExperienceEntity(id = it.id, name = it.name) },
        schedule = schedule?.let { ScheduleEntity(id = it.id, name = it.name) },
        employment = employment?.let { EmploymentEntity(id = it.id, name = it.name) },
        contacts = contacts?.toEntity(),
        employer = employer.toEntity(),
        area = area,
        skills = skills,
        shareUrl = shareUrl,
        industryName = industryName
    )

internal fun FavoriteVacancyEntity.toDomain(): VacancyDetails =
    VacancyDetails(
        id = id,
        name = name,
        description = description,
        salary = salary?.toDomain(),
        address = address?.toDomain(),
        experience = experience?.let { Experience(id = it.id, name = it.name) },
        schedule = schedule?.let { Schedule(id = it.id, name = it.name) },
        employment = employment?.let { Employment(id = it.id, name = it.name) },
        contacts = contacts?.toDomain(),
        employer = employer.toDomain(),
        area = area,
        skills = skills,
        shareUrl = shareUrl,
        industryName = industryName
    )

// Короткая запись для списка избранного -> карточка вакансии
internal fun FavoriteVacancyShortDb.toDomain(): VacancyShort =
    VacancyShort(
        id = id,
        name = name,
        employerName = employerName,
        area = area,
        logoUrl = logoUrl,
        salary = salary?.toDomain()
    )

private fun Salary.toEntity(): SalaryEntity =
    SalaryEntity(from = from, to = to, currency = currency)

private fun SalaryEntity.toDomain(): Salary =
    Salary(from = from, to = to, currency = currency)

private fun Address.toEntity(): AddressEntity =
    AddressEntity(id = id, city = city, street = street, building = building, raw = raw)

private fun AddressEntity.toDomain(): Address =
    Address(id = id, city = city, street = street, building = building, raw = raw)

private fun Contacts.toEntity(): ContactsEntity =
    ContactsEntity(
        id = id,
        name = name,
        email = email,
        phones = phones.map { PhoneEntity(comment = it.comment, formatted = it.formatted) }
    )

private fun ContactsEntity.toDomain(): Contacts =
    Contacts(
        id = id,
        name = name,
        email = email,
        phones = phones.map { Phone(comment = it.comment, formatted = it.formatted) }
    )

private fun Employer.toEntity(): EmployerEntity =
    EmployerEntity(id = id, name = name, logoUrl = logoUrl)

private fun EmployerEntity.toDomain(): Employer =
    Employer(id = id, name = name, logoUrl = logoUrl)
