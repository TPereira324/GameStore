package pt.iade.ei.gamestore.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pt.iade.ei.gamestore.R
import pt.iade.ei.gamestore.controller.GameDetailViewModel
import pt.iade.ei.gamestore.model.Game
import pt.iade.ei.gamestore.ui.theme.GameStoreTheme

@Composable
fun TelaPrincipalScreen(games: List<Game>, onGameClick: (Game) -> Unit) {
    remember { GameDetailViewModel() }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.linearGradient(listOf(Color(0xFFEF4444), Color.White)))
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Spacer(modifier = Modifier.size(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Game Store",
                style = MaterialTheme.typography.titleLarge,
                color = Color.Black,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.weight(1f))

        }
        val g1 = games.getOrNull(0)
        if (g1 != null) {
            SimpleGameBox(
                title = g1.title, imageResId = R.drawable.estadio_noturno
            ) { onGameClick(g1) }
        }
        val g2 = games.getOrNull(1)
        if (g2 != null) {
            SimpleGameBox(
                title = g2.title, imageResId = R.drawable.galaxia
            ) { onGameClick(g2) }
        }
    }
}

@Composable
fun SimpleGameBox(title: String, imageResId: Int, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clickable { onClick() }) {
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TelaPrincipalScreenPreview() {
    GameStoreTheme {
        TelaPrincipalScreen(
            games = listOf(
                Game(
                    id = "g1",
                    title = "Street Football",
                    imageUrl = null,
                    price = 9.99,
                    featured = true
                ), Game(
                    id = "g2",
                    title = "Galaxy Explorers",
                    imageUrl = null,
                    price = 14.99,
                    featured = true
                )
            ), onGameClick = {})
    }
}
