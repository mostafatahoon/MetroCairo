package com.example.metroapp.domain.use_case

import domain.model.Station
import domain.repository.MetroRepository

class GetAllStationsUseCase(
    private val repository: MetroRepository
) {

    operator fun invoke(): List<Station>{
        return repository.getStations()
    }
}