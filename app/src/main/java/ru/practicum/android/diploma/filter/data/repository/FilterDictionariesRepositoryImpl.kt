package ru.practicum.android.diploma.filter.data.repository

import ru.practicum.android.diploma.core.common.result.Resource
import ru.practicum.android.diploma.core.domain.model.FilterArea
import ru.practicum.android.diploma.core.domain.model.FilterIndustry
import ru.practicum.android.diploma.core.network.NetworkClient
import ru.practicum.android.diploma.core.network.mapper.toDomain
import ru.practicum.android.diploma.filter.data.api.FilterApi
import ru.practicum.android.diploma.filter.domain.repository.FilterDictionariesRepository

class FilterDictionariesRepositoryImpl(
    private val networkClient: NetworkClient,
    private val filterApi: FilterApi
) : FilterDictionariesRepository {
    override suspend fun getAreas(): Resource<List<FilterArea>> {
        return networkClient.execute {
            filterApi.getAreas().map { it.toDomain() }
        }
    }

    override suspend fun getIndustries(): Resource<List<FilterIndustry>> {
        return networkClient.execute {
            filterApi.getIndustries().map { it.toDomain() }
        }
    }
}
