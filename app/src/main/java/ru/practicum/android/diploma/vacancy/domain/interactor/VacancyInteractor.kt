package ru.practicum.android.diploma.vacancy.domain.interactor

import ru.practicum.android.diploma.core.common.result.Response
import ru.practicum.android.diploma.vacancy.domain.model.VacancyDetails

interface VacancyInteractor {
    suspend fun getVacancyDetails(
        vacancyId: String
    ): Response<VacancyDetails>
}
