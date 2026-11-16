package ru.practicum.android.diploma.filter.presentation.state

// Состояние экрана "Настройки фильтрации".
// Зарплата хранится строкой, как она введена в поле: пустая строка - зарплата не указана.
// isChanged - настройки отличаются от тех, что были при входе на экран
data class FilterState(
    val salary: String = "",
    val onlyWithSalary: Boolean = false,
    val isChanged: Boolean = false,
) {
    // "Сбросить" видна, если указан хотя бы один параметр
    val isResetVisible: Boolean
        get() = salary.isNotEmpty() || onlyWithSalary

    // "Применить" видна, если указан хотя бы один параметр (критерии ревью)
    // или фильтр изменили (ТЗ экрана): так после очистки можно перезапустить поиск без фильтра
    val isApplyVisible: Boolean
        get() = isResetVisible || isChanged
}
