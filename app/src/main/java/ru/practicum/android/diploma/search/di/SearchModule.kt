package ru.practicum.android.diploma.search.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import ru.practicum.android.diploma.search.data.api.SearchApi
import ru.practicum.android.diploma.search.data.repository.SearchRepositoryImpl
import ru.practicum.android.diploma.search.domain.interactor.SearchInteractor
import ru.practicum.android.diploma.search.domain.interactor.SearchInteractorImpl
import ru.practicum.android.diploma.search.domain.repository.SearchRepository
import ru.practicum.android.diploma.search.presentation.viewmodel.SearchViewModel

val searchModule = module {
    single<SearchApi> {
        get<Retrofit>().create(SearchApi::class.java)
    }

    single<SearchRepository> {
        SearchRepositoryImpl(
            networkClient = get(),
            searchApi = get()
        )
    }

    single<SearchInteractor> {
        SearchInteractorImpl(
            searchRepository = get()
        )
    }

    viewModel {
        SearchViewModel(
            searchInteractor = get(),
        )
    }
}
