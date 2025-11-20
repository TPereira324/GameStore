package pt.iade.ei.gamestore.model

data class PaymentMethod(
    val id: String,
    val brand: String,
    val last4: String,
    val expiryMonth: Int,
    val expiryYear: Int,
    val isDefault: Boolean = false
)