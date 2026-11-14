package ru.practicum.android.diploma.filter.domain.interactor

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.core.common.result.Resource
import ru.practicum.android.diploma.core.domain.interactor.FilterInteractor
import ru.practicum.android.diploma.core.domain.model.FilterArea
import ru.practicum.android.diploma.core.domain.model.FilterIndustry
import ru.practicum.android.diploma.core.domain.model.FilterSettings
import ru.practicum.android.diploma.filter.domain.repository.FilterDictionariesRepository
import ru.practicum.android.diploma.filter.domain.repository.FilterSettingsRepository

class FilterInteractorImpl(
    private val filterRepository: FilterSettingsRepository,
    private val dictionariesRepository: FilterDictionariesRepository
) : FilterInteractor {
    override suspend fun getAreas(): Resource<List<FilterArea>> =
        dictionariesRepository.getAreas()

    override suspend fun getIndustries(): Resource<List<FilterIndustry>> =
        dictionariesRepository.getIndustries()

    override fun getFilterSettings(): Flow<FilterSettings> =
        filterRepository.getFilterSettings()

    override suspend fun saveFilterSettings(settings: FilterSettings) =
        filterRepository.saveFilterSettings(settings)

    override suspend fun clearFilterSettings() =
        filterRepository.clearFilterSettings()
}
