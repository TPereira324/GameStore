package pt.iade.ei.gamestore.controller

import pt.iade.ei.gamestore.model.Game
import pt.iade.ei.gamestore.model.Purchase
import java.time.LocalDateTime

interface StoreRepository {
    fun getGames(): List<Game>
    fun getPurchases(): List<Purchase>
    fun addPurchase(userId: String, game: Game): Purchase
    fun addPurchaseItem(userId: String, game: Game, itemTitle: String, price: Double): Purchase
    fun clearPurchases()
}

object LocalStoreRepository : StoreRepository {
    private val games = mutableListOf(
        Game(id = "g1", title = "Street Football", imageUrl = null, price = 9.99, featured = true),
        Game(id = "g2", title = "Galaxy Explorers", imageUrl = null, price = 14.99, featured = true)
    )
    private val purchases = mutableListOf<Purchase>()

    override fun getGames(): List<Game> = games
    override fun getPurchases(): List<Purchase> = purchases

    override fun addPurchase(userId: String, game: Game): Purchase {
        val p = Purchase(
            id = "p" + (purchases.size + 1),
            userId = userId,
            gameId = game.id,
            price = game.price,
            date = LocalDateTime.now()
        )
        purchases.add(p)
        return p
    }

    override fun addPurchaseItem(
        userId: String, game: Game, itemTitle: String, price: Double
    ): Purchase {
        val p = Purchase(
            id = "p" + (purchases.size + 1),
            userId = userId,
            gameId = game.id,
            itemTitle = itemTitle,
            price = price,
            date = LocalDateTime.now()
        )
        purchases.add(p)
        return p
    }

    override fun clearPurchases() {
        purchases.clear()
    }
}

