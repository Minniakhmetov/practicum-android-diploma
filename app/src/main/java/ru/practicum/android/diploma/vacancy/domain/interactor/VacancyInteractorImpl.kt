package ru.practicum.android.diploma.vacancy.domain.interactor

import ru.practicum.android.diploma.core.common.result.Resource
import ru.practicum.android.diploma.core.domain.model.VacancyDetails
import ru.practicum.android.diploma.vacancy.domain.repository.VacancyDetailsRepository

class VacancyInteractorImpl(
    private val repository: VacancyDetailsRepository
) : VacancyInteractor {
    override suspend fun getVacancyDetails(
        vacancyId: String
    ): Resource<VacancyDetails> {
        return repository.getVacancyDetails(vacancyId)
    }
}
