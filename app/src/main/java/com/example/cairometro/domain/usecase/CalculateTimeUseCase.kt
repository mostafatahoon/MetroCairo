package com.example.cairometro.domain.usecase
import domain.repository.MetroRepository

class CalculateTimeUseCase(
    private val repo: MetroRepository,
) {

    fun execute(count: Int) =
        count * repo.getTravelTime()
}