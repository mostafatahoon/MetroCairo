package com.example.cairometro.domain.usecase

import domain.model.Station
import java.util.*

class BFSUseCase {

    fun execute(
        start: Station,
        end: Station,
        stations: List<Station>
    ): List<Station>? {

        val queue: Queue<List<Station>> = LinkedList()
        val visited = mutableSetOf<Station>()

        queue.add(listOf(start))

        while (queue.isNotEmpty()) {

            val path = queue.poll()
            val current = path.last()

            if (current == end)
                return path

            if (current in visited)
                continue

            visited.add(current)

            val neighbors = getNeighbors(current, stations)

            neighbors.forEach {
                queue.add(path + it)
            }
        }

        return null
    }


    private fun getNeighbors(
        station: Station,
        stations: List<Station>
    ): List<Station> {

        val sameLine = stations.filter {
            it.line == station.line &&
                    (it.order == station.order + 1 ||
                            it.order == station.order - 1)
        }

        val transfers = if (station.isTransfer)
            stations.filter {
                it.name == station.name &&
                        it.line != station.line
            }
        else emptyList()

        return sameLine + transfers
    }
}