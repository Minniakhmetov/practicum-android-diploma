package ru.practicum.android.diploma.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.core.database.entity.FavoriteVacancyEntity

@Dao
interface FavoriteVacancyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vacancy: FavoriteVacancyEntity)

    @Query("DELETE FROM favorite_vacancies WHERE id = :id")
    suspend fun deleteById(id: String)

    @Query("SELECT * FROM favorite_vacancies ORDER BY addedAt DESC")
    fun getAll(): Flow<List<FavoriteVacancyEntity>>

    @Query("SELECT * FROM favorite_vacancies WHERE id = :id LIMIT 1")
    suspend fun getById(id: String): FavoriteVacancyEntity?

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_vacancies WHERE id = :id)")
    fun isFavorite(id: String): Flow<Boolean>
}
