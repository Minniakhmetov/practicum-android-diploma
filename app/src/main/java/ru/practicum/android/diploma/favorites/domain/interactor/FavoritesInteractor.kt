package ru.practicum.android.diploma.favorites.domain.interactor

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.core.domain.model.VacancyShort

interface FavoritesInteractor {

    fun getFavorites(): Flow<List<VacancyShort>>

    suspend fun addToFavorites(vacancyId: String)

    suspend fun removeFromFavorites(vacancyId: String)

    fun isFavorite(vacancyId: String): Flow<Boolean>
}
