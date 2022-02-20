package com.example.application.data.repo.searchRepo

import com.example.application.data.model.DataState
import com.example.application.data.model.SearchModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector

interface SearchApiRepo {
    suspend fun searchMovies(type: String, query: String, page : Int): Flow<DataState<SearchModel>>
}