package com.example.cairometroapp.presentation.ui.home

import com.example.cairometroapp.domain.model.Station

data class HomeUiState(
    val startStation: String = "",
    val endStation: String = "",
    val stations: List<Station> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
