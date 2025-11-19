package pt.iade.ei.gamestore.model

data class User(
    val id: String,
    val name: String,
    val phone: String,
    val email: String,
    val password: String
)