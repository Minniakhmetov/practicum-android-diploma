package ru.practicum.android.diploma.vacancy.domain.repository

import ru.practicum.android.diploma.core.common.result.Resource
import ru.practicum.android.diploma.core.domain.model.VacancyDetails

interface VacancyDetailsRepository {
    suspend fun getVacancyDetails(vacancyId: String): Resource<VacancyDetails>
}
