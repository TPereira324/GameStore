package pt.iade.ei.gamestore.controller

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import java.time.LocalDateTime
import pt.iade.ei.gamestore.model.Game
import pt.iade.ei.gamestore.model.Purchase

class StoreViewModel : ViewModel() {
    val games: SnapshotStateList<Game> = mutableStateListOf(
        Game(id = "g1", title = "Street Football", imageUrl = null, price = 9.99, featured = true),
        Game(id = "g2", title = "Galaxy Explorers", imageUrl = null, price = 14.99, featured = true)
    )

    val purchases: SnapshotStateList<Purchase> = mutableStateListOf()

    fun addPurchase(userId: String, game: Game) {
        purchases.add(
            Purchase(
                id = "p" + (purchases.size + 1),
                userId = userId,
                gameId = game.id,
                price = game.price,
                date = LocalDateTime.now()
            )
        )
    }
}