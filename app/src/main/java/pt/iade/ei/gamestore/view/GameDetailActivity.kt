package pt.iade.ei.gamestore.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
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
import pt.iade.ei.gamestore.controller.StoreViewModel
import pt.iade.ei.gamestore.model.Game
import pt.iade.ei.gamestore.model.GameItem
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
                    Box(Modifier.fillMaxSize())
                }
            }
        }
    }
}

@Composable
fun GameDetailScreen(game: Game, onBuyItem: (GameItem) -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        when (game.id) {
            "g1" -> StreetFootballDetailScreen(game = game, onBuyItem = onBuyItem)
            "g2" -> GalaxyExplorersDetailScreen(game = game, onBuyItem = onBuyItem)
            else -> GalaxyExplorersDetailScreen(game = game, onBuyItem = onBuyItem)
        }
    }
}


@SuppressLint("DefaultLocale")
fun formatPriceEur(price: Double): String = String.format("%.2f€", price).replace('.', ',')

@SuppressLint("ContextCastToActivity")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StreetFootballDetailScreen(game: Game, onBuyItem: (GameItem) -> Unit) {
    val vm = remember { GameDetailViewModel() }
    val store = remember { StoreViewModel() }
    val items = vm.itemsForStreetFootball()
    val selectedItem = remember { mutableStateOf<GameItem?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.linearGradient(listOf(Color(0xFFEF4444), Color.White)))
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            val activity = (LocalContext.current as? android.app.Activity)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(start = 16.dp, end = 16.dp, top = 56.dp)
            ) {
                IconButton(
                    onClick = { activity?.finish() }, modifier = Modifier.align(Alignment.TopStart)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                        contentDescription = null,
                        tint = Color.Black
                    )
                }
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(bottom = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.estadio_noturno),
                        contentDescription = "Street Football",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(72.dp)
                    )
                    Spacer(modifier = Modifier.size(12.dp))
                    Column {
                        Text(
                            "Street Football",
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.Black
                        )
                        Text(
                            "Futebol de rua com ambientação urbana vibrante.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Black,
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.size(14.dp))
            Text(
                "Itens Disponíveis",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(16.dp),
                color = Color.Black
            )
            LazyColumn(
                modifier = Modifier.weight(100f),
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 20.dp),
                verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(20.dp)
            ) {
                items(items) { item ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)
                        )
                    ) {
                        ItemCard(
                            item = item,
                            imageFallbackRes = R.drawable.estadio_noturno,
                            buttonColor = Color(0xFFEF4444),
                            onSelect = { selectedItem.value = item })
                    }
                }
            }
        }
        if (selectedItem.value != null) {
            val item = selectedItem.value!!
            SelectedItemPanel(
                item = item,
                imageFallbackRes = R.drawable.estadio_noturno,
                buttonColor = Color(0xFFEF4444),
                onClose = { selectedItem.value = null },
                onBuy = {
                    store.addPurchaseItem(
                        userId = "u1", game = game, itemTitle = item.title, price = item.price
                    )
                    onBuyItem(item)
                    selectedItem.value = null
                },
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}

@SuppressLint("ContextCastToActivity")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GalaxyExplorersDetailScreen(game: Game, onBuyItem: (GameItem) -> Unit) {
    val vm = remember { GameDetailViewModel() }
    val store = remember { StoreViewModel() }
    val items = vm.itemsForGalaxyExplorers()
    val selectedItem = remember { mutableStateOf<GameItem?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.linearGradient(listOf(Color(0xFFEF4444), Color.White)))
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            val activity = (LocalContext.current as? android.app.Activity)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(start = 16.dp, end = 16.dp, top = 56.dp)
            ) {
                IconButton(
                    onClick = { activity?.finish() }, modifier = Modifier.align(Alignment.TopStart)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                        contentDescription = null,
                        tint = Color.Black
                    )
                }
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(bottom = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.galaxia),
                        contentDescription = "Galaxy Explorers",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(72.dp)
                    )
                    Spacer(modifier = Modifier.size(12.dp))
                    Column {
                        Text(
                            "Galaxy Explorers",
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.Black
                        )
                        Text(
                            "Aventura espacial com exploração intergaláctica",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Black,
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.size(14.dp))
            Text(
                "Itens Disponíveis",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(16.dp),
                color = Color.Black
            )
            LazyColumn(
                modifier = Modifier.weight(100f),
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 20.dp),
                verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(20.dp)
            ) {
                items(items) { item ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)
                        )
                    ) {
                        ItemCard(
                            item = item,
                            imageFallbackRes = R.drawable.galaxia,
                            buttonColor = Color(0xFFEF4444),
                            onSelect = { selectedItem.value = item })
                    }
                }
            }
        }
        if (selectedItem.value != null) {
            val item = selectedItem.value!!
            SelectedItemPanel(
                item = item,
                imageFallbackRes = R.drawable.galaxia,
                buttonColor = Color(0xFFEF4444),
                onClose = { selectedItem.value = null },
                onBuy = {
                    store.addPurchaseItem(
                        userId = "u1", game = game, itemTitle = item.title, price = item.price
                    )
                    onBuyItem(item)
                    selectedItem.value = null
                },
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}

@Composable
private fun ItemCard(
    item: GameItem, imageFallbackRes: Int, buttonColor: Color, onSelect: () -> Unit
) {
    Row(
        modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = item.imageResId ?: imageFallbackRes),
            contentDescription = item.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(60.dp)
        )
        Spacer(modifier = Modifier.size(14.dp))
        Column(modifier = Modifier.weight(10f)) {
            Text(item.title, style = MaterialTheme.typography.titleMedium)
            Text(
                item.shortDescription ?: item.description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 2
            )
        }
        Spacer(modifier = Modifier.size(14.dp))
        Button(
            onClick = onSelect, colors = ButtonDefaults.buttonColors(
                containerColor = buttonColor, contentColor = Color.White
            )
        ) {
            Text(formatPriceEur(item.price), color = Color.White)
        }
    }
}

