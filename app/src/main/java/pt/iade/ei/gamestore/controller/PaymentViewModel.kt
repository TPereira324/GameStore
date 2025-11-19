package pt.iade.ei.gamestore.controller

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import pt.iade.ei.gamestore.model.PaymentMethod

class PaymentViewModel : ViewModel() {
    val methods: SnapshotStateList<PaymentMethod> = mutableStateListOf(
        PaymentMethod(
            id = "pm1",
            brand = "Visa",
            last4 = "1234",
            expiryMonth = 8,
            expiryYear = 2026,
            isDefault = true
        ),
        PaymentMethod(
            id = "pm2",
            brand = "Mastercard",
            last4 = "5678",
            expiryMonth = 11,
            expiryYear = 2027,
            isDefault = false
        )
    )

    fun add(method: PaymentMethod) {
        methods.add(method)
    }

    fun remove(id: String) {
        methods.removeAll { it.id == id }
    }

    fun setDefault(id: String) {
        for (i in methods.indices) {
            val m = methods[i]
            methods[i] = m.copy(isDefault = m.id == id)
        }
    }
}