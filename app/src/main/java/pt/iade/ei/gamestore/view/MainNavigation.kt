package pt.iade.ei.gamestore.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import pt.iade.ei.gamestore.controller.StoreViewModel
import pt.iade.ei.gamestore.ui.theme.GameStoreTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainNavigation(screen: String? = null, gameId: String? = null) {
    val store = remember { StoreViewModel() }
    val context = LocalContext.current
    val routeState = androidx.compose.runtime.remember {
        androidx.compose.runtime.mutableStateOf(
            screen ?: "tela_principal"
        )
    }

    Scaffold(
        bottomBar = {
            run {
                val selectedIndex = 0
                GameStoreBottomBar(selectedIndex = selectedIndex, onSelectedIndexChange = {
                    when (it) {
                        0 -> routeState.value = "tela_principal"

                    }
                })
            }
        }
    ) { inner ->
        Box(
            modifier = Modifier
                .padding(inner)
                .background(Brush.linearGradient(listOf(Color(0xFFEF4444), Color.White)))
        ) {
            when (routeState.value) {
                "tela_principal" -> {
                    TelaPrincipalScreen(
                        games = store.games,
                        onGameClick = { game ->
                            val i = android.content.Intent(
                                context,
                                GameDetailActivity::class.java
                            )
                            i.putExtra("game", game)
                            context.startActivity(i)
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainNavigationPreview() {
    GameStoreTheme { MainNavigation() }
}
