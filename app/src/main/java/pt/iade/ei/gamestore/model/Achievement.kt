package pt.iade.ei.gamestore.model

import java.time.LocalDateTime

data class Achievement(
    val id: String,
    val title: String,
    val description: String,
    val gameId: String,
    val date: LocalDateTime
)