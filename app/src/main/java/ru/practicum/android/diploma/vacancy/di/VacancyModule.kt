package ru.practicum.android.diploma.vacancy.di

import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import ru.practicum.android.diploma.vacancy.data.api.VacancyApi
import ru.practicum.android.diploma.vacancy.data.repository.VacancyDetailsRepositoryImpl
import ru.practicum.android.diploma.vacancy.data.sharing.ExternalNavigatorImpl
import ru.practicum.android.diploma.vacancy.domain.interactor.VacancyInteractor
import ru.practicum.android.diploma.vacancy.domain.interactor.VacancyInteractorImpl
import ru.practicum.android.diploma.vacancy.domain.repository.VacancyDetailsRepository
import ru.practicum.android.diploma.vacancy.domain.sharing.ExternalNavigator
import ru.practicum.android.diploma.vacancy.presentation.viewmodel.VacancyViewModel

val vacancyModule = module {
    single<VacancyApi> {
        get<Retrofit>().create(VacancyApi::class.java)
    }

    single<VacancyDetailsRepository> {
        VacancyDetailsRepositoryImpl(
            networkClient = get(),
            vacancyApi = get()
        )
    }

    single<ExternalNavigator> {
        ExternalNavigatorImpl(context = androidContext())
    }

    single<VacancyInteractor> {
        VacancyInteractorImpl(
            repository = get(),
            externalNavigator = get()
        )
    }

    // vacancyId приходит из аргументов навигации: viewModel { parametersOf(args.vacancyId) }
    viewModel { (vacancyId: String) ->
        VacancyViewModel(
            vacancyId = vacancyId,
            vacancyInteractor = get(),
            salaryFormatter = get()
        )
    }
}
