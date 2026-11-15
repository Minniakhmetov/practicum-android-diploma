package ru.practicum.android.diploma.search.data.api

import retrofit2.http.GET
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.search.data.dto.VacancyResponseDto

interface SearchApi {
    @GET("vacancies")
    suspend fun searchVacancies(
        @QueryMap options: Map<String, String>
    ): VacancyResponseDto
}
