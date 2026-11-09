package ru.practicum.android.diploma.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.practicum.android.diploma.core.database.converter.FavoriteVacancyConverter
import ru.practicum.android.diploma.core.database.dao.FavoriteVacancyDao
import ru.practicum.android.diploma.core.database.entity.FavoriteVacancyEntity

@Database(
    entities = [FavoriteVacancyEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    FavoriteVacancyConverter::class
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun favoriteVacancyDao(): FavoriteVacancyDao
}
