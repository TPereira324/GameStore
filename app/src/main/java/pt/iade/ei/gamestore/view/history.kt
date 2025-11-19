package pt.iade.ei.gamestore.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import pt.iade.ei.gamestore.model.Purchase
import pt.iade.ei.gamestore.ui.theme.GameStoreTheme
import java.time.LocalDateTime

@Composable
fun HistoryScreen(purchases: List<Purchase>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (purchases.isEmpty()) {
            Icon(imageVector = Icons.Outlined.ShoppingCart, contentDescription = null, tint = Color(0xFF9CA3AF))
            Spacer(modifier = Modifier.height(12.dp))
            Text("Nenhuma compra ainda", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text("Seus itens comprados aparecerão aqui", style = MaterialTheme.typography.bodyMedium, color = Color(0xFF6B7280))
        } else {
            purchases.forEach {
                Text("Compra: ${'$'}{it.gameId} - ${'$'}{it.price}", style = MaterialTheme.typography.bodyLarge)
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
                Purchase(id = "p1", userId = "1", gameId = "g1", price = 9.99, date = LocalDateTime.now())
            )
        )
    }
}