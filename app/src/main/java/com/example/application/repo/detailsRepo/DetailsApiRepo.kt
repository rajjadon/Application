package com.example.application.repo.detailsRepo

import com.example.application.data.model.DataState
import com.example.application.data.model.DetailsModel
import kotlinx.coroutines.flow.Flow

interface DetailsApiRepo {
    suspend fun getDetails(plot: String, tittle: String): Flow<DataState<DetailsModel>>
}