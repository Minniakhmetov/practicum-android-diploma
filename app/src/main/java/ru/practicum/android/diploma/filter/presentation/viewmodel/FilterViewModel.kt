package ru.practicum.android.diploma.filter.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.core.domain.interactor.FilterInteractor
import ru.practicum.android.diploma.core.domain.model.FilterSettings
import ru.practicum.android.diploma.filter.presentation.state.FilterState

// ViewModel экрана "Настройки фильтрации". Каждое изменение сразу сохраняется через интерактор.
// "Применить" и "Назад" - навигация, их обрабатывает фрагмент: настройки к этому моменту уже сохранены
class FilterViewModel(
    private val filterInteractor: FilterInteractor,
) : ViewModel() {

    private val _state = MutableStateFlow(FilterState())
    val state: StateFlow<FilterState> = _state.asStateFlow()

    // Сохранённые настройки целиком: при записи не теряем параметры, которых нет на экране
    private var settings = FilterSettings()

    init {
        loadSettings()
    }

    fun onSalaryChange(salary: String) {
        _state.value = _state.value.copy(salary = salary)
        saveSettings(settings.copy(salary = salary.toIntOrNull()))
    }

    fun onSalaryClear() {
        onSalaryChange("")
    }

    fun onOnlyWithSalaryChange(onlyWithSalary: Boolean) {
        _state.value = _state.value.copy(onlyWithSalary = onlyWithSalary)
        saveSettings(settings.copy(onlyWithSalary = onlyWithSalary))
    }

    fun onResetClick() {
        settings = FilterSettings()
        _state.value = FilterState()
        viewModelScope.launch {
            filterInteractor.clearFilterSettings()
        }
    }

    // Настройки читаются один раз при входе на экран. Дальше источник правды - состояние экрана:
    // если подписаться на поток, сохранённое число вернулось бы в поле и стёрло бы
    // введённые ведущие нули ("007" -> "7")
    private fun loadSettings() {
        viewModelScope.launch {
            settings = filterInteractor.getFilterSettings().first()
            _state.value = FilterState(
                salary = settings.salary?.toString().orEmpty(),
                onlyWithSalary = settings.onlyWithSalary,
            )
        }
    }

    private fun saveSettings(newSettings: FilterSettings) {
        settings = newSettings
        viewModelScope.launch {
            filterInteractor.saveFilterSettings(newSettings)
        }
    }
}
