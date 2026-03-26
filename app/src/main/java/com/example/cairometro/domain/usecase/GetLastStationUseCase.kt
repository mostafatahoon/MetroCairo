package domain.usecase

import domain.model.MetroLine
import domain.repository.MetroRepository

class GetLastStationUseCase(val repo: MetroRepository) {

   operator fun invoke(line: MetroLine): String{

        return repo.getStations()
            .filter { it.line==line }
            .maxBy { it.order }
            .name
    }

}