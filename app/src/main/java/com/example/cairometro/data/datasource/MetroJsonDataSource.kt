package data.datasource

import com.example.cairometroapp.MainActivity
import com.google.gson.Gson
import data.model.MetroDto
import data.model.StationDto
import kotlin.getValue
import java.io.File

class MetroJsonDataSource(private val path: MainActivity) : MetroDataSource{

    private val gson = Gson()
    private val dto by lazy {
        gson.fromJson(File(path).readText(), MetroDto::class.java)
    }

    override fun loadStations(): List<StationDto> {
        return dto.stations
     }

    override fun getTimeTravel(): Int {
        return dto.travel_time_between_stations_minutes
    }
}