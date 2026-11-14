package ru.practicum.android.diploma.filter.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import ru.practicum.android.diploma.core.domain.interactor.FilterInteractor
import ru.practicum.android.diploma.filter.data.api.FilterApi
import ru.practicum.android.diploma.filter.data.repository.FilterDictionariesRepositoryImpl
import ru.practicum.android.diploma.filter.data.repository.FilterSettingsRepositoryImpl
import ru.practicum.android.diploma.filter.data.storage.FilterStorage
import ru.practicum.android.diploma.filter.domain.interactor.FilterInteractorImpl
import ru.practicum.android.diploma.filter.domain.repository.FilterDictionariesRepository
import ru.practicum.android.diploma.filter.domain.repository.FilterSettingsRepository

val filterModule = module {
    single<FilterApi> {
        get<Retrofit>().create(FilterApi::class.java)
    }

    single {
        FilterStorage(androidContext())
    }

    single<FilterSettingsRepository> {
        FilterSettingsRepositoryImpl(get())
    }

    single<FilterDictionariesRepository> {
        FilterDictionariesRepositoryImpl(get(), get())
    }

    single<FilterInteractor> {
        FilterInteractorImpl(get(), get())
    }
}
