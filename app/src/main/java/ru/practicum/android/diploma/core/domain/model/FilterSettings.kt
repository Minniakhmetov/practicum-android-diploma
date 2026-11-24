package ru.practicum.android.diploma.core.domain.model

data class FilterSettings(
    val salary: Int? = null,
    val onlyWithSalary: Boolean = false
) {
    val isEmpty: Boolean
        get() {
            return salary == null && !onlyWithSalary
        }
}
