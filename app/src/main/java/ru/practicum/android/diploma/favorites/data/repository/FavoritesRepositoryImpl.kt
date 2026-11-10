package ru.practicum.android.diploma.favorites.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.core.database.dao.FavoriteVacancyDao
import ru.practicum.android.diploma.core.domain.model.VacancyDetails
import ru.practicum.android.diploma.core.domain.model.VacancyShort
import ru.practicum.android.diploma.favorites.data.mapper.toDomain
import ru.practicum.android.diploma.favorites.data.mapper.toEntity
import ru.practicum.android.diploma.favorites.domain.repository.FavoritesRepository

// Room сам выполняет suspend-запросы и Flow вне главного потока, поэтому диспетчер не переключаем
class FavoritesRepositoryImpl(
    private val dao: FavoriteVacancyDao
) : FavoritesRepository {

    override fun getFavorites(): Flow<List<VacancyShort>> =
        dao.getAllShorts().map { vacancies -> vacancies.map { it.toDomain() } }

    override suspend fun getFavoriteVacancy(vacancyId: String): VacancyDetails? =
        dao.getById(vacancyId)?.toDomain()

    override suspend fun addToFavorites(vacancy: VacancyDetails) {
        dao.insert(vacancy.toEntity())
    }

    override suspend fun removeFromFavorites(vacancyId: String) {
        dao.deleteById(vacancyId)
    }

    override fun isFavorite(vacancyId: String): Flow<Boolean> =
        dao.isFavorite(vacancyId)
}
