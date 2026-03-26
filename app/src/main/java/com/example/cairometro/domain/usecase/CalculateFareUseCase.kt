package com.example.cairometro.domain.usecase

class CalculateFareUseCase {

    fun execute(count: Int): Int {

        return when {

            count <= 9 -> 10

            count <= 20 -> 15

            else -> 20
        }
    }
}