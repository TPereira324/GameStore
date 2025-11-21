package pt.iade.ei.gamestore

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import pt.iade.ei.gamestore.controller.StoreViewModel
import pt.iade.ei.gamestore.model.Game
import pt.iade.ei.gamestore.ui.theme.GameStoreTheme
import pt.iade.ei.gamestore.view.TelaPrincipalScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val store = StoreViewModel()
        setContent {
            GameStoreTheme {
                TelaPrincipalScreen(games = store.games, onGameClick = { game: Game ->
                    val intent = Intent(this, pt.iade.ei.gamestore.view.GameDetailActivity::class.java)
                    intent.putExtra("game", game)
                    startActivity(intent)
                })
            }
        }
    }
}