package ru.practicum.android.diploma.core.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import ru.practicum.android.diploma.BuildConfig
import java.util.concurrent.TimeUnit

private const val CONNECT_TIMEOUT_SECONDS = 15L
private const val WRITE_TIMEOUT_SECONDS = 15L
private const val READ_TIMEOUT_SECONDS = 15L
private const val CALL_TIMEOUT_SECONDS = 20L

object OkHttpClientFactory {
    fun create(): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .connectTimeout(CONNECT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .callTimeout(CALL_TIMEOUT_SECONDS, TimeUnit.SECONDS)

        builder.addInterceptor { chain ->
            val originalRequest = chain.request()
            val builder = originalRequest.newBuilder()
                .header("Authorization", "Bearer ${BuildConfig.API_ACCESS_TOKEN}")
            val newRequest = builder.build()
            chain.proceed(newRequest)
        }

        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
                redactHeader("Authorization")
            }
            builder.addInterceptor(loggingInterceptor)
        }

        return builder.build()
    }
}
