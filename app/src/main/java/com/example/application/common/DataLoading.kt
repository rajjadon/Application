package com.example.application.common

import com.example.application.common.extension.toSharedFlow
import kotlinx.coroutines.flow.MutableSharedFlow

class DataLoading {
    private val _isLoading = MutableSharedFlow<Boolean>()
    val isLoading = _isLoading.toSharedFlow()
    suspend fun setIsLoading(isLoading: Boolean) {
        _isLoading.emit(isLoading)
    }

    private val _apiError = MutableSharedFlow<String>()
    val apiError = _apiError.toSharedFlow()
    suspend fun setApiError(apiError: String) {
        _apiError.emit(apiError)
    }
}