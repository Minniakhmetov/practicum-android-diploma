package ru.practicum.android.diploma.core.network

import ru.practicum.android.diploma.core.common.result.Resource

interface NetworkClient {
    suspend fun <T> execute(
        request: suspend () -> T
    ): Resource<T>
}
