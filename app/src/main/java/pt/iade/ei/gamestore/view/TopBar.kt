package pt.iade.ei.gamestore.view

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameStoreTopBar(
    title: String,
    showBack: Boolean,
    onBack: () -> Unit
) {
    TopAppBar(
        title = { Text(title) },
        navigationIcon = {
            if (showBack) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.Outlined.ArrowBack,
                        contentDescription = null
                    )
                }
            }
        },

        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFFEF4444),
            titleContentColor = Color.Black,
            navigationIconContentColor = Color.White,
            actionIconContentColor = Color.White
        )
    )
}
@Preview(showBackground = true)
@Composable
fun GameStoreTopBarPreview() {

}