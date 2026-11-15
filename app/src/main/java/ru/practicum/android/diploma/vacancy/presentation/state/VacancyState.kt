package ru.practicum.android.diploma.vacancy.presentation.state

import ru.practicum.android.diploma.core.domain.model.VacancyDetails

// Состояние экрана вакансии. Начальное - Loading, чтобы до ответа сервера не мигала ошибка
sealed interface VacancyState {
    data object Loading : VacancyState

    // salary - готовая строка из SalaryFormatter: экран не форматирует данные сам
    data class Content(
        val vacancy: VacancyDetails,
        val salary: String,
    ) : VacancyState
    data object NoInternet : VacancyState
    data object ServerError : VacancyState

    // Сервер ответил 404: вакансия удалена или не существует
    data object NotFound : VacancyState
}
