package ru.practicum.android.diploma.core.common.result

sealed interface Resource<out T> {
    data class Success<T>(val data: T) : Resource<T>
    data class Error(
        val type: ErrorType,
    ) : Resource<Nothing>
}
