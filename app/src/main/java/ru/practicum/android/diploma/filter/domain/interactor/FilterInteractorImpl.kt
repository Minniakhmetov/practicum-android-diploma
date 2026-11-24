package ru.practicum.android.diploma.filter.domain.interactor

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.core.domain.interactor.FilterInteractor
import ru.practicum.android.diploma.core.domain.model.FilterSettings
import ru.practicum.android.diploma.filter.domain.repository.FilterSettingsRepository

class FilterInteractorImpl(
    private val filterRepository: FilterSettingsRepository,
) : FilterInteractor {
    override fun getFilterSettings(): Flow<FilterSettings> =
        filterRepository.getFilterSettings()

    override suspend fun saveFilterSettings(settings: FilterSettings) =
        filterRepository.saveFilterSettings(settings)

    override suspend fun clearFilterSettings() =
        filterRepository.clearFilterSettings()

    override fun getAppliedSettings(): FilterSettings =
        filterRepository.getAppliedSettings()

    override fun setAppliedSettings(settings: FilterSettings) =
        filterRepository.setAppliedSettings(settings)

    override suspend fun awaitFilterSettingsLoaded() {
        filterRepository.awaitLoaded()
    }
}
