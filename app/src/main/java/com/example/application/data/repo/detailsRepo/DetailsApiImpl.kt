package com.example.application.data.repo.detailsRepo

import com.example.application.data.apiServices.MovieApiServices
import com.example.application.data.model.DataState
import com.example.application.data.networkWapper.SafeApiRequest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DetailsApiImpl @Inject constructor(
    private val safeApiRequest: SafeApiRequest,
    private val movieApiServices: MovieApiServices
) : DetailsApiRepo {
    override suspend fun getMovieDetail(plot: String, tittle: String) = flow {
        emit(DataState.Loading)
        emit(safeApiRequest.apiRequest { movieApiServices.getMovieDetail(plot, tittle) })
    }
}