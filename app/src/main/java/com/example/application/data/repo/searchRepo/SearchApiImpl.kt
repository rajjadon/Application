package com.example.application.data.repo.searchRepo

import com.example.application.data.apiServices.MovieApiServices
import com.example.application.data.model.DataState
import com.example.application.data.networkWapper.SafeApiRequest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchApiImpl @Inject constructor(
    private val safeApiRequest: SafeApiRequest,
    private val movieApiServices: MovieApiServices
) : SearchApiRepo {
    override suspend fun searchMovies(
        type: String,
        query: String,
        page: Int
    ) = flow {
        emit(DataState.Loading)
        emit(safeApiRequest.apiRequest { movieApiServices.searchMovies(type, page, query) })
    }
}