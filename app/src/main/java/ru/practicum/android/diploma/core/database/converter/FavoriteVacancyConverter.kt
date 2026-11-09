package ru.practicum.android.diploma.core.database.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.practicum.android.diploma.core.database.entity.model.AddressEntity
import ru.practicum.android.diploma.core.database.entity.model.ContactsEntity
import ru.practicum.android.diploma.core.database.entity.model.EmployerEntity
import ru.practicum.android.diploma.core.database.entity.model.EmploymentEntity
import ru.practicum.android.diploma.core.database.entity.model.ExperienceEntity
import ru.practicum.android.diploma.core.database.entity.model.SalaryEntity
import ru.practicum.android.diploma.core.database.entity.model.ScheduleEntity

class FavoriteVacancyConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromSalary(value: SalaryEntity?): String? =
        value?.let { gson.toJson(it) }

    @TypeConverter
    fun toSalary(value: String?): SalaryEntity? =
        value?.let { gson.fromJson(it, SalaryEntity::class.java) }

    @TypeConverter
    fun fromAddress(value: AddressEntity?): String? =
        value?.let { gson.toJson(it) }

    @TypeConverter
    fun toAddress(value: String?): AddressEntity? =
        value?.let { gson.fromJson(it, AddressEntity::class.java) }

    @TypeConverter
    fun fromExperience(value: ExperienceEntity?): String? =
        value?.let { gson.toJson(it) }

    @TypeConverter
    fun toExperience(value: String?): ExperienceEntity? =
        value?.let { gson.fromJson(it, ExperienceEntity::class.java) }

    @TypeConverter
    fun fromSchedule(value: ScheduleEntity?): String? =
        value?.let { gson.toJson(it) }

    @TypeConverter
    fun toSchedule(value: String?): ScheduleEntity? =
        value?.let { gson.fromJson(it, ScheduleEntity::class.java) }

    @TypeConverter
    fun fromEmployment(value: EmploymentEntity?): String? =
        value?.let { gson.toJson(it) }

    @TypeConverter
    fun toEmployment(value: String?): EmploymentEntity? =
        value?.let { gson.fromJson(it, EmploymentEntity::class.java) }

    @TypeConverter
    fun fromContacts(value: ContactsEntity?): String? =
        value?.let { gson.toJson(it) }

    @TypeConverter
    fun toContacts(value: String?): ContactsEntity? =
        value?.let { gson.fromJson(it, ContactsEntity::class.java) }

    @TypeConverter
    fun fromEmployer(value: EmployerEntity): String =
        gson.toJson(value)

    @TypeConverter
    fun toEmployer(value: String): EmployerEntity =
        gson.fromJson(value, EmployerEntity::class.java)

    @TypeConverter
    fun fromSkills(value: List<String>): String =
        gson.toJson(value)

    @TypeConverter
    fun toSkills(value: String): List<String> {
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, type)
    }
}
