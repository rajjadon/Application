package com.example.application.repo.searchRepo

import com.example.application.data.model.DataState
import com.example.application.data.model.SearchModel
import kotlinx.coroutines.flow.Flow

class SearchApiImpl : SearchApiRepo {
    override suspend fun search(type: String, query: String): Flow<DataState<SearchModel>> {
        TODO("Not yet implemented")
    }
}