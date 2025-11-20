package pt.iade.ei.gamestore.view

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import pt.iade.ei.gamestore.ui.theme.GameStoreTheme

data class BottomItem(val icon: ImageVector, val label: String)

private val bottomItems = listOf(
    BottomItem(Icons.Outlined.Star, "Tela Principal"),
    BottomItem(Icons.Outlined.History, "Histórico"),
    BottomItem(Icons.Outlined.Person, "Perfil")
)

@Composable
fun GameStoreBottomBar(selectedIndex: Int, onSelectedIndexChange: (Int) -> Unit) {
    NavigationBar(containerColor = Color(0xFFEF4444), contentColor = Color.White) {
        bottomItems.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedIndex == index,
                onClick = { onSelectedIndexChange(index) },
                icon = { Icon(item.icon, contentDescription = null) },
                label = { Text(item.label) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    selectedTextColor = Color.Black,
                    unselectedIconColor = Color(0xFFFFE4E6),
                    unselectedTextColor = Color.Black,
                    indicatorColor = Color(0xFFB91C1C)
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomBarPreview() {
    GameStoreTheme {
        val selected = remember { mutableStateOf(0) }
        GameStoreBottomBar(
            selectedIndex = selected.value,
            onSelectedIndexChange = { selected.value = it })
    }
}