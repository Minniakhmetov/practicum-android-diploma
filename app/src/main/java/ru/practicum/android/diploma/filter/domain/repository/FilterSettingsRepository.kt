package ru.practicum.android.diploma.filter.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.core.domain.model.FilterSettings

interface FilterSettingsRepository {
    fun getFilterSettings(): Flow<FilterSettings>
    suspend fun saveFilterSettings(settings: FilterSettings)
    suspend fun clearFilterSettings()
}
