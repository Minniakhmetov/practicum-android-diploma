package ru.practicum.android.diploma.search.presentation.state

import ru.practicum.android.diploma.core.common.result.ErrorType

sealed interface SearchEvent {
    data class NextPageError(val type: ErrorType) : SearchEvent
}
