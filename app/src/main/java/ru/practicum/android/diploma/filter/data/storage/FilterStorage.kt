package ru.practicum.android.diploma.filter.data.storage

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import ru.practicum.android.diploma.core.domain.model.FilterSettings

class FilterStorage(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun read(): FilterSettings = FilterSettings(
        salary = if (prefs.contains(KEY_SALARY)) prefs.getInt(KEY_SALARY, 0) else null,
        onlyWithSalary = prefs.getBoolean(KEY_ONLY_WITH_SALARY, false)
    )
    fun write(settings: FilterSettings) {
        prefs.edit {
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
        const val KEY_SALARY = "salary"
        const val KEY_ONLY_WITH_SALARY = "only_with_salary"
    }
}
