package domain.repository

import domain.model.Station

interface MetroRepository {

    fun getStations(): List<Station>

    fun getTravelTime(): Int

}