@Composable
private fun SelectedItemPanel(
    item: GameItem,
    imageFallbackRes: Int,
    buttonColor: Color,
    onClose: () -> Unit,
    onBuy: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(350.dp)
            .background(Brush.linearGradient(listOf(Color(0xFFEF4444), Color.White)))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            IconButton(onClick = onClose) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                    contentDescription = null,
                    tint = Color.Black
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = item.imageResId ?: imageFallbackRes),
                    contentDescription = item.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(96.dp)
                )
                Spacer(modifier = Modifier.size(14.dp))
                Column(modifier = Modifier.weight(10f)) {
                    Text(
                        item.title, style = MaterialTheme.typography.titleLarge, color = Color.Black
                    )
                    Text(
                        item.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Black
                    )
                    Text(
                        item.seller ?: "",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Black
                    )
                }
            }
            Spacer(modifier = Modifier.size(14.dp))
            Row(
                modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    formatPriceEur(item.price),
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White
                )
                Spacer(modifier = Modifier.weight(10f))
                Button(
                    onClick = onBuy, colors = ButtonDefaults.buttonColors(
                        containerColor = buttonColor, contentColor = Color.White
                    )
                ) {
                    Text("Buy with 1-click")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StreetFootballDetailScreenPreview() {
    GameStoreTheme {
        StreetFootballDetailScreen(
            game = Game(
                id = "g1", title = "Street Football", imageUrl = null, price = 0.0
            ), onBuyItem = {})
    }
}

@Preview(showBackground = true)
@Composable
fun GalaxyExplorersDetailScreenPreview() {
    GameStoreTheme {
        GalaxyExplorersDetailScreen(
            game = Game(
                id = "g2", title = "Galaxy Explorers", imageUrl = null, price = 0.0
            ), onBuyItem = {})
    }
}

@Preview(showBackground = true)
@Composable
fun StreetFootballSelectedItemPreview() {
    GameStoreTheme {
        val item = GameItem(
            "Pacote de Celebrações", "10 celebrações exclusivas após o gol", 4.99
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
                .background(Brush.linearGradient(listOf(Color(0xFFEF4444), Color.White)))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                        contentDescription = null,
                        tint = Color.Black
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(
                            id = item.imageResId ?: R.drawable.estadio_noturno
                        ),
                        contentDescription = item.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(96.dp)
                    )
                    Spacer(modifier = Modifier.size(14.dp))
                    Column(modifier = Modifier.weight(10f)) {
                        Text(
                            item.title,
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.Black
                        )
                        Text(
                            item.description,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Black
                        )
                    }
                }
                Spacer(modifier = Modifier.size(14.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        formatPriceEur(item.price),
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.weight(10f))
                    Button(
                        onClick = { }, colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFEF4444), contentColor = Color.White
                        )
                    ) {
                        Text("Buy with 1-click")
                    }
                }
            }
        }
    }
}
