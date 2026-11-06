package ru.practicum.android.diploma.vacancy.domain.model

data class Contacts(
    val id: String,
    val name: String,
    val email: String,
    val phones: List<Phone>
)
