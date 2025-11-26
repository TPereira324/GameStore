package pt.iade.ei.gamestore.view

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pt.iade.ei.gamestore.R
import pt.iade.ei.gamestore.controller.GameDetailViewModel
import pt.iade.ei.gamestore.model.GameItem
import pt.iade.ei.gamestore.ui.theme.GameStoreTheme

@SuppressLint("ContextCastToActivity")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StreetFootballDetailScreen(onBuyItem: (GameItem) -> Unit) {
    val vm = remember { GameDetailViewModel() }
    val items = vm.itemsForStreetFootball()
    val selectedItem = remember { mutableStateOf<GameItem?>(null) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.linearGradient(listOf(Color(0xFFEF4444), Color.White)))
    ) {
        val activity = (LocalContext.current as? android.app.Activity)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .padding(start = 16.dp, end = 16.dp, top = 56.dp)

        ) {
            IconButton(
                onClick = { activity?.finish() },
                modifier = Modifier.align(Alignment.TopStart)
            ) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = null,
                    tint = Color.Black
                )
            }
            Column(modifier = Modifier.align(Alignment.BottomStart)) {
                Text(
                    "Street Football",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.Black
                )
                Text(
                    "Futebol de rua com ambientação urbana vibrante.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        Spacer(modifier = Modifier.size(14.dp))
        Text(
            "Itens Disponíveis",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(16.dp),
            color = Color.Black
        )
        LazyColumn(contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)) {
            items(items) { item ->
                Card(
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = imageResForStreetItem(item.title)),
                            contentDescription = item.title,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.size(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(item.title, style = MaterialTheme.typography.titleMedium)
                            Text(
                                item.description,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface,
                                maxLines = 2
                            )
                        }
                        Spacer(modifier = Modifier.size(12.dp))
                        Button(
                            onClick = { selectedItem.value = item },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Black,
                                contentColor = Color.White
                            )
                        ) {
                            Text(formatPriceEur(item.price), color = Color.White)
                        }
                    }
                }
            }
        }
    }
    if (selectedItem.value != null) {
        val item = selectedItem.value!!
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.linearGradient(listOf(Color(0xFFEF4444), Color.White)))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                IconButton(onClick = { selectedItem.value = null }) {
                    Icon(
                        imageVector = Icons.Outlined.ArrowBack,
                        contentDescription = null,
                        tint = Color.Black
                    )
                }
                Text(
                    item.title,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    item.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        formatPriceEur(item.price),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Button(
                        onClick = { onBuyItem(item); selectedItem.value = null },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFEF4444),
                            contentColor = Color.White
                        )
                    ) {
                        Text("Buy with 1-click")
                    }
                }
            }
        }
    }
}


private fun imageResForStreetItem(title: String): Int {
    val t = title.lowercase()
    return when {
        t.contains("celebra") -> R.drawable.celebrator
        t.contains("bola") || t.contains("ouro") -> R.drawable.bola_de_ouro
        t.contains("camisa") || t.contains("brasil") -> R.drawable.camisa_do_brasil
        else -> R.drawable.estadio_noturno
    }
}

@Preview(showBackground = true)
@Composable
fun StreetFootballDetailScreenPreview() {
    GameStoreTheme { StreetFootballDetailScreen(onBuyItem = {}) }
}
