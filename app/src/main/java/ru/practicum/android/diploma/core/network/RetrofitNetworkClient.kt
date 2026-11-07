package ru.practicum.android.diploma.core.network

import android.util.Log
import com.google.gson.JsonParseException
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ru.practicum.android.diploma.core.common.result.ErrorType
import ru.practicum.android.diploma.core.common.result.Resource
import java.io.IOException
import java.net.HttpURLConnection

class RetrofitNetworkClient(
    private val networkStatus: NetworkStatus
) : NetworkClient {
    override suspend fun <T> execute(
        request: suspend () -> T
    ): Resource<T> {
        if (!networkStatus.isOnline()) {
            return Resource.Error(ErrorType.NoInternet)
        }
        return withContext(Dispatchers.IO) {
            try {
                val result = request()
                Resource.Success(result)
            } catch (exception: CancellationException) {
                throw exception
            } catch (exception: IOException) {
                Log.w(TAG, "Network request failed", exception)
                Resource.Error(ErrorType.NoInternet)
            } catch (exception: HttpException) {
                Log.w(TAG, "HTTP request failed: ${exception.code()}", exception)
                if (exception.code() == HttpURLConnection.HTTP_NOT_FOUND) {
                    Resource.Error(ErrorType.NotFound)
                } else {
                    Resource.Error(ErrorType.Server)
                }
            } catch (exception: JsonParseException) {
                Log.w(TAG, "Failed to parse server response", exception)
                Resource.Error(ErrorType.Server)
            }
        }
    }

    private companion object {
        const val TAG = "RetrofitNetworkClient"
    }
}
