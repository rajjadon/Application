package com.example.application.data.networkWapper

import com.example.application.data.model.DataState
import com.example.application.data.model.ErrorResponse
import com.example.application.data.networkWapper.networkMapper.NetworkMapper
import com.squareup.moshi.Moshi
import retrofit2.HttpException
import java.net.SocketTimeoutException
import javax.inject.Inject

enum class ErrorCodes(val code: Int) {
    SocketTimeOut(-1)
}

open class SafeApiRequest
@Inject constructor(
    private val networkHelper: NetworkHelper,
    private val networkMapper: NetworkMapper
) {
    suspend fun <T : Any> apiRequest(dataRequest: suspend () -> T): DataState<T> {
        return try {
            if (networkHelper.isNetworkConnected()) {
                val invoke = dataRequest.invoke()
                networkMapper.mapFromServerResponse(invoke)
            } else {
                val throwable = NoInternetException("Please check your Internet Connection")
                DataState.Error(throwable, throwable.message.toString())
            }
        } catch (throwable: Throwable) {
            when (throwable) {
                is HttpException -> {
                    val errorResponse = convertErrorBody(throwable)
                    DataState.Error(
                        throwable,
                        errorMessage = errorResponse?.message.toString()
                    )
                }
                is SocketTimeoutException -> DataState.Error(
                    throwable,
                    getErrorMessage(ErrorCodes.SocketTimeOut.code)
                )

                else -> {
                    DataState.Error(throwable as Exception, throwable.message.toString())
                }
            }
        }
    }
}

private fun convertErrorBody(throwable: HttpException): ErrorResponse? {
    return try {
        throwable.response()?.errorBody()?.source()?.let {
            val moshiAdapter = Moshi.Builder().build().adapter(ErrorResponse::class.java)
            moshiAdapter.fromJson(it)
        }
    } catch (exception: Exception) {
        ErrorResponse(true.toString(), getErrorMessage(throwable.code()))
    }
}

private fun getErrorMessage(code: Int): String {
    return when (code) {
        ErrorCodes.SocketTimeOut.code -> "Timeout"
        400 -> "Bad Request"
        401 -> "Unauthorised"
        403 -> "Forbidden"
        404 -> "Not found"
        409 -> "Conflict"
        500 -> "Internal Server Error"
        else -> "Something went wrong"
    }
}