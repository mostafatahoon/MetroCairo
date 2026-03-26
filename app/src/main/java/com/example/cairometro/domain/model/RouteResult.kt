package domain.model

sealed class RouteResult {

    data class Success(
       val station: List<Station>,
        val fare: Int,
        val time: Int

    ): RouteResult()

    data class Error(
        val message: String,
    ): RouteResult()


}