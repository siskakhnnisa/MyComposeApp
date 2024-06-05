package com.example.mycomposeapp.ui.screen.profil

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mycomposeapp.data.SeriesRepository
import com.example.mycomposeapp.model.Profil
import com.example.mycomposeapp.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfilViewModel(
    private val repository: SeriesRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<Profil>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<Profil>>
        get() = _uiState

    fun getAbout() {
        viewModelScope.launch {
            repository.getProfil()
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getProfil())
        }
    }

}