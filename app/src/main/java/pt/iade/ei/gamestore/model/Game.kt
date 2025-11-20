package pt.iade.ei.gamestore.model

data class Game(
    val id: String,
    val title: String,
    val imageUrl: String?,
    val price: Double,
    val featured: Boolean = false
)