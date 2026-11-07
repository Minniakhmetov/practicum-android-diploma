package ru.practicum.android.diploma.core.network

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ru.practicum.android.diploma.core.common.result.ErrorType
import ru.practicum.android.diploma.core.common.result.Resource
import java.io.IOException

class RetrofitNetworkClient(
    private val networkStatus: NetworkStatus
) : NetworkClient {
    override suspend fun <T> execute(
        request: suspend () -> T
    ): Resource<T> {
        return withContext(Dispatchers.IO) {
            if (!networkStatus.isOnline()) {
                return@withContext Resource.Error(ErrorType.NoInternet)
            }

            try {
                val result = request()
                Resource.Success(result)
            } catch (exception: CancellationException) {
                throw exception
            } catch (exception: IOException) {
                Resource.Error(ErrorType.NoInternet)
            } catch (exception: HttpException) {
                if (exception.code() == 404) {
                    Resource.Error(ErrorType.NotFound)
                } else {
                    Resource.Error(ErrorType.Server)
                }
            }
        }
    }
}

