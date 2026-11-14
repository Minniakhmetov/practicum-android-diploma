package ru.practicum.android.diploma.filter.data.storage

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import ru.practicum.android.diploma.core.domain.model.FilterSettings

class FilterStorage(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun read(): FilterSettings = FilterSettings(
        cityId = readIntOrNull(KEY_CITY_ID),
        industryId = readIntOrNull(KEY_INDUSTRY_ID),
        salary = readIntOrNull(KEY_SALARY),
        onlyWithSalary = prefs.getBoolean(KEY_ONLY_WITH_SALARY, false)
    )

    private fun readIntOrNull(key: String): Int? =
        if (prefs.contains(key)) prefs.getInt(key, 0) else null

    fun write(settings: FilterSettings) {
        prefs.edit {
            if (settings.cityId != null) {
                putInt(KEY_CITY_ID, settings.cityId)
            } else {
                remove(KEY_CITY_ID)
            }
            if (settings.industryId != null) {
                putInt(KEY_INDUSTRY_ID, settings.industryId)
            } else {
                remove(KEY_INDUSTRY_ID)
            }
            if (settings.salary != null) {
                putInt(KEY_SALARY, settings.salary)
            } else {
                remove(KEY_SALARY)
            }
            putBoolean(KEY_ONLY_WITH_SALARY, settings.onlyWithSalary)
        }
    }

    fun clear() {
        prefs.edit { clear() }
    }

    private companion object {
        const val PREFS_NAME = "filter_prefs"
        const val KEY_CITY_ID = "city_id"
        const val KEY_INDUSTRY_ID = "industry_id"
        const val KEY_SALARY = "salary"
        const val KEY_ONLY_WITH_SALARY = "only_with_salary"
    }
}
