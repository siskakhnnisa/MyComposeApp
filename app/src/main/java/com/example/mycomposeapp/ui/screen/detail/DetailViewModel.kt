package com.example.mycomposeapp.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mycomposeapp.data.SeriesRepository
import com.example.mycomposeapp.model.Series
import com.example.mycomposeapp.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: SeriesRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<Series>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<Series>>
        get() = _uiState

    fun getSeriesById(seriesId: Int) {
        viewModelScope.launch {
            _uiState.value = UiState.Success(repository.getSeriesById(seriesId))
        }
    }

    fun updateStatusBookmarked(seriesId: Int, status: Boolean) {
        viewModelScope.launch {
            repository.updateStatusFavorite(seriesId, status)
        }
    }

}