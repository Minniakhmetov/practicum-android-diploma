package ru.practicum.android.diploma.core.database.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.practicum.android.diploma.core.database.entity.model.PhoneEntity

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

    @TypeConverter
    fun fromPhones(value: List<PhoneEntity>): String =
        gson.toJson(value)

    @TypeConverter
    fun toPhones(value: String): List<PhoneEntity> {
        if (value.isBlank()) return emptyList()
        val type = object : TypeToken<List<PhoneEntity>>() {}.type
        return gson.fromJson<List<PhoneEntity>>(value, type)
            ?: emptyList()
    }
}
