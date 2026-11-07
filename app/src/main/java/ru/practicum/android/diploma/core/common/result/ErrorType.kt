package ru.practicum.android.diploma.core.common.result

sealed interface ErrorType {
    data object NoInternet : ErrorType
    data object Server : ErrorType
    data object NotFound : ErrorType
}
