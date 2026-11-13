package ru.practicum.android.diploma.favorites.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.common.format.SalaryFormatter
import ru.practicum.android.diploma.core.domain.interactor.FavoritesInteractor
import ru.practicum.android.diploma.core.domain.model.VacancyShort
import ru.practicum.android.diploma.favorites.presentation.state.FavoriteVacancyItem
import ru.practicum.android.diploma.favorites.presentation.state.FavoritesState

// ViewModel экрана "Избранное". Список подписан на Flow из БД, поэтому вакансия,
// добавленная или удалённая на экране вакансии, сразу появляется или исчезает здесь.
// Переход на экран вакансии по клику делает фрагмент: навигация - забота UI
class FavoritesViewModel(
    favoritesInteractor: FavoritesInteractor,
    private val salaryFormatter: SalaryFormatter,
) : ViewModel() {

    val state: StateFlow<FavoritesState> = favoritesInteractor.getFavorites()
        .map { vacancies -> vacancies.toState() }
        .catch { emit(FavoritesState.Error) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(STOP_TIMEOUT_MILLIS),
            initialValue = FavoritesState.Loading,
        )

    private fun List<VacancyShort>.toState(): FavoritesState =
        if (isEmpty()) {
            FavoritesState.Empty
        } else {
            FavoritesState.Content(
                vacancies = map { vacancy ->
                    FavoriteVacancyItem(
                        vacancy = vacancy,
                        salary = salaryFormatter.format(vacancy.salary, R.string.salary_not_specified),
                    )
                },
            )
        }

    private companion object {
        // Подписка на БД живёт ещё 5 секунд после ухода с экрана: при смене темы
        // (пересоздание Activity) список не перечитывается заново
        const val STOP_TIMEOUT_MILLIS = 5_000L
    }
}
