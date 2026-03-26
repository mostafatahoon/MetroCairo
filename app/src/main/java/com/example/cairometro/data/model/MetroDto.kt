package data.model

data class MetroDto (
    val stations: List<StationDto>,
    val travel_time_between_stations_minutes: Int
)