package ru.practicum.android.diploma.favorites.domain.interactor

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.core.domain.interactor.FavoritesInteractor
import ru.practicum.android.diploma.core.domain.model.VacancyDetails
import ru.practicum.android.diploma.core.domain.model.VacancyShort
import ru.practicum.android.diploma.favorites.domain.repository.FavoritesRepository

class FavoritesInteractorImpl(
    private val repository: FavoritesRepository
) : FavoritesInteractor {

    override fun getFavorites(): Flow<List<VacancyShort>> =
        repository.getFavorites()

    override suspend fun getFavoriteVacancy(vacancyId: String): VacancyDetails? =
        repository.getFavoriteVacancy(vacancyId)

    override suspend fun addToFavorites(vacancy: VacancyDetails) {
        repository.addToFavorites(vacancy)
    }

    override suspend fun removeFromFavorites(vacancyId: String) {
        repository.removeFromFavorites(vacancyId)
    }

    override fun isFavorite(vacancyId: String): Flow<Boolean> =
        repository.isFavorite(vacancyId)
}
