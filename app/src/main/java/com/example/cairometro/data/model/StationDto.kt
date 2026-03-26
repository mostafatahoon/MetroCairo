package data.model

import domain.model.MetroLine

data class StationDto (
    val id: Int,
    val name: String,
    val line: String,
    val order: Int,
    val isTransfer : Boolean,
    val trasferlines: List<String>


)