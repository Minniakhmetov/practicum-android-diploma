package ru.practicum.android.diploma.vacancy.data.repository

import ru.practicum.android.diploma.core.common.result.Resource
import ru.practicum.android.diploma.core.domain.model.VacancyDetails
import ru.practicum.android.diploma.core.network.NetworkClient
import ru.practicum.android.diploma.vacancy.data.api.VacancyApi
import ru.practicum.android.diploma.vacancy.data.mapper.toDomain
import ru.practicum.android.diploma.vacancy.domain.repository.VacancyDetailsRepository

class VacancyDetailsRepositoryImpl(
    private val networkClient: NetworkClient,
    private val vacancyApi: VacancyApi
) : VacancyDetailsRepository {

    override suspend fun getVacancyDetails(
        vacancyId: String
    ): Resource<VacancyDetails> {
        val result = networkClient.execute {
            vacancyApi.getVacancyDetails(vacancyId)
        }
        return when (result) {
            is Resource.Success -> Resource.Success(result.data.toDomain())
            is Resource.Error -> result
        }
    }
}
