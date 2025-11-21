package pt.iade.ei.gamestore.controller

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import pt.iade.ei.gamestore.model.Game
import pt.iade.ei.gamestore.model.Purchase

class StoreViewModel(
    private val repo: StoreRepository = LocalStoreRepository
) : ViewModel() {
    val games: SnapshotStateList<Game> = mutableStateListOf(*repo.getGames().toTypedArray())

    val purchases: SnapshotStateList<Purchase> =
        mutableStateListOf(*repo.getPurchases().toTypedArray())

    fun addPurchase(userId: String, game: Game) {
        val p = repo.addPurchase(userId, game)
        purchases.add(p)
    }

    fun addPurchaseItem(userId: String, game: Game, itemTitle: String, price: Double) {
        val p = repo.addPurchaseItem(userId, game, itemTitle, price)
        purchases.add(p)
    }

    fun clearPurchases() {
        repo.clearPurchases()
        purchases.clear()
    }
}