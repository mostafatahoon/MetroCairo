package com.example.cairometroapp.presentation.ui.route

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cairometroapp.domain.model.Station
import com.example.cairometroapp.domain.repository.MetroRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class RouteUiState(
    val stations: List<Station> = emptyList(),
    val fare: Int = 0,
    val time: Int = 0,
    val isLoading: Boolean = false,
    val error: String? = null
)

class RouteResultViewModel(
    private val repository: MetroRepository,
    private val start: String,
    private val end: String
) : ViewModel() {

    private val _uiState = MutableStateFlow(RouteUiState())
    val uiState: StateFlow<RouteUiState> = _uiState.asStateFlow()

    init {
        calculateRoute()
    }

    private fun calculateRoute() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val route = repository.findRoute(start, end)
                val fare = repository.calculateFare(route.size)
                val time = repository.calculateTime(route.size)
                _uiState.update { 
                    it.copy(
                        stations = route,
                        fare = fare,
                        time = time,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message, isLoading = false) }
            }
        }
    }
}
