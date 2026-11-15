package ru.practicum.android.diploma.app

import android.app.Application
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.SingletonImageLoader
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.practicum.android.diploma.core.di.commonModule
import ru.practicum.android.diploma.core.di.databaseModule
import ru.practicum.android.diploma.core.di.networkModule
import ru.practicum.android.diploma.core.network.ImageLoaderFactory
import ru.practicum.android.diploma.favorites.di.favoritesModule
import ru.practicum.android.diploma.filter.di.filterModule
import ru.practicum.android.diploma.search.di.searchModule
import ru.practicum.android.diploma.team.di.teamModule
import ru.practicum.android.diploma.vacancy.di.vacancyModule

class App : Application(), SingletonImageLoader.Factory {

    // Общий загрузчик для всех AsyncImage в приложении
    override fun newImageLoader(context: PlatformContext): ImageLoader =
        ImageLoaderFactory.create(context)

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                commonModule,
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
