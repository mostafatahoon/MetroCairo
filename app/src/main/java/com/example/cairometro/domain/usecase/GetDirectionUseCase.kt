package domain.usecases
import domain.model.Station
import domain.usecase.GetFirstStationUseCase
import domain.usecase.GetLastStationUseCase

class GetDirectionUseCase(
    private val getFirstStationUseCase: GetFirstStationUseCase,
    private val getLastStationUseCase: GetLastStationUseCase
) {
    fun execute(
        current: Station,
        next: Station,
    ): String {
        val first = getFirstStationUseCase(current.line)

        val last = getLastStationUseCase(current.line)

        return if (next.order > current.order)
            last
        else
            first
    }
}