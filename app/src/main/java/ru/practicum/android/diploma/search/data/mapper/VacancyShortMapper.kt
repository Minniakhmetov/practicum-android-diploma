package ru.practicum.android.diploma.search.data.mapper

import ru.practicum.android.diploma.core.domain.model.VacancyShort
import ru.practicum.android.diploma.core.network.mapper.toDomain
import ru.practicum.android.diploma.search.data.dto.VacancyDto

internal fun VacancyDto.toDomain(): VacancyShort {
    return VacancyShort(
        id = id,
        name = name,
        employerName = company,
        area = city,
        salary = salary?.toDomain(),
        logoUrl = logo
    )
}
