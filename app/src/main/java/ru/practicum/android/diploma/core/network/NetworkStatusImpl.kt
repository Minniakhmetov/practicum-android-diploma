package ru.practicum.android.diploma.core.network

import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class NetworkStatusImpl(
    private val connectivityManager: ConnectivityManager
) : NetworkStatus {

    override fun isOnline(): Boolean {
        val network = connectivityManager.activeNetwork
        val capabilities = network?.let {
            connectivityManager.getNetworkCapabilities(it)
        }

        return capabilities != null &&
            capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
            capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
    }
}
