package data.mapper

import data.model.StationDto
import domain.model.MetroLine
import domain.model.Station

object MetroMapper {

    fun toDomain(dto: StationDto): Station {
        return Station(
            id = dto.id,
            name = dto.name,
            line = toMetroLine(dto.line),
            order = dto.order,
            isTransfer = dto.isTransfer,
            trasferlines = dto.trasferlines.map { toMetroLine(it) }
        )
    }
    fun toMetroLine(line: String): MetroLine =
       when(line.trim().uppercase()) {
           "LINE_1","FIRST LINE","1"-> MetroLine.LINE1
           "LINE_2","SECOND LINE","2"-> MetroLine.LINE2
           "LINE_3","THIRD LINE","3"-> MetroLine.LINE3
           else -> MetroLine.LINE0

       }


    }

