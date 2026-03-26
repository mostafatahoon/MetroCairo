package domain.model

data class Station(
  val id: Int,
    val name: String,
    val line: MetroLine,
    val order: Int,
    val isTransfer : Boolean,
    val trasferlines: List<MetroLine>



)
