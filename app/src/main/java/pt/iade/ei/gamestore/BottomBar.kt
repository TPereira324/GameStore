package pt.iade.ei.gamestore

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.graphics.vector.ImageVector
import pt.iade.ei.gamestore.ui.theme.GameStoreTheme

data class BottomItem(val icon: ImageVector, val label: String)

private val bottomItems = listOf(
    BottomItem(Icons.Outlined.Star, "Featured"),
    BottomItem(Icons.Outlined.History, "History"),
    BottomItem(Icons.Outlined.Person, "Profile")
)

@Composable
fun GameStoreBottomBar(selectedIndex: Int, onSelectedIndexChange: (Int) -> Unit) {
    NavigationBar {
        bottomItems.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedIndex == index,
                onClick = { onSelectedIndexChange(index) },
                icon = { Icon(item.icon, contentDescription = null) },
                label = { Text(item.label) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomBarPreview() {
    GameStoreTheme {
        val selected = remember { mutableStateOf(0) }
        GameStoreBottomBar(selectedIndex = selected.value, onSelectedIndexChange = { selected.value = it })
    }
}