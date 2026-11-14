package ru.practicum.android.diploma.filter.data.api

import retrofit2.http.GET
import ru.practicum.android.diploma.core.network.dto.AreaDto
import ru.practicum.android.diploma.core.network.dto.IndustryDto

interface FilterApi {
    @GET("areas")
    suspend fun getAreas(): List<AreaDto>

    @GET("industries")
    suspend fun getIndustries(): List<IndustryDto>
}
