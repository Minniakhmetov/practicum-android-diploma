package ru.practicum.android.diploma.vacancy.di

import org.koin.dsl.module
import retrofit2.Retrofit
import ru.practicum.android.diploma.vacancy.data.api.VacancyApi

val vacancyModule = module {
    single<VacancyApi> {
        get<Retrofit>().create(VacancyApi::class.java)
    }
}
