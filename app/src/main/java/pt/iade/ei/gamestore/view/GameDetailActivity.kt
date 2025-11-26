package pt.iade.ei.gamestore.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import pt.iade.ei.gamestore.model.Game
import pt.iade.ei.gamestore.ui.theme.GameStoreTheme

class GameDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val game = intent.getSerializableExtra("game") as? Game
        setContent {
            GameStoreTheme {
                if (game != null) {
                    GameDetailScreen(game = game, onBuyItem = { item ->
                        Toast.makeText(
                            this,
                            "Acabou de comprar o item ${item.title} por ${formatPriceEur(item.price)}",
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    })
                } else {
                    androidx.compose.foundation.layout.Box(Modifier.fillMaxSize())
                }
            }
        }
    }
}

@Composable
fun GameDetailScreen(game: Game, onBuyItem: (pt.iade.ei.gamestore.model.GameItem) -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        when (game.id) {
            "g1" -> StreetFootballDetailScreen(onBuyItem = onBuyItem)
            "g2" -> GalaxyExplorersDetailScreen(onBuyItem = onBuyItem)
            else -> GalaxyExplorersDetailScreen(onBuyItem = onBuyItem)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GameDetailScreenPreview() {
    GameStoreTheme {
        GameDetailScreen(
            game = Game(
                id = "g1",
                title = "Street Football",
                imageUrl = null,
                price = 9.99,
                featured = true
            ), onBuyItem = {})
    }
}

fun formatPriceEur(price: Double): String = String.format("%.2f€", price).replace('.', ',')
