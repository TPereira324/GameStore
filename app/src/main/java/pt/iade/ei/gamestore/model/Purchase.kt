package pt.iade.ei.gamestore.model

import java.time.LocalDateTime

data class Purchase(
    val id: String,
    val userId: String,
    val gameId: String,
    val price: Double,
    val date: LocalDateTime
)