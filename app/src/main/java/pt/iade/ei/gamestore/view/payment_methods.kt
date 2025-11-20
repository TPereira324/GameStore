package pt.iade.ei.gamestore.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pt.iade.ei.gamestore.controller.PaymentViewModel
import pt.iade.ei.gamestore.ui.theme.GameStoreTheme

@Composable
fun PaymentMethodsScreen(vm: PaymentViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color(0xFFEF4444), Color.White)))
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Métodos de Pagamento", style = MaterialTheme.typography.titleLarge)
        vm.methods.forEach { m ->
            Card(colors = CardDefaults.cardColors(containerColor = Color.White)) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        m.brand + " terminando em " + m.last4 + if (m.isDefault) " • Principal" else "",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        "Expira %02d/%02d".format(m.expiryMonth, m.expiryYear % 100),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Black
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            vm.methods.size.toString() + " cartões guardados",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Black
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PaymentMethodsPreview() {
    GameStoreTheme { PaymentMethodsScreen(vm = remember { PaymentViewModel() }) }
}