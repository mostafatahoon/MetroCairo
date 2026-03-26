package data.repositoryImp

import data.datasource.MetroDataSource
import data.mapper.MetroMapper
import domain.model.Station
import domain.repository.MetroRepository

class MetroRepositoryImpl(private val dataSource: MetroDataSource) : MetroRepository {
    override fun getStations(): List<Station> {
        return dataSource.loadStations()
            .map { MetroMapper.toDomain( it) }
    }

    override fun getTravelTime(): Int {
        return dataSource.getTimeTravel()
    }


}