package pt.iade.ei.gamestore.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import pt.iade.ei.gamestore.controller.AuthViewModel
import pt.iade.ei.gamestore.controller.PaymentViewModel
import pt.iade.ei.gamestore.controller.ProfileStatsViewModel
import pt.iade.ei.gamestore.controller.SecurityViewModel
import pt.iade.ei.gamestore.controller.SettingsViewModel
import pt.iade.ei.gamestore.controller.StoreViewModel
import pt.iade.ei.gamestore.ui.theme.GameStoreTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainNavigation(screen: String? = null, gameId: String? = null) {
    val auth = remember { AuthViewModel() }
    val store = remember { StoreViewModel() }
    val settings = remember { SettingsViewModel() }
    val security = remember { SecurityViewModel() }
    val payments = remember { PaymentViewModel() }
    val stats = remember { ProfileStatsViewModel() }
    val context = LocalContext.current
    val currentRoute = screen ?: "tela_principal"
    LaunchedEffect(Unit) { auth.restore(context) }
    Scaffold(
        topBar = {
            val title = when (currentRoute) {
                "auth/login" -> "Entrar"
                "auth/register" -> "Criar conta"
                "history" -> "Histórico de Compras"
                "account/payments" -> "Métodos de Pagamento"
                "profile" -> "Perfil"
                "settings/notifications" -> "Notificações"
                "settings/privacy" -> "Privacidade"
                "detail" -> store.games.firstOrNull { it.id == gameId }?.title ?: "Detalhe"
                else -> "Tela Principal"
            }
            val showBack = currentRoute !in listOf("tela_principal", "history", "profile")
            val showActions = !currentRoute.startsWith("auth/")
            GameStoreTopBar(
                title = title,
                showBack = showBack,
                showActions = showActions,
                onBack = { (context as? android.app.Activity)?.finish() },
                onNotifications = {
                    val i = android.content.Intent(context, pt.iade.ei.gamestore.MainActivity::class.java)
                    i.putExtra("screen", "settings/notifications")
                    context.startActivity(i)
                }
            )
        },
        bottomBar = {
            if (!currentRoute.startsWith("auth/")) {
                val selectedIndex = when {
                    currentRoute == "history" -> 1
                    currentRoute == "profile" -> 2
                    else -> 0
                }
                GameStoreBottomBar(selectedIndex = selectedIndex, onSelectedIndexChange = {
                    when (it) {
                        0 -> {
                            val i = android.content.Intent(context, pt.iade.ei.gamestore.MainActivity::class.java)
                            i.putExtra("screen", "tela_principal")
                            context.startActivity(i)
                        }
                        1 -> {
                            val i = android.content.Intent(context, pt.iade.ei.gamestore.MainActivity::class.java)
                            i.putExtra("screen", "history")
                            context.startActivity(i)
                        }
                        2 -> {
                            val loggedIn = auth.currentUser.value != null
                            if (loggedIn) {
                                val i = android.content.Intent(context, pt.iade.ei.gamestore.MainActivity::class.java)
                                i.putExtra("screen", "profile")
                                context.startActivity(i)
                            } else {
                                val i = android.content.Intent(context, pt.iade.ei.gamestore.view.LoginActivity::class.java)
                                context.startActivity(i)
                            }
                        }
                    }
                })
            }
        }
    ) { inner ->
        Box(
            modifier = Modifier
                .padding(inner)
                .background(Brush.verticalGradient(listOf(Color(0xFFEF4444), Color.White)))
        ) {
            when (currentRoute) {
                "tela_principal" -> {
                    TelaPrincipalScreen(
                        games = store.games,
                        onGameClick = { game ->
                            val i = android.content.Intent(context, pt.iade.ei.gamestore.MainActivity::class.java)
                            i.putExtra("screen", "detail")
                            i.putExtra("gameId", game.id)
                            context.startActivity(i)
                        }
                    )
                }
                "history" -> {
                    HistoryScreen(
                        purchases = store.purchases,
                        games = store.games,
                        onClear = { store.clearPurchases() }
                    )
                }
                "profile" -> {
                    ProfileScreen(
                        user = auth.currentUser.value,
                        purchasesCount = store.purchases.size,
                        achievementsCount = stats.achievements.size,
                        points = stats.points.value,
                        onLogout = {
                            auth.logout(context)
                            val i = android.content.Intent(context, pt.iade.ei.gamestore.MainActivity::class.java)
                            i.putExtra("screen", "tela_principal")
                            context.startActivity(i)
                        },
                        onOpenNotifications = {
                            val i = android.content.Intent(context, pt.iade.ei.gamestore.MainActivity::class.java)
                            i.putExtra("screen", "settings/notifications")
                            context.startActivity(i)
                        },
                        onOpenPrivacy = {
                            val i = android.content.Intent(context, pt.iade.ei.gamestore.MainActivity::class.java)
                            i.putExtra("screen", "settings/privacy")
                            context.startActivity(i)
                        },
                        onOpenPayments = {
                            val i = android.content.Intent(context, pt.iade.ei.gamestore.MainActivity::class.java)
                            i.putExtra("screen", "account/payments")
                            context.startActivity(i)
                        }
                    )
                }
                "settings/notifications" -> {
                    NotificationsSettingsScreen(settings = settings)
                }
                "settings/privacy" -> {
                    PrivacySecurityScreen(security = security)
                }
                "account/payments" -> {
                    PaymentMethodsScreen(vm = payments)
                }
                "detail" -> {
                    val game = store.games.firstOrNull { it.id == gameId }
                    if (game != null) {
                        val onBuy = {
                            val userId = auth.currentUser.value?.id
                            if (userId != null) {
                                store.addPurchase(userId, game)
                                val i = android.content.Intent(context, pt.iade.ei.gamestore.MainActivity::class.java)
                                i.putExtra("screen", "history")
                                context.startActivity(i)
                            } else {
                                val i = android.content.Intent(context, pt.iade.ei.gamestore.view.LoginActivity::class.java)
                                context.startActivity(i)
                            }
                        }
                        val onBuyItem: (pt.iade.ei.gamestore.model.GameItem) -> Unit = { item ->
                            val userId = auth.currentUser.value?.id
                            if (userId != null) {
                                store.addPurchaseItem(userId, game, item.title, item.price)
                                val i = android.content.Intent(context, pt.iade.ei.gamestore.MainActivity::class.java)
                                i.putExtra("screen", "history")
                                context.startActivity(i)
                            } else {
                                val i = android.content.Intent(context, pt.iade.ei.gamestore.view.LoginActivity::class.java)
                                context.startActivity(i)
                            }
                        }
                        if (game.title == "Street Football") StreetFootballDetailScreen(
                            onBuy = onBuy,
                            onBuyItem = onBuyItem
                        ) else GalaxyExplorersDetailScreen(onBuy = onBuy, onBuyItem = onBuyItem)
                    } else {
                        Text("Jogo não encontrado")
                    }
                }
                else -> {
                    TelaPrincipalScreen(
                        games = store.games,
                        onGameClick = { game ->
                            val i = android.content.Intent(context, pt.iade.ei.gamestore.MainActivity::class.java)
                            i.putExtra("screen", "detail")
                            i.putExtra("gameId", game.id)
                            context.startActivity(i)
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainNavigationPreview() {
    GameStoreTheme { MainNavigation() }
}