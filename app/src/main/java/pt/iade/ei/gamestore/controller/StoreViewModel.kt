package pt.iade.ei.gamestore.controller

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import pt.iade.ei.gamestore.model.Game

class StoreViewModel(
    repo: StoreRepository = LocalStoreRepository
) : ViewModel() {
    val games: SnapshotStateList<Game> = mutableStateListOf(*repo.getGames().toTypedArray())
}
