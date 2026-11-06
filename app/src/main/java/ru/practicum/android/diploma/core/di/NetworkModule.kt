package ru.practicum.android.diploma.core.di

import org.koin.dsl.module
import ru.practicum.android.diploma.core.network.GsonFactory

val networkModule = module {
    single {
        GsonFactory.create()
    }
}
