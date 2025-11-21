package pt.iade.ei.gamestore.view

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import pt.iade.ei.gamestore.controller.SecurityViewModel
import pt.iade.ei.gamestore.ui.theme.GameStoreTheme

@Composable
fun PrivacySecurityScreen(security: SecurityViewModel) {
    val password = remember { mutableStateOf("") }
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
            Text("Privacidade e Segurança", style = MaterialTheme.typography.titleLarge)
            Text(
                "Alterar password, 2FA",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text("Autenticação de dois fatores", style = MaterialTheme.typography.bodyMedium)
            Switch(
                checked = security.twoFactorEnabled.value,
                onCheckedChange = { enabled ->
                    security.setTwoFactorEnabled(enabled)
                    scope.launch { snack.showSnackbar(if (enabled) "2FA ativada" else "2FA desativada") }
                },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = MaterialTheme.colorScheme.primary
                )
            )
            OutlinedTextField(
                value = password.value,
                onValueChange = { password.value = it },
                label = { Text("Nova password") },
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = {
                    val ok = security.updatePassword(password.value)
                    scope.launch { snack.showSnackbar(if (ok) "Password atualizada" else "Password inválida (mínimo 6 caracteres)") }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                )
            ) { Text("Guardar", color = Color.White) }
        }
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun PrivacySecurityPreview() {
    GameStoreTheme { PrivacySecurityScreen(security = pt.iade.ei.gamestore.controller.SecurityViewModel()) }
}