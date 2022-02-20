package com.example.application.data.networkWapper.networkMapper

import com.example.application.data.model.DataState
import com.example.application.data.networkWapper.ApiException
import com.google.gson.Gson
import org.json.JSONObject
import timber.log.Timber
import javax.inject.Inject

class NetworkMapperImpl @Inject constructor() : NetworkMapper {
    override suspend fun <T> mapFromServerResponse(apiResponse: T): DataState<T> {
        val response = JSONObject(Gson().toJson(apiResponse))
        Timber.e(response.toString())
        return when (response.getString("response")) {
            "True" -> DataState.Success(apiResponse)
            "False" -> DataState.Success(apiResponse)
            else -> {
                val throwable = ApiException("Something went wrong")
                DataState.Error(throwable, throwable.message.toString())
            }
        }
    }
}