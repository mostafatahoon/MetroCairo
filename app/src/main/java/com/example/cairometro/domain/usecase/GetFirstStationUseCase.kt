package domain.usecase

import domain.model.MetroLine
import domain.model.Station
import domain.repository.MetroRepository

class GetFirstStationUseCase(val repo: MetroRepository) {

    operator fun invoke(line: MetroLine): String{

        return repo.getStations()
            .filter { it.line==line }
            .minBy { it.order }
            .name

    }


}