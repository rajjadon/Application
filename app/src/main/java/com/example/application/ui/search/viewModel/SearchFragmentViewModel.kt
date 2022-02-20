package com.example.application.ui.search.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.application.common.DataLoading
import com.example.application.common.extension.toSharedFlow
import com.example.application.data.model.DataState
import com.example.application.data.model.Search
import com.example.application.data.repo.searchRepo.SearchApiRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SearchFragmentViewModel @Inject constructor(
    private val searchApiRepo: SearchApiRepo,
    private val dataLoading: DataLoading
) : ViewModel() {

    private val _searchedMovies = MutableSharedFlow<List<Search>>()
    val movies: MutableList<Search> = emptyList<Search>().toMutableList()
    val searchedMovies = _searchedMovies.toSharedFlow()

    fun searchMovies(type: String, query: String, page: Int) {
        viewModelScope.launch {
            searchApiRepo.searchMovies(type, query, page).onEach {
                when (it) {
                    is DataState.Error -> {
                        dataLoading.setIsLoading(false)
                        dataLoading.setApiError(it.errorMessage)
                        Timber.e(it.errorMessage)
                    }
                    DataState.Loading -> dataLoading.setIsLoading(true)
                    is DataState.Success -> {
                        dataLoading.setIsLoading(false)
                        if (movies.isEmpty())
                            movies.clear()
                        movies.addAll(it.baseResponseData.search)
                        _searchedMovies.emit(movies)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }
}