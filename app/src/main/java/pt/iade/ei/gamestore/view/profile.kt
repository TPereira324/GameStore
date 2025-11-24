package pt.iade.ei.gamestore.view

import android.annotation.SuppressLint
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.CreditCard
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.EmojiEvents
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pt.iade.ei.gamestore.model.User
import pt.iade.ei.gamestore.ui.theme.GameStoreTheme

@SuppressLint("SuspiciousIndentation")
@Composable
fun ProfileScreen(
    user: User?,
    purchasesCount: Int = 0,
    achievementsCount: Int = 0,
    points: Int = 0,
    onLogout: (() -> Unit)? = null,
    onOpenPayments: (() -> Unit)? = null,
    onOpenNotifications: (() -> Unit)? = null,
    
) {
    val context = LocalContext.current
    val isPreview = LocalInspectionMode.current
    var profileBitmap by remember { mutableStateOf<Bitmap?>(null) }
    val imagePicker = if (!isPreview) {
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                try {
                    context.contentResolver.openInputStream(uri)?.use { stream ->
                        profileBitmap = BitmapFactory.decodeStream(stream)
                    }
                } catch (_: Exception) {
                }
            }
        }
    } else null


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color(0xFFEF4444), Color.White)))
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Card(
            shape = androidx.compose.foundation.shape.RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFFFE4E6))
                            .clickable(enabled = imagePicker != null) { imagePicker?.launch("image/*") },
                        contentAlignment = Alignment.Center
                    ) {
                        if (profileBitmap != null) {
                            Image(
                                bitmap = profileBitmap!!.asImageBitmap(),
                                contentDescription = null
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Outlined.Person,
                                contentDescription = null,
                                tint = Color.Black,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .size(12.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF22C55E))
                        )
                    }
                    Spacer(modifier = Modifier.size(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            user?.name ?: "Utilizador",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                Icons.Outlined.Star,
                                contentDescription = null,
                                tint = Color(0xFFF59E0B),
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.size(6.dp))
                            Text(
                                "Jogador Premium",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Black
                            )
                            Spacer(modifier = Modifier.size(12.dp))
                            Text(
                                "Nível 47",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Black
                            )
                        }
                    }
                }
                Divider(color = Color(0xFFFFE4E6))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    StatItem(
                        icon = Icons.Outlined.CreditCard,
                        value = purchasesCount.toString(),
                        label = "Compras"
                    )
                    StatItem(
                        icon = Icons.Outlined.EmojiEvents,
                        value = achievementsCount.toString(),
                        label = "Conquistas"
                    )
                    StatItem(
                        icon = Icons.Outlined.Star,
                        value = points.toString(),
                        label = "Pontos"
                    )
                }
            }
        }

        Card(
            shape = androidx.compose.foundation.shape.RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF9FAFB))
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    "Informações da Conta",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )
                ListRow(
                    icon = Icons.Outlined.Email,
                    iconBg = Color(0xFFFFE4E6),
                    title = "Email",
                    subtitle = user?.email ?: ""
                )
                ListRow(
                    icon = Icons.Outlined.Phone,
                    iconBg = Color(0xFFFFE4E6),
                    title = "Telefone",
                    subtitle = user?.phone ?: ""
                )
                ListRow(
                    icon = Icons.Outlined.LocationOn,
                    iconBg = Color(0xFFFFE4E6),
                    title = "Localização",
                    subtitle = "Lisboa, Portugal"
                )
                ListRow(
                    icon = Icons.Outlined.CreditCard,
                    iconBg = Color(0xFFFFE4E6),
                    title = "Métodos de Pagamento",
                    subtitle = "2 cartões guardados",
                    onClick = onOpenPayments
                )
            }
        }

        Card(
            shape = androidx.compose.foundation.shape.RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF9FAFB))
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    "Configurações",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )
                ListRow(
                    icon = Icons.Outlined.Notifications,
                    iconBg = Color(0xFFFFE4E6),
                    title = "Notificações",
                    subtitle = "Gerir notificações push",
                    onClick = onOpenNotifications
                )
                
            }
        }

        Card(
            shape = androidx.compose.foundation.shape.RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Button(
                onClick = { onLogout?.invoke() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFEF4444),
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                Icon(
                    Icons.Outlined.ExitToApp,
                    contentDescription = null,
                    tint = Color.White
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text("Terminar Sessão", color = Color.White)
            }
        }
    }
}


@Composable
fun StatItem(icon: ImageVector, value: String, label: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(Color(0xFFFFE4E6)),
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = Color.Black)
        }
        Spacer(modifier = Modifier.size(6.dp))
        Text(value, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        Text(label, style = MaterialTheme.typography.bodySmall, color = Color.Black)
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
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.size(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, style = MaterialTheme.typography.bodyLarge)
            Text(
                subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    GameStoreTheme {
        ProfileScreen(
            user = User(
                id = "1",
                name = "José Silva",
                phone = "+351 912 345 678",
                email = "jose.silva@exemplo.pt",
                password = "******"
            ),
            purchasesCount = 28,
            achievementsCount = 15,
            points = 1240
        )
    }
}

@Composable
fun ListRow(
    icon: ImageVector,
    iconBg: Color,
    title: String,
    subtitle: String,
    onClick: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(enabled = onClick != null) { onClick?.invoke() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(iconBg),
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = Color.Black)
        }
        Spacer(modifier = Modifier.size(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, style = MaterialTheme.typography.bodyLarge)
            Text(subtitle, style = MaterialTheme.typography.bodyMedium, color = Color.Black)
        }
        Icon(
            imageVector = Icons.Outlined.ChevronRight,
            contentDescription = null,
            tint = Color.Black
        )
    }
}
