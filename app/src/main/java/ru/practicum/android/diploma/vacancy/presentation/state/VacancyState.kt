package ru.practicum.android.diploma.vacancy.presentation.state

import ru.practicum.android.diploma.core.domain.model.VacancyDetails

// Состояние экрана вакансии. Начальное - Loading, чтобы до ответа сервера не мигала ошибка
sealed interface VacancyState {
    data object Loading : VacancyState
    data class Content(val vacancy: VacancyDetails) : VacancyState
    data object NoInternet : VacancyState
    data object ServerError : VacancyState

    // Сервер ответил 404: вакансия удалена или не существует
    data object NotFound : VacancyState
}
