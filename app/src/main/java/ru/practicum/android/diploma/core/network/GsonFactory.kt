package ru.practicum.android.diploma.core.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder

object GsonFactory {
    fun create(): Gson {
        return GsonBuilder()
            .create()
    }
}
