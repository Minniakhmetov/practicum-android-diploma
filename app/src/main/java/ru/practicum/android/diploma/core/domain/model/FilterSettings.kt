package ru.practicum.android.diploma.core.domain.model

data class FilterSettings(
    val cityId: Int? = null,
    val industryId: Int? = null,
    val salary: Int? = null,
    val onlyWithSalary: Boolean = false
) {
    val isEmpty: Boolean
        get() {
            return cityId == null &&
                industryId == null &&
                salary == null &&
                !onlyWithSalary
        }
}
