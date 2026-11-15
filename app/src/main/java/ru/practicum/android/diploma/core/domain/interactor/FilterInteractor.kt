package ru.practicum.android.diploma.core.domain.interactor

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.core.common.result.Resource
import ru.practicum.android.diploma.core.domain.model.FilterArea
import ru.practicum.android.diploma.core.domain.model.FilterIndustry
import ru.practicum.android.diploma.core.domain.model.FilterSettings

interface FilterInteractor {
    suspend fun getAreas(): Resource<List<FilterArea>>
    suspend fun getIndustries(): Resource<List<FilterIndustry>>
    fun getFilterSettings(): Flow<FilterSettings>
    suspend fun saveFilterSettings(settings: FilterSettings)
    suspend fun clearFilterSettings()
}
