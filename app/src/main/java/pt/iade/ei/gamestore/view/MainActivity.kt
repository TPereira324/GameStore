package pt.iade.ei.gamestore.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import pt.iade.ei.gamestore.ui.theme.GameStoreTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val screen = intent?.getStringExtra("screen")
        val gameId = intent?.getStringExtra("gameId")
        setContent { GameStoreTheme { MainNavigation(screen = screen, gameId = gameId) } }
    }
}