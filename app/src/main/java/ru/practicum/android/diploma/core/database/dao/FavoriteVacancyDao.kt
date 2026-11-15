package ru.practicum.android.diploma.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.core.database.entity.FavoriteVacancyEntity
import ru.practicum.android.diploma.core.database.entity.FavoriteVacancyShortDb

@Dao
interface FavoriteVacancyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vacancy: FavoriteVacancyEntity)

    @Query("DELETE FROM favorite_vacancies WHERE id = :id")
    suspend fun deleteById(id: String)

    @Query("""
    SELECT
        id,
        name,
        employer_name AS employerName,
        area,
        salary_from,
        salary_to,
        salary_currency,
        employer_logoUrl AS logoUrl,
        addedAt
    FROM favorite_vacancies
    ORDER BY addedAt DESC
""")
    fun getAllShorts(): Flow<List<FavoriteVacancyShortDb>>

    @Query("SELECT * FROM favorite_vacancies WHERE id = :id LIMIT 1")
    suspend fun getById(id: String): FavoriteVacancyEntity?

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_vacancies WHERE id = :id)")
    fun isFavorite(id: String): Flow<Boolean>

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_vacancies WHERE id = :id)")
    suspend fun exists(id: String): Boolean

    // Проверка и изменение в одной транзакции: решение принимается по актуальному состоянию БД,
    // а не по значению на экране. Транзакции Room выполняются по очереди, поэтому быстрые
    // повторные нажатия обрабатываются последовательно
    @Transaction
    suspend fun toggle(vacancy: FavoriteVacancyEntity) {
        if (exists(vacancy.id)) {
            deleteById(vacancy.id)
        } else {
            insert(vacancy)
        }
    }
}
