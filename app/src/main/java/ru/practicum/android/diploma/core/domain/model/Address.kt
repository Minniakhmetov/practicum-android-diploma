package ru.practicum.android.diploma.core.domain.model

data class Address(
    val id: String,
    val city: String,
    val street: String,
    val building: String,
    val raw: String
)
