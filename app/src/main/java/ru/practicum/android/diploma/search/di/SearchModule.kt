package ru.practicum.android.diploma.search.di

import org.koin.dsl.module
import retrofit2.Retrofit
import ru.practicum.android.diploma.search.data.api.SearchApi

val searchModule = module {
    single<SearchApi> {
        get<Retrofit>().create(SearchApi::class.java)
    }
}
