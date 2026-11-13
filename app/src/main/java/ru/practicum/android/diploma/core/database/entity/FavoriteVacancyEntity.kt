package ru.practicum.android.diploma.core.database.entity

import androidx.room.Embedded
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
    @Embedded(prefix = "salary_")
    val salary: SalaryEntity?,
    @Embedded(prefix = "address_")
    val address: AddressEntity?,
    @Embedded(prefix = "experience_")
    val experience: ExperienceEntity?,
    @Embedded(prefix = "schedule_")
    val schedule: ScheduleEntity?,
    @Embedded(prefix = "employment_")
    val employment: EmploymentEntity?,
    @Embedded(prefix = "contacts_")
    val contacts: ContactsEntity?,
    @Embedded(prefix = "employer_")
    val employer: EmployerEntity,
    val area: String,
    val skills: List<String>,
    val shareUrl: String,
    val industryName: String,
    val addedAt: Long = System.currentTimeMillis()
)
