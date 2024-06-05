package com.example.mycomposeapp.ui.screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mycomposeapp.data.SeriesRepository
import com.example.mycomposeapp.model.Series
import com.example.mycomposeapp.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch


class HomeViewModel(
    private val repository: SeriesRepository
) : ViewModel() {
    private var currentKeyword = ""
    private val _uiState: MutableStateFlow<UiState<List<Series>>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Series>>>
        get() = _uiState

    fun getAllSeries(keyword: String = "") {
        viewModelScope.launch {
            repository.getAllSeries()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { series ->
                    _uiState.value = UiState.Success(series)
                }
        }
    }

    fun searchSeries(keyword: String) {
        currentKeyword = keyword
        getAllSeries(keyword)
    }
}