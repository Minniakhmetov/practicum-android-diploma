package ru.practicum.android.diploma.vacancy.domain.interactor

import ru.practicum.android.diploma.core.common.result.Resource
import ru.practicum.android.diploma.core.domain.model.VacancyDetails
import ru.practicum.android.diploma.vacancy.domain.repository.VacancyDetailsRepository
import ru.practicum.android.diploma.vacancy.domain.sharing.ExternalNavigator

class VacancyInteractorImpl(
    private val repository: VacancyDetailsRepository,
    private val externalNavigator: ExternalNavigator,
) : VacancyInteractor {
    override suspend fun getVacancyDetails(
        vacancyId: String
    ): Resource<VacancyDetails> {
        return repository.getVacancyDetails(vacancyId)
    }

    override fun shareVacancy(url: String) {
        externalNavigator.shareLink(url)
    }

    override fun sendEmail(email: String) {
        externalNavigator.sendEmail(email)
    }

    override fun callPhone(phone: String) {
        externalNavigator.dialPhone(phone)
    }
}
