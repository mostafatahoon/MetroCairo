package com.example.cairometroapp.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cairometroapp.domain.repository.MetroRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: MetroRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadStations()
    }

    private fun loadStations() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                // repository.getStations() should return List<Station>
                val stations = repository.getStations()
                _uiState.update { it.copy(stations = stations, isLoading = false) }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message, isLoading = false) }
            }
        }
    }

    fun onStartStationChanged(name: String) {
        _uiState.update { it.copy(startStation = name) }
    }

    fun onEndStationChanged(name: String) {
        _uiState.update { it.copy(endStation = name) }
    }

    fun swapStations() {
        _uiState.update { 
            it.copy(
                startStation = it.endStation,
                endStation = it.startStation
            )
        }
    }
}
