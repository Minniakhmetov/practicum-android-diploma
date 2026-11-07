package ru.practicum.android.diploma.core.common.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun <T> debounce(
    delayMillis: Long,
    scope: CoroutineScope,
    action: suspend (T) -> Unit
): (T) -> Unit {
    var job: Job? = null
    return { value ->
        job?.cancel()

        job = scope.launch {
            delay(delayMillis)
            action(value)
        }
    }
}
