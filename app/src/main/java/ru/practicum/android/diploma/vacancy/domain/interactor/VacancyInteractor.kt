package ru.practicum.android.diploma.vacancy.domain.interactor

import ru.practicum.android.diploma.core.common.result.Resource
import ru.practicum.android.diploma.core.domain.model.VacancyDetails

interface VacancyInteractor {
    suspend fun getVacancyDetails(
        vacancyId: String
    ): Resource<VacancyDetails>

    fun shareVacancy(url: String)
    fun sendEmail(email: String)
    fun callPhone(phone: String)
}
