package ru.practicum.android.diploma.filter.domain.repository

import ru.practicum.android.diploma.core.common.result.Resource
import ru.practicum.android.diploma.core.domain.model.FilterArea
import ru.practicum.android.diploma.core.domain.model.FilterIndustry

interface FilterDictionariesRepository {
    suspend fun getAreas(): Resource<List<FilterArea>>
    suspend fun getIndustries(): Resource<List<FilterIndustry>>
}
