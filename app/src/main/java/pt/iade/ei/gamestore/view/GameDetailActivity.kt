package pt.iade.ei.gamestore.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import pt.iade.ei.gamestore.controller.GameDetailViewModel
import pt.iade.ei.gamestore.model.Game
import pt.iade.ei.gamestore.model.GameItem
import pt.iade.ei.gamestore.ui.theme.GameStoreTheme

class GameDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val game = intent.getSerializableExtra("game") as? Game
        setContent {
            GameStoreTheme {
                if (game == null) {
                    Text("Jogo não encontrado")
                } else {
                    GameDetailContent(game = game, onBuyItem = { item ->
                        Toast.makeText(
                            this,
                            "Acabou de comprar o item ${item.title} por $${String.format("%.2f", item.price)}",
                            Toast.LENGTH_SHORT
                        ).show()
                    })
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameDetailContent(game: Game, onBuyItem: (GameItem) -> Unit) {
    val brush = Brush.linearGradient(listOf(Color(0xFFEF4444), Color.White))
    val vm = remember { GameDetailViewModel() }
    val items = remember(game.id) {
        if (game.title == "Street Football") vm.itemsForStreetFootball() else vm.itemsForGalaxyExplorers()
    }
    var showSheet by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .background(brush)
                .padding(16.dp)
        ) {
            Column {
                Text(game.title, style = MaterialTheme.typography.titleLarge, color = Color.Black, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(12.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Button(onClick = { /* comprar jogo simples */ }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEF4444), contentColor = Color.White)) {
                        Text("Comprar Jogo")
                    }
                    Button(onClick = { showSheet = true }, colors = ButtonDefaults.buttonColors(containerColor = Color.Black, contentColor = Color.White)) {
                        Text("Ver Itens")
                    }
                }
            }
        }

        if (showSheet) {
            ModalBottomSheet(onDismissRequest = { showSheet = false }) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("Itens Disponíveis", style = MaterialTheme.typography.titleMedium, color = Color.Black)
                    items.forEach { item ->
                        Card(colors = CardDefaults.cardColors(containerColor = Color(0xFFF9FAFB))) {
                            Row(modifier = Modifier.padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(item.title, style = MaterialTheme.typography.titleMedium)
                                    Text(item.description, style = MaterialTheme.typography.bodySmall, color = Color.Black)
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Button(onClick = {
                                    onBuyItem(item)
                                    showSheet = false
                                }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEF4444), contentColor = Color.White)) {
                                    Text("$" + String.format("%.2f", item.price))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}