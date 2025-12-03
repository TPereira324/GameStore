package pt.iade.ei.gamestore.model

import java.io.Serializable

data class GameItem(
    val title: String,
    val description: String,
    val price: Double,
    val id: String? = null,
    val gameId: String? = null,
    val sku: String? = null,
    val imageResId: Int? = null,
    val imageUrl: String? = null,
    val shortDescription: String? = null,
    val badge: String? = null,
    val seller: String? = null,
    val category: String? = null,
    val rarity: String? = null,
    val currency: String = "EUR",
    val originalPrice: Double? = null,
    val discountPercent: Int? = null,
    val available: Boolean = true,
    val purchaseLimit: Int? = null,
    val owned: Boolean = false,
    val requiresLevel: Int? = null,
    val consumable: Boolean = true,
) : Serializable {
}
