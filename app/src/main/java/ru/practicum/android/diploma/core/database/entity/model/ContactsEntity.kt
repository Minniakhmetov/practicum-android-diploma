package ru.practicum.android.diploma.core.database.entity.model

data class ContactsEntity(
    val id: String,
    val name: String,
    val email: String,
    val phones: List<PhoneEntity>
)
