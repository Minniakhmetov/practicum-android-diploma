package ru.practicum.android.diploma.core.domain.interactor

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.core.domain.model.FilterSettings

interface FilterInteractor {
    fun getFilterSettings(): Flow<FilterSettings>
    suspend fun saveFilterSettings(settings: FilterSettings)
    suspend fun clearFilterSettings()

    // Настройки, с которыми получена текущая выдача на экране поиска. Хранятся только в памяти:
    // поиск сообщает их при запуске, фильтр сравнивает с ними, чтобы показать "Применить"
    fun getAppliedSettings(): FilterSettings
    fun setAppliedSettings(settings: FilterSettings)

    // Приостанавливает вызывающего, пока начальные настройки фильтра
    // не прочитаны из хранилища. Первый поиск должен дождаться этого.
    suspend fun awaitFilterSettingsLoaded()
}
