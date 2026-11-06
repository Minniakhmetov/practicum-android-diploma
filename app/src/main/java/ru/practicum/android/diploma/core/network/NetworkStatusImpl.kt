package ru.practicum.android.diploma.core.network

import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class NetworkStatusImpl(
    private val connectivityManager: ConnectivityManager
) : NetworkStatus {

    override fun isOnline(): Boolean {
        val network = connectivityManager.activeNetwork ?: return false

        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false

        return capabilities.hasCapability(
            NetworkCapabilities.NET_CAPABILITY_INTERNET
        ) && capabilities.hasCapability(
            NetworkCapabilities.NET_CAPABILITY_VALIDATED
        )
    }
}
