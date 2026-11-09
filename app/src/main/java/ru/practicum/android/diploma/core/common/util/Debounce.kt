package ru.practicum.android.diploma.core.common.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Debounce<T>(
    private val delayMillis: Long,
    private val scope: CoroutineScope,
    private val action: suspend (T) -> Unit
) {
    private var job: Job? = null

    fun start(value: T) {
        job?.cancel()

        job = scope.launch {
            delay(delayMillis)
            action(value)
        }
    }

    fun cancel() {
        job?.cancel()
        job = null
    }
}
