package com.example.application.ui.details.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.application.common.DataLoading
import com.example.application.common.extension.toSharedFlow
import com.example.application.data.model.DataState
import com.example.application.data.model.DetailsModel
import com.example.application.data.repo.detailsRepo.DetailsApiRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val detailsApiRepo: DetailsApiRepo,
    private val dataLoading: DataLoading
) : ViewModel() {

    private val _detailsModel = MutableSharedFlow<DetailsModel>()
    val detailsModel = _detailsModel.toSharedFlow()

    fun getMovieDetail(plot: String, tittle: String) {
        viewModelScope.launch {
            detailsApiRepo.getMovieDetail(plot, tittle).onEach {
                when (it) {
                    is DataState.Error -> {
                        dataLoading.setIsLoading(false)
                        dataLoading.setApiError(it.errorMessage)
                        Timber.e(it.errorMessage)
                    }
                    DataState.Loading -> dataLoading.setIsLoading(true)
                    is DataState.Success -> {
                        dataLoading.setIsLoading(false)
                        _detailsModel.emit(it.baseResponseData)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }
}