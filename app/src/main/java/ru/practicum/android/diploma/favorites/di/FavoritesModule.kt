package ru.practicum.android.diploma.favorites.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.favorites.presentation.viewmodel.FavoritesViewModel

val favoritesModule = module {
    viewModel {
        FavoritesViewModel(
            favoritesInteractor = get(),
            salaryFormatter = get()
        )
    }
}
