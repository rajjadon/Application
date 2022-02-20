package com.example.application.data.networkWapper.networkMapper

import com.example.application.data.model.DataState

interface NetworkMapper {
    suspend fun <T> mapFromServerResponse(apiResponse: T): DataState<T>
}