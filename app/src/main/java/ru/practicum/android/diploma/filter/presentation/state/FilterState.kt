package ru.practicum.android.diploma.filter.presentation.state

// Состояние экрана "Настройки фильтрации".
// Зарплата хранится строкой, как она введена в поле: пустая строка - зарплата не указана.
// isChanged - настройки отличаются от тех, с которыми получена текущая выдача поиска
data class FilterState(
    val salary: String = "",
    val onlyWithSalary: Boolean = false,
    val isChanged: Boolean = false,
) {
    // "Сбросить" видна, если указан хотя бы один параметр
    val isResetVisible: Boolean
        get() = salary.isNotEmpty() || onlyWithSalary

    // "Применить" видна, если указан хотя бы один параметр (критерии ревью)
    // или фильтр не совпадает с текущей выдачей (ТЗ экрана): так после очистки фильтра,
    // в том числе с выходом по "Назад", можно перезапустить поиск без фильтра
    val isApplyVisible: Boolean
        get() = isResetVisible || isChanged
}
