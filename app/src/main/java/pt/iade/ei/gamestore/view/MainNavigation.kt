package pt.iade.ei.gamestore.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import pt.iade.ei.gamestore.controller.AuthViewModel
import pt.iade.ei.gamestore.controller.StoreViewModel
import pt.iade.ei.gamestore.ui.theme.GameStoreTheme
import pt.iade.ei.gamestore.controller.SettingsViewModel
import pt.iade.ei.gamestore.controller.SecurityViewModel
import pt.iade.ei.gamestore.controller.PaymentViewModel
import pt.iade.ei.gamestore.controller.ProfileStatsViewModel
import pt.iade.ei.gamestore.view.PaymentMethodsScreen
import pt.iade.ei.gamestore.view.*
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material3.Text

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainNavigation() {
    val auth = remember { AuthViewModel() }
    val store = remember { StoreViewModel() }
    val settings = remember { SettingsViewModel() }
    val security = remember { SecurityViewModel() }
    val payments = remember { PaymentViewModel() }
    val stats = remember { ProfileStatsViewModel() }
    val navController = rememberNavController()
    val context = LocalContext.current
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route ?: "tela_principal"
    LaunchedEffect(Unit) { auth.restore(context) }
    Scaffold(
        topBar = {
            val title = when {
                currentRoute.startsWith("detail/") -> {
                    val id = backStackEntry?.arguments?.getString("gameId")
                    store.games.firstOrNull { it.id == id }?.title ?: "Detalhe"
                }
                currentRoute == "auth/login" -> "Entrar"
                currentRoute == "auth/register" -> "Criar conta"
                currentRoute == "history" -> "Histórico de Compras"
                currentRoute == "account/payments" -> "Métodos de Pagamento"
                currentRoute == "profile" -> "Perfil"
                else -> "Tela Principal"
            }
            val showBack = !currentRoute.startsWith("auth/") && currentRoute !in listOf("tela_principal", "history", "profile")
            val showActions = !currentRoute.startsWith("auth/")
            GameStoreTopBar(
                title = title,
                showBack = showBack,
                showActions = showActions,
                onBack = { navController.popBackStack() },
                onNotifications = { navController.navigate("settings/notifications") }
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
                        0 -> navController.navigate("tela_principal")
                        1 -> navController.navigate("history")
                        2 -> {
                            val loggedIn = auth.currentUser.value != null
                            if (loggedIn) navController.navigate("profile") else navController.navigate("auth/login")
                        }
                    }
                })
            }
        }
    ) { inner ->
        Box(
            modifier = Modifier
                .padding(inner)
                .background(Brush.verticalGradient(listOf(Color(0xFFEF4444), Color.White)))) {
            NavHost(navController = navController, startDestination = "tela_principal") {
                composable("tela_principal") {
                    TelaPrincipalScreen(games = store.games, onGameClick = { game -> navController.navigate("detail/${game.id}") })
                }
                composable("history") { HistoryScreen(purchases = store.purchases, games = store.games, onClear = { store.clearPurchases() }) }
                composable("profile") {
                    ProfileScreen(
                        user = auth.currentUser.value,
                        purchasesCount = store.purchases.size,
                        achievementsCount = stats.achievements.size,
                        points = stats.points.value,
                        onLogout = { auth.logout(context); navController.navigate("tela_principal") },
                        onOpenNotifications = { navController.navigate("settings/notifications") },
                        onOpenPrivacy = { navController.navigate("settings/privacy") },
                        onOpenPayments = { navController.navigate("account/payments") }
                    )
                }
                composable("auth/login") {
                    LoginScreen(
                        auth = auth,
                        onLoginSuccess = { navController.navigate("profile") { popUpTo("auth/login") { inclusive = true } } },
                        onNavigateToRegister = { navController.navigate("auth/register") }
                    )
                }
                composable("auth/register") {
                    RegisterScreen(
                        auth = auth,
                        onRegisterSuccess = { navController.navigate("auth/login") { popUpTo("auth/register") { inclusive = true } } },
                        onNavigateToLogin = { navController.navigate("auth/login") }
                    )
                }
                composable("settings/notifications") { NotificationsSettingsScreen(settings = settings) }
                composable("settings/privacy") { PrivacySecurityScreen(security = security) }
                composable("account/payments") { PaymentMethodsScreen(vm = payments) }
                composable("detail/{gameId}") { entry ->
                    val id = entry.arguments?.getString("gameId")
                    val game = store.games.firstOrNull { it.id == id }
                    if (game != null) {
                        val onBuy = {
                            val userId = auth.currentUser.value?.id
                            if (userId != null) {
                                store.addPurchase(userId, game)
                                navController.navigate("history")
                            } else {
                                navController.navigate("auth/login")
                            }
                        }
                        val onBuyItem: (pt.iade.ei.gamestore.model.GameItem) -> Unit = { item ->
                            val userId = auth.currentUser.value?.id
                            if (userId != null) {
                                store.addPurchaseItem(userId, game, item.title, item.price)
                                navController.navigate("history")
                            } else {
                                navController.navigate("auth/login")
                            }
                        }
                        if (game.title == "Street Football") StreetFootballDetailScreen(onBuy = onBuy, onBuyItem = onBuyItem) else GalaxyExplorersDetailScreen(onBuy = onBuy, onBuyItem = onBuyItem)
                    } else {
                        Text("Jogo não encontrado")
                    }
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