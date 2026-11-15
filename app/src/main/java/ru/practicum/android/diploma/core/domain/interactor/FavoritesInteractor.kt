package ru.practicum.android.diploma.core.domain.interactor

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.core.domain.model.VacancyDetails
import ru.practicum.android.diploma.core.domain.model.VacancyShort

interface FavoritesInteractor {
    fun getFavorites(): Flow<List<VacancyShort>>
    suspend fun getFavoriteVacancy(vacancyId: String): VacancyDetails?

    // Добавить, если вакансии нет в избранном, иначе удалить
    suspend fun toggleFavorite(vacancy: VacancyDetails)
    suspend fun removeFromFavorites(vacancyId: String)
    fun isFavorite(vacancyId: String): Flow<Boolean>
}
