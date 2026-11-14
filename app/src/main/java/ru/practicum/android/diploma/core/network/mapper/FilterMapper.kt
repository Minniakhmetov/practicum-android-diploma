package ru.practicum.android.diploma.core.network.mapper

import ru.practicum.android.diploma.core.domain.model.FilterArea
import ru.practicum.android.diploma.core.domain.model.FilterIndustry
import ru.practicum.android.diploma.core.network.dto.AreaDto
import ru.practicum.android.diploma.core.network.dto.IndustryDto

fun AreaDto.toDomain(): FilterArea = FilterArea(
    id = id,
    name = name,
    parentId = parentId,
    areas = areas.map { it.toDomain() }
)

fun IndustryDto.toDomain(): FilterIndustry =
    FilterIndustry(id = id, name = name)
