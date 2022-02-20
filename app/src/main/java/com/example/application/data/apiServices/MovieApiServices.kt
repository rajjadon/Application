package com.example.application.data.apiServices

import com.example.application.BuildConfig
import com.example.application.data.model.DetailsModel
import com.example.application.data.model.SearchModel
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiServices {
    @GET(BuildConfig.API_BASE_URL)
    suspend fun searchMovies(
        @Query("type") type: String,
        @Query("page") page: Int,
        @Query("s") query: String
    ): SearchModel

    @GET(BuildConfig.API_BASE_URL)
    suspend fun getMovieDetail(
        @Query("plot") plot: String,
        @Query("tittle") tittle: String
    ): DetailsModel
}