package pt.iade.ei.gamestore.view

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import pt.iade.ei.gamestore.controller.SettingsViewModel
import pt.iade.ei.gamestore.ui.theme.GameStoreTheme

@Composable
fun NotificationsSettingsScreen(settings: SettingsViewModel) {
    val snack = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    Scaffold(snackbarHost = { SnackbarHost(snack) }) { inner ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(inner)
                .background(Brush.verticalGradient(listOf(Color(0xFFEF4444), Color.White)))
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Notificações", style = MaterialTheme.typography.titleLarge)
            Text(
                "Gerir notificações push",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Switch(
                checked = settings.pushEnabled.value,
                onCheckedChange = {
                    settings.setPushEnabled(it)
                    scope.launch { snack.showSnackbar("Notificações push " + if (it) "ativadas" else "desativadas") }
                },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = MaterialTheme.colorScheme.primary
                )
            )
            Text("Receber promoções", style = MaterialTheme.typography.bodyMedium)
            Switch(
                checked = settings.promotionsEnabled.value,
                onCheckedChange = {
                    settings.setPromotionsEnabled(it)
                    scope.launch { snack.showSnackbar("Promoções " + if (it) "ativadas" else "desativadas") }
                },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun NotificationsSettingsPreview() {
    GameStoreTheme { NotificationsSettingsScreen(settings = SettingsViewModel()) }
}