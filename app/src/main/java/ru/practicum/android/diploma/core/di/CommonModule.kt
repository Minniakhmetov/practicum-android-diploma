package ru.practicum.android.diploma.core.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.practicum.android.diploma.core.common.format.SalaryFormatter
import ru.practicum.android.diploma.core.common.resources.AndroidResourceProvider
import ru.practicum.android.diploma.core.common.resources.ResourceProvider

val commonModule = module {
    single<ResourceProvider> {
        AndroidResourceProvider(context = androidContext())
    }
    single {
        SalaryFormatter(resourceProvider = get())
    }
}
