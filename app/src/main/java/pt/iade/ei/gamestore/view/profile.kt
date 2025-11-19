package pt.iade.ei.gamestore.view

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pt.iade.ei.gamestore.model.User
import pt.iade.ei.gamestore.ui.theme.GameStoreTheme

@Composable
fun ProfileScreen(user: User?) {
    val context = LocalContext.current
    var profileBitmap by remember { mutableStateOf<Bitmap?>(null) }
    val imagePicker = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            try {
                context.contentResolver.openInputStream(uri)?.use { stream ->
                    profileBitmap = BitmapFactory.decodeStream(stream)
                }
            } catch (_: Exception) {}
        }
    }

    Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF8B5CF6))
                    .clickable { imagePicker.launch("image/*") },
                contentAlignment = Alignment.Center
            ) {
                if (profileBitmap != null) {
                    Image(bitmap = profileBitmap!!.asImageBitmap(), contentDescription = null)
                } else {
                    Icon(imageVector = Icons.Outlined.Person, contentDescription = null, tint = Color.White, modifier = Modifier.size(36.dp))
                }
            }
            Spacer(modifier = Modifier.size(16.dp))
            Column {
                Text(user?.name ?: "Utilizador", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Text("Jogador Premium", style = MaterialTheme.typography.bodyMedium, color = Color(0xFF6B7280))
            }
        }

        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text("Informações da Conta", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            InfoRow(icon = Icons.Outlined.Person, title = "Email", subtitle = user?.email ?: "")
            InfoRow(icon = Icons.Outlined.Person, title = "Telefone", subtitle = user?.phone ?: "")
            InfoRow(icon = Icons.Outlined.Person, title = "Localização", subtitle = "Lisboa, Portugal")
        }

        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text("Configurações", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            InfoRow(icon = Icons.Outlined.Notifications, title = "Notificações", subtitle = "Ativadas")
        }
    }
}

@Composable
fun InfoRow(icon: ImageVector, title: String, subtitle: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = Color(0xFF6B7280), modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.size(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, style = MaterialTheme.typography.bodyLarge)
            Text(subtitle, style = MaterialTheme.typography.bodyMedium, color = Color(0xFF6B7280))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    GameStoreTheme {
        ProfileScreen(
            user = User(id = "1", name = "José Silva", phone = "+351 912 345 678", email = "jose.silva@exemplo.pt", password = "******")
        )
    }
}