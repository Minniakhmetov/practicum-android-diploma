package ru.practicum.android.diploma.vacancy.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.common.format.SalaryFormatter
import ru.practicum.android.diploma.core.common.result.ErrorType
import ru.practicum.android.diploma.core.common.result.Resource
import ru.practicum.android.diploma.core.domain.interactor.FavoritesInteractor
import ru.practicum.android.diploma.core.domain.model.VacancyDetails
import ru.practicum.android.diploma.vacancy.domain.interactor.VacancyInteractor
import ru.practicum.android.diploma.vacancy.presentation.state.VacancyState

// ViewModel экрана вакансии. Вакансия загружается один раз при создании ViewModel,
// поэтому возврат на экран (из браузера, "Поделиться", звонилки) не сбрасывает состояние
class VacancyViewModel(
    private val vacancyId: String,
    private val vacancyInteractor: VacancyInteractor,
    private val favoritesInteractor: FavoritesInteractor,
    private val salaryFormatter: SalaryFormatter,
) : ViewModel() {

    private val _state = MutableStateFlow<VacancyState>(VacancyState.Loading)
    val state: StateFlow<VacancyState> = _state.asStateFlow()

    // Признак "в избранном" подписан на БД: иконка меняется сразу после нажатия
    // и остаётся верной после возврата с экрана "Избранное"
    val isFavorite: StateFlow<Boolean> = favoritesInteractor.isFavorite(vacancyId)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(STOP_TIMEOUT_MILLIS),
            initialValue = false,
        )

    init {
        loadVacancy()
    }

    fun onShareClick() {
        val vacancy = (_state.value as? VacancyState.Content)?.vacancy ?: return
        vacancyInteractor.shareVacancy(vacancy.shareUrl)
    }

    // Добавить или удалить можно только загруженную вакансию: в БД сохраняется она целиком
    fun onFavoriteClick() {
        val vacancy = (_state.value as? VacancyState.Content)?.vacancy ?: return
        viewModelScope.launch {
            if (isFavorite.value) {
                favoritesInteractor.removeFromFavorites(vacancy.id)
            } else {
                favoritesInteractor.addToFavorites(vacancy)
            }
        }
    }

    fun onEmailClick(email: String) {
        vacancyInteractor.sendEmail(email)
    }

    fun onPhoneClick(phone: String) {
        vacancyInteractor.callPhone(phone)
    }

    private fun loadVacancy() {
        viewModelScope.launch {
            _state.value = when (val result = vacancyInteractor.getVacancyDetails(vacancyId)) {
                is Resource.Success -> result.data.toContent()
                is Resource.Error -> onLoadError(result.type)
            }
        }
    }

    // Нет сети или сервер не ответил - показываем сохранённую копию из избранного, если она есть.
    // 404 - вакансию удалили на сервере: по ТЗ удаляем её и из избранного и показываем ошибку
    private suspend fun onLoadError(type: ErrorType): VacancyState {
        if (type == ErrorType.NotFound) {
            favoritesInteractor.removeFromFavorites(vacancyId)
            return VacancyState.NotFound
        }
        return favoritesInteractor.getFavoriteVacancy(vacancyId)?.toContent() ?: type.toState()
    }

    private fun VacancyDetails.toContent() = VacancyState.Content(
        vacancy = this,
        salary = salaryFormatter.format(salary, R.string.salary_not_specified),
    )

    private fun ErrorType.toState(): VacancyState = when (this) {
        ErrorType.NoInternet -> VacancyState.NoInternet
        ErrorType.Server -> VacancyState.ServerError
        ErrorType.NotFound -> VacancyState.NotFound
    }

    private companion object {
        // Подписка на БД живёт ещё 5 секунд после ухода с экрана (смена темы, звонилка)
        const val STOP_TIMEOUT_MILLIS = 5_000L
    }
}
