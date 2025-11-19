package pt.iade.ei.gamestore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pt.iade.ei.gamestore.controller.AuthViewModel
import pt.iade.ei.gamestore.controller.StoreViewModel
import pt.iade.ei.gamestore.ui.theme.GameStoreTheme
import pt.iade.ei.gamestore.view.FeaturedScreen
import pt.iade.ei.gamestore.view.HistoryScreen
import pt.iade.ei.gamestore.view.ProfileScreen
import pt.iade.ei.gamestore.view.StreetFootballDetailScreen
import pt.iade.ei.gamestore.view.GalaxyExplorersDetailScreen
import pt.iade.ei.gamestore.model.Game

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GameStoreTheme {
                GameStoreApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameStoreApp() {
    var selected by remember { mutableStateOf(0) }
    val auth = remember { AuthViewModel() }
    val store = remember { StoreViewModel() }
    var selectedGame by remember { mutableStateOf<Game?>(null) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(selectedGame?.title ?: "Sports Games Store") },
                navigationIcon = {
                    if (selectedGame != null) {
                        Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = null, modifier = Modifier.padding(start = 8.dp))
                    }
                },
                actions = {
                    Icon(imageVector = Icons.Outlined.Notifications, contentDescription = null, modifier = Modifier.padding(horizontal = 8.dp))
                    Icon(imageVector = Icons.Outlined.Settings, contentDescription = null, modifier = Modifier.padding(end = 8.dp))
                }
            )
        },
        bottomBar = { GameStoreBottomBar(selectedIndex = selected, onSelectedIndexChange = { selected = it; selectedGame = null }) }
    ) { inner ->
        androidx.compose.foundation.layout.Box(modifier = Modifier.padding(inner)) {
            when (selected) {
                0 -> if (selectedGame == null) {
                    FeaturedScreen(games = store.games, onGameClick = { selectedGame = it })
                } else {
                    val game = selectedGame!!
                    if (game.title == "Street Football") {
                        StreetFootballDetailScreen()
                    } else {
                        GalaxyExplorersDetailScreen()
                    }
                }
                1 -> HistoryScreen(purchases = store.purchases)
                else -> ProfileScreen(user = auth.currentUser.value)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    GameStoreTheme { GameStoreApp() }
}