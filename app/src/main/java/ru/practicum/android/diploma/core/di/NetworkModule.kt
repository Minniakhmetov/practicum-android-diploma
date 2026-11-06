package ru.practicum.android.diploma.core.di

import android.content.Context
import android.net.ConnectivityManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.practicum.android.diploma.core.network.NetworkStatus
import ru.practicum.android.diploma.core.network.NetworkStatusImpl

val networkModule = module {
    single<NetworkStatus> {
        NetworkStatusImpl(
            connectivityManager = androidContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        )
    }

}
