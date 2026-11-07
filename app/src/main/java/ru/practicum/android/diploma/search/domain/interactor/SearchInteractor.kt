package ru.practicum.android.diploma.search.domain.interactor

import ru.practicum.android.diploma.core.common.result.Resource
import ru.practicum.android.diploma.core.domain.model.FilterSettings
import ru.practicum.android.diploma.search.domain.model.VacanciesPage

interface SearchInteractor {
    suspend fun searchVacancies(
        text: String,
        settings: FilterSettings,
        page: Int
    ): Resource<VacanciesPage>
}
