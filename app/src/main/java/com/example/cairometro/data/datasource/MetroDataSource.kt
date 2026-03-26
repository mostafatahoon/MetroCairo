package data.datasource

import data.model.StationDto

interface MetroDataSource {

    fun loadStations(): List<StationDto>

    fun getTimeTravel(): Int
}