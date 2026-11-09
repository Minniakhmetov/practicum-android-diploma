package ru.practicum.android.diploma.vacancy.presentation.state

// Одноразовые действия экрана вакансии: выполняются один раз и не повторяются при возврате на экран
sealed interface VacancyEvent {
    data class Share(val url: String) : VacancyEvent
    data class SendEmail(val email: String) : VacancyEvent
    data class Call(val phone: String) : VacancyEvent
}
