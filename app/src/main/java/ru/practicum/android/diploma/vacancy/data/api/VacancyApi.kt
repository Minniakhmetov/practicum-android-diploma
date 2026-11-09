package ru.practicum.android.diploma.vacancy.data.api

import retrofit2.http.GET
import retrofit2.http.Path
import ru.practicum.android.diploma.vacancy.data.dto.VacancyDetailsResponse

interface VacancyApi {
    @GET("vacancies/{id}")
    suspend fun getVacancyDetails(
        @Path("id") vacancyId: String
    ): VacancyDetailsResponse
}
