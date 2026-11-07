package ru.practicum.android.diploma.core.di

import okhttp3.OkHttpClient
import org.koin.dsl.module
import ru.practicum.android.diploma.core.network.OkHttpClientFactory

val networkModule = module {
    single<OkHttpClient> {
        OkHttpClientFactory.create()
    }
}
