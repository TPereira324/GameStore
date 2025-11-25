package pt.iade.ei.gamestore.controller

import androidx.lifecycle.ViewModel
import pt.iade.ei.gamestore.model.GameItem


class GameDetailViewModel(
    private val repo: GameItemRepository = LocalGameItemRepository()
) : ViewModel() {
    fun itemsForStreetFootball(): List<GameItem> = repo.itemsForStreetFootball()
    fun itemsForGalaxyExplorers(): List<GameItem> = repo.itemsForGalaxyExplorers()


}
