package ru.practicum.android.diploma.favorites.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.core.domain.interactor.FavoritesInteractor
import ru.practicum.android.diploma.favorites.data.repository.FavoritesRepositoryImpl
import ru.practicum.android.diploma.favorites.domain.interactor.FavoritesInteractorImpl
import ru.practicum.android.diploma.favorites.domain.repository.FavoritesRepository
import ru.practicum.android.diploma.favorites.presentation.viewmodel.FavoritesViewModel

val favoritesModule = module {
    single<FavoritesRepository> {
        FavoritesRepositoryImpl(dao = get())
    }

    single<FavoritesInteractor> {
        FavoritesInteractorImpl(repository = get())
    }

    viewModel {
        FavoritesViewModel(
            favoritesInteractor = get(),
            salaryFormatter = get()
        )
    }
}
