package ru.practicum.android.diploma.filter.data.repository

import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.core.domain.model.FilterSettings
import ru.practicum.android.diploma.filter.data.storage.FilterStorage
import ru.practicum.android.diploma.filter.domain.repository.FilterSettingsRepository

class FilterSettingsRepositoryImpl(
    private val filterStorage: FilterStorage,
    appScope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default),
    ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : FilterSettingsRepository {
    private val state = MutableStateFlow(FilterSettings())

    // Сигнал «начальные настройки прочитаны».
    private val loaded = CompletableDeferred<Unit>()

    // Настройки текущей выдачи поиска. Только в памяти: репозиторий - single и живёт, пока жив процесс,
    // а после перезапуска процесса выдачи на экране поиска тоже нет
    private var appliedSettings = FilterSettings()

    init {
        appScope.launch(ioDispatcher) {
            try {
                state.value = filterStorage.read()
            } finally {
                // Завершаем сигнал в любом случае (успех/ошибка),
                // иначе awaitLoaded() повиснет навсегда при исключении.
                loaded.complete(Unit)
            }
        }
    }

    override fun getFilterSettings(): Flow<FilterSettings> =
        state.asStateFlow()

    override suspend fun saveFilterSettings(settings: FilterSettings) {
        filterStorage.write(settings)
        loaded.await()
        state.value = settings
    }

    override suspend fun clearFilterSettings() {
        filterStorage.clear()
        loaded.await()
        state.value = FilterSettings()
    }

    override fun getAppliedSettings(): FilterSettings = appliedSettings

    override fun setAppliedSettings(settings: FilterSettings) {
        appliedSettings = settings
    }

    override suspend fun awaitLoaded() {
        loaded.await()
    }
}
