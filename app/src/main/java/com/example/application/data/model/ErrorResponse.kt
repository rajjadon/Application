package com.example.application.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ErrorResponse(

    @Json(name = "Response")
    val response: Boolean,
    @Json(name = "message")
    val message: String,
)