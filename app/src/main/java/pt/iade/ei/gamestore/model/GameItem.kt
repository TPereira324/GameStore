package pt.iade.ei.gamestore.model

import java.io.Serializable
data class GameItem(
    val title: String,
    val description: String,
    val price: Double
) : Serializable
