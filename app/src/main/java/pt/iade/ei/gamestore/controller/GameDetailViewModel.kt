package pt.iade.ei.gamestore.controller

import androidx.lifecycle.ViewModel
import pt.iade.ei.gamestore.model.Game
import pt.iade.ei.gamestore.model.GameItem
import pt.iade.ei.myapplication.R

class GameDetailViewModel(
    private val repo: GameItemRepository = LocalGameItemRepository()
) : ViewModel() {
    fun itemsForStreetFootball(): List<GameItem> = repo.itemsForStreetFootball()
    fun itemsForGalaxyExplorers(): List<GameItem> = repo.itemsForGalaxyExplorers()

    fun imageResForGame(game: Game): Int {
        return when (game.title) {
            "Street Football" -> R.drawable.estadio_noturno
            "Galaxy Explorers" -> R.drawable.galaxia
            else -> R.mipmap.ic_launcher_foreground
        }
    }
}
