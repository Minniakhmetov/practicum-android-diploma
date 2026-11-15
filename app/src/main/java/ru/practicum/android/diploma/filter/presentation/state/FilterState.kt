package ru.practicum.android.diploma.filter.presentation.state

// Состояние экрана "Настройки фильтрации".
// Зарплата хранится строкой, как она введена в поле: пустая строка - зарплата не указана
data class FilterState(
    val salary: String = "",
    val onlyWithSalary: Boolean = false,
) {
    // "Применить" и "Сбросить" видны, если указан хотя бы один параметр
    val isActionsVisible: Boolean
        get() = salary.isNotEmpty() || onlyWithSalary
}
