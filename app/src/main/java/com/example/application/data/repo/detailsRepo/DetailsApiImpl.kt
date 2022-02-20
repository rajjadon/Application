package com.example.application.data.repo.detailsRepo

import com.example.application.data.model.DataState
import com.example.application.data.model.DetailsModel
import kotlinx.coroutines.flow.Flow

class DetailsApiImpl : DetailsApiRepo {
    override suspend fun getDetails(plot: String, tittle: String): Flow<DataState<DetailsModel>> {
        TODO("Not yet implemented")
    }
}