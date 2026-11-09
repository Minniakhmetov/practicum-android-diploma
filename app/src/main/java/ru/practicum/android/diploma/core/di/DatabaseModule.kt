package ru.practicum.android.diploma.core.di

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.practicum.android.diploma.core.database.AppDatabase
import ru.practicum.android.diploma.core.database.dao.FavoriteVacancyDao

val databaseModule = module {
    single<AppDatabase> {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "app_database.db"
        ).build()
    }

    single<FavoriteVacancyDao> {
        get<AppDatabase>().favoriteVacancyDao()
    }
}
