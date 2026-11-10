package ru.practicum.android.diploma.core.database.entity

import androidx.room.Embedded
import ru.practicum.android.diploma.core.database.entity.model.SalaryEntity

data class FavoriteVacancyShortDb(
    val id: String,
    val name: String,
    val employerName: String?,
    val area: String?,
    @Embedded(prefix = "salary_")
    val salary: SalaryEntity?,
    val logoUrl: String?,
    val addedAt: Long
)
