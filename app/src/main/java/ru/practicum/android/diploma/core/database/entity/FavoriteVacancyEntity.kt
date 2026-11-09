package ru.practicum.android.diploma.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.practicum.android.diploma.core.database.entity.model.AddressEntity
import ru.practicum.android.diploma.core.database.entity.model.ContactsEntity
import ru.practicum.android.diploma.core.database.entity.model.EmployerEntity
import ru.practicum.android.diploma.core.database.entity.model.EmploymentEntity
import ru.practicum.android.diploma.core.database.entity.model.ExperienceEntity
import ru.practicum.android.diploma.core.database.entity.model.SalaryEntity
import ru.practicum.android.diploma.core.database.entity.model.ScheduleEntity

@Entity(tableName = "favorite_vacancies")
data class FavoriteVacancyEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val description: String,
    val salary: SalaryEntity?,
    val address: AddressEntity?,
    val experience: ExperienceEntity?,
    val schedule: ScheduleEntity?,
    val employment: EmploymentEntity?,
    val contacts: ContactsEntity?,
    val employer: EmployerEntity,
    val area: String,
    val skills: List<String>,
    val shareUrl: String,
    val industryName: String,
    val addedAt: Long = System.currentTimeMillis()
)
