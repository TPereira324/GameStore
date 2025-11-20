package pt.iade.ei.gamestore.controller

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import pt.iade.ei.gamestore.model.Achievement
import java.time.LocalDateTime

class ProfileStatsViewModel : ViewModel() {
    val achievements: SnapshotStateList<Achievement> = mutableStateListOf(
        Achievement("a1", "Estreante", "Primeira compra", "g1", LocalDateTime.now().minusDays(10)),
        Achievement(
            "a2",
            "Explorador",
            "Comprou um item premium",
            "g2",
            LocalDateTime.now().minusDays(5)
        ),
        Achievement(
            "a3",
            "Camisa Lendária",
            "Item raro adquirido",
            "g1",
            LocalDateTime.now().minusDays(2)
        )
    )

    private val _points = mutableStateOf(1240)
    val points: State<Int> get() = _points

    fun addAchievement(a: Achievement) {
        achievements.add(a)
    }

    fun addPoints(p: Int) {
        _points.value = _points.value + p
    }
}