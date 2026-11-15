package ru.practicum.android.diploma.filter.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.practicum.android.diploma.core.domain.model.FilterSettings
import ru.practicum.android.diploma.filter.data.storage.FilterStorage
import ru.practicum.android.diploma.filter.domain.repository.FilterSettingsRepository

class FilterSettingsRepositoryImpl(
    private val filterStorage: FilterStorage
) : FilterSettingsRepository {
    private val state = MutableStateFlow(filterStorage.read())

    override fun getFilterSettings(): Flow<FilterSettings> =
        state.asStateFlow()

    override suspend fun saveFilterSettings(settings: FilterSettings) {
        filterStorage.write(settings)
        state.value = settings
    }

    override suspend fun clearFilterSettings() {
        filterStorage.clear()
        state.value = FilterSettings()
    }
}
