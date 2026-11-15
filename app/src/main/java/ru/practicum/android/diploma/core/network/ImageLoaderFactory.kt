package ru.practicum.android.diploma.core.network

import coil3.ImageLoader
import coil3.PlatformContext
import coil3.network.okhttp.OkHttpNetworkFetcherFactory
import coil3.svg.SvgDecoder
import okhttp3.OkHttpClient
import ru.practicum.android.diploma.BuildConfig

// Загрузчик картинок Coil для логотипов работодателей.
// Логотипы приходят в SVG со стороннего хоста (upload.wikimedia.org), поэтому:
// - нужен SvgDecoder, без него Coil не декодирует SVG и всегда показывает плейсхолдер;
// - отдельный OkHttpClient без Bearer-токена API, чтобы токен не уходил на чужой сервер;
// - свой User-Agent: Wikimedia отвечает 403 на стандартный User-Agent OkHttp.
object ImageLoaderFactory {

    private val USER_AGENT = "PracticumAndroidDiploma/${BuildConfig.VERSION_NAME} (Android)"

    fun create(context: PlatformContext): ImageLoader {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .header("User-Agent", USER_AGENT)
                    .build()
                chain.proceed(request)
            }
            .build()

        return ImageLoader.Builder(context)
            .components {
                add(OkHttpNetworkFetcherFactory(callFactory = { okHttpClient }))
                add(SvgDecoder.Factory())
            }
            .build()
    }
}
