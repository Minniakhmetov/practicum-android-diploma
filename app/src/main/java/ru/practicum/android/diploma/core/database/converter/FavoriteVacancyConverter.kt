package ru.practicum.android.diploma.core.database.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class FavoriteVacancyConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromSkills(value: List<String>): String =
        gson.toJson(value)

    @TypeConverter
    fun toSkills(value: String): List<String> {
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, type)
    }
}
