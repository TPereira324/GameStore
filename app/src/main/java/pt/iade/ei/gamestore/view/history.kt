package pt.iade.ei.gamestore.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Inventory2
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pt.iade.ei.gamestore.model.Game
import pt.iade.ei.gamestore.model.Purchase
import pt.iade.ei.gamestore.ui.theme.GameStoreTheme
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import androidx.compose.foundation.layout.Row as FRow

@Composable
fun HistoryScreen(purchases: List<Purchase>, games: List<Game>, onClear: (() -> Unit)? = null) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color(0xFFEF4444), Color.White)))
            .padding(16.dp)
    ) {
        val total = purchases.sumOf { it.price }
        Card(colors = CardDefaults.cardColors(containerColor = Color.White)) {
            FRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                Text(
                    "Total",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    formatPrice(total),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        if (purchases.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Inventory2,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("Nenhuma compra ainda", style = MaterialTheme.typography.titleMedium)
                }
            }
        } else {
            val listToShow = purchases.sortedBy { it.date }.reversed()
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(listToShow) { p ->
                    val game = games.firstOrNull { it.id == p.gameId }
                    Card(
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    p.itemTitle ?: (game?.title ?: p.gameId),
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(
                                    formatTime(p.date),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                            Text(
                                formatPrice(p.price),
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HistoryScreenPreview() {
    GameStoreTheme {
        HistoryScreen(
            purchases = listOf(
                
                Purchase(
                    id = "p3",
                    userId = "1",
                    gameId = "g2",
                    itemTitle = "Expansão: Fronteira Alien",
                    price = 12.99,
                    date = LocalDateTime.now().minusDays(1)
                ),
                Purchase(
                    id = "p4",
                    userId = "1",
                    gameId = "g1",
                    itemTitle = "Camisa Legendária Brasil",
                    price = 9.99,
                    date = LocalDateTime.now().minusDays(2)
                )
            ),
            games = listOf(
                pt.iade.ei.gamestore.model.Game(
                    id = "g1",
                    title = "Street Football",
                    imageUrl = null,
                    price = 9.99,
                    featured = true
                ),
                pt.iade.ei.gamestore.model.Game(
                    id = "g2",
                    title = "Galaxy Explorers",
                    imageUrl = null,
                    price = 14.99,
                    featured = true
                )
            ),
            onClear = {}
        )
    }
}

private fun formatPrice(price: Double): String = String.format("%.2f€", price).replace('.', ',')

private fun formatTime(date: LocalDateTime): String {
    val now = LocalDateTime.now()
    val d = Duration.between(date, now)
    val minutes = d.toMinutes()
    val hours = d.toHours()
    return if (hours < 24) {
        if (hours >= 1) "${hours}h atrás" else "${minutes}m atrás"
    } else {
        date.toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
    }
}

private fun inPeriod(date: LocalDateTime, period: String): Boolean {
    val now = LocalDateTime.now()
    return when (period) {
        "hoje" -> date.toLocalDate() == now.toLocalDate()
        "semana" -> {
            val startOfWeek = now.minusDays((now.dayOfWeek.value - 1).toLong()).toLocalDate()
            date.toLocalDate() >= startOfWeek
        }

        "mes" -> date.year == now.year && date.month == now.month
        else -> true
    }
}
