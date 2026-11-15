package ru.practicum.android.diploma.favorites.presentation.state

import ru.practicum.android.diploma.core.domain.model.VacancyShort

// Состояние экрана "Избранное". Начальное - Loading, чтобы до ответа БД не мигала заглушка "пусто"
sealed interface FavoritesState {
    data object Loading : FavoritesState
    data object Empty : FavoritesState

    // Ошибка чтения списка из БД
    data object Error : FavoritesState

    data class Content(
        val vacancies: List<FavoriteVacancyItem>,
    ) : FavoritesState
}

// salary - готовая строка из SalaryFormatter: экран не форматирует данные сам
data class FavoriteVacancyItem(
    val vacancy: VacancyShort,
    val salary: String,
)
