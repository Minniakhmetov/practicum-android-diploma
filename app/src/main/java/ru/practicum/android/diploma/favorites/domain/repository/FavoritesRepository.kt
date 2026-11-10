package ru.practicum.android.diploma.favorites.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.core.domain.model.VacancyDetails
import ru.practicum.android.diploma.core.domain.model.VacancyShort

interface FavoritesRepository {
    // Новые сверху. Flow из БД: список обновляется сразу после добавления или удаления
    fun getFavorites(): Flow<List<VacancyShort>>
    suspend fun getFavoriteVacancy(vacancyId: String): VacancyDetails?
    suspend fun addToFavorites(vacancy: VacancyDetails)
    suspend fun removeFromFavorites(vacancyId: String)
    fun isFavorite(vacancyId: String): Flow<Boolean>
}
