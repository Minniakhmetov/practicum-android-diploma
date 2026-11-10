package com.example.sprint28.app

import ru.practicum.android.diploma.core.common.result.Resource
import ru.practicum.android.diploma.core.domain.model.FilterSettings
import ru.practicum.android.diploma.core.network.NetworkClient
import ru.practicum.android.diploma.search.data.api.SearchApi
import ru.practicum.android.diploma.search.data.mapper.toDomain
import ru.practicum.android.diploma.search.domain.model.VacanciesPage
import ru.practicum.android.diploma.search.domain.repository.SearchRepository

class SearchRepositoryImpl(
    private val networkClient: NetworkClient,
    private val searchApi: SearchApi
) : SearchRepository {
    override suspend fun searchVacancies(
        text: String,
        settings: FilterSettings,
        page: Int
    ): Resource<VacanciesPage> {
        val options = createSearchOptions(text, settings, page)
        val result = networkClient.execute {
            searchApi.searchVacancies(options)
        }
        return when (result) {
            is Resource.Success -> {
                Resource.Success(result.data.toDomain())
            }

            is Resource.Error -> result
        }
    }

    private fun createSearchOptions(
        text: String,
        settings: FilterSettings,
        page: Int
    ): Map<String, String> {
        return buildMap {
            put("text", text)
            put("page", page.toString())

            settings.cityId?.let {
                put("area", it.toString())
            }

            settings.industryId?.let {
                put("industry", it.toString())
            }

            settings.salary?.let {
                put("salary", it.toString())
            }

            settings.onlyWithSalary.let {
                put("only_with_salary", it.toString())
            }
        }
    }
}
