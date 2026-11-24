package ru.practicum.android.diploma.filter.di

import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.core.domain.interactor.FilterInteractor
import ru.practicum.android.diploma.filter.data.repository.FilterSettingsRepositoryImpl
import ru.practicum.android.diploma.filter.data.storage.FilterStorage
import ru.practicum.android.diploma.filter.domain.interactor.FilterInteractorImpl
import ru.practicum.android.diploma.filter.domain.repository.FilterSettingsRepository
import ru.practicum.android.diploma.filter.presentation.viewmodel.FilterViewModel

val filterModule = module {
    single {
        FilterStorage(androidContext())
    }

    single<FilterSettingsRepository> {
        FilterSettingsRepositoryImpl(get())
    }

    single<FilterInteractor> {
        FilterInteractorImpl(get())
    }

    viewModel {
        FilterViewModel(filterInteractor = get())
    }
}
