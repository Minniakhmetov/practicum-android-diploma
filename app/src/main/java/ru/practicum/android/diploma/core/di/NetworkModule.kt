package ru.practicum.android.diploma.core.di

import android.content.Context
import android.net.ConnectivityManager
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.practicum.android.diploma.core.network.GsonFactory
import ru.practicum.android.diploma.core.network.NetworkStatus
import ru.practicum.android.diploma.core.network.NetworkStatusImpl
import ru.practicum.android.diploma.core.network.OkHttpClientFactory

val networkModule = module {

    single {
        GsonFactory.create()
    }

    single<OkHttpClient> {
        OkHttpClientFactory.create()
    }

    single<NetworkStatus> {
        NetworkStatusImpl(
            connectivityManager = androidContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        )
    }
}
