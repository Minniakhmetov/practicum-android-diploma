package ru.practicum.android.diploma.vacancy.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.common.format.SalaryFormatter
import ru.practicum.android.diploma.core.common.result.ErrorType
import ru.practicum.android.diploma.core.common.result.Resource
import ru.practicum.android.diploma.vacancy.domain.interactor.VacancyInteractor
import ru.practicum.android.diploma.vacancy.presentation.state.VacancyState

// ViewModel экрана вакансии. Вакансия загружается один раз при создании ViewModel,
// поэтому возврат на экран (из браузера, "Поделиться", звонилки) не сбрасывает состояние
class VacancyViewModel(
    private val vacancyId: String,
    private val vacancyInteractor: VacancyInteractor,
    private val salaryFormatter: SalaryFormatter,
) : ViewModel() {

    private val _state = MutableStateFlow<VacancyState>(VacancyState.Loading)
    val state: StateFlow<VacancyState> = _state.asStateFlow()

    init {
        loadVacancy()
    }

    fun onShareClick() {
        val vacancy = (_state.value as? VacancyState.Content)?.vacancy ?: return
        vacancyInteractor.shareVacancy(vacancy.shareUrl)
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
                is Resource.Success -> VacancyState.Content(
                    vacancy = result.data,
                    salary = salaryFormatter.format(result.data.salary, R.string.salary_not_specified),
                )
                is Resource.Error -> result.type.toState()
            }
        }
    }

    private fun ErrorType.toState(): VacancyState = when (this) {
        ErrorType.NoInternet -> VacancyState.NoInternet
        ErrorType.Server -> VacancyState.ServerError
        ErrorType.NotFound -> VacancyState.NotFound
    }
}
