package ru.practicum.android.diploma.core.domain.model

data class Contacts(
    val id: String,
    val name: String,
    val email: String,
    val phones: List<Phone>
)
