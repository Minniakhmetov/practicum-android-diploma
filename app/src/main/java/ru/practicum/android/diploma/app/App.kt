package ru.practicum.android.diploma.app

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.practicum.android.diploma.core.di.databaseModule
import ru.practicum.android.diploma.core.di.networkModule
import ru.practicum.android.diploma.favorites.di.favoritesModule
import ru.practicum.android.diploma.filter.di.filterModule
import ru.practicum.android.diploma.search.di.searchModule
import ru.practicum.android.diploma.team.di.teamModule
import ru.practicum.android.diploma.vacancy.di.vacancyModule

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                networkModule,
                databaseModule,
                searchModule,
                vacancyModule,
                favoritesModule,
                filterModule,
                teamModule
            )
        }
    }
}
