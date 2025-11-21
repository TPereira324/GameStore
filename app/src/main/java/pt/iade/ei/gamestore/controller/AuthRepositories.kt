package pt.iade.ei.gamestore.controller

import android.content.Context
import pt.iade.ei.gamestore.model.User

interface AuthRepository {
    fun login(context: Context, email: String, password: String): User?
    fun register(
        context: Context,
        name: String,
        phone: String,
        email: String,
        password: String
    ): Pair<User?, String?>

    fun logout(context: Context)
    fun restore(context: Context): User
}

class LocalAuthRepository : AuthRepository {
    override fun login(context: Context, email: String, password: String): User? {
        val valid = listOf(
            "user@email.com" to "password123",
            "test@test.com" to "test123"
        ).any { it.first == email && it.second == password }
        return if (valid) {
            val u = User(
                id = "1",
                name = "José Silva",
                phone = "+351 912 345 678",
                email = email,
                password = password
            )
            persistUser(context, u)
            u
        } else null
    }

    override fun register(
        context: Context,
        name: String,
        phone: String,
        email: String,
        password: String
    ): Pair<User?, String?> {
        val exists = listOf("existing@email.com", "test@test.com").contains(email)
        return if (exists) Pair(null, "Este email já está em uso") else {
            val u = User(id = "2", name = name, phone = phone, email = email, password = password)
            persistUser(context, u)
            Pair(u, null)
        }
    }

    override fun logout(context: Context) {
        clearPersistedUser(context)
    }

    override fun restore(context: Context): User {
        val p = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
        val logged = p.getBoolean("logged_in", false)
        if (logged) {
            val id = p.getString("id", null)
            val name = p.getString("name", null)
            val phone = p.getString("phone", null)
            val email = p.getString("email", null)
            val password = p.getString("password", null)
            if (id != null && name != null && phone != null && email != null && password != null) {
                return User(id, name, phone, email, password)
            }
        }
        val default = User(
            id = "1",
            name = "Usuário Padrão",
            phone = "+351 900 000 000",
            email = "user@email.com",
            password = "password123"
        )
        persistUser(context, default)
        return default
    }

}

interface AuthApi {
    fun login(email: String, password: String): User?
    fun register(name: String, phone: String, email: String, password: String): Pair<User?, String?>
    fun logout()
}

class RemoteAuthRepository(private val api: AuthApi) : AuthRepository {
    override fun login(context: Context, email: String, password: String): User? {
        val u = api.login(email, password)
        if (u != null) persistUser(context, u)
        return u
    }

    override fun register(
        context: Context,
        name: String,
        phone: String,
        email: String,
        password: String
    ): Pair<User?, String?> {
        val res = api.register(name, phone, email, password)
        val u = res.first
        if (u != null) persistUser(context, u)
        return res
    }

    override fun logout(context: Context) {
        api.logout()
        clearPersistedUser(context)
    }

    override fun restore(context: Context): User {
        val p = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
        val logged = p.getBoolean("logged_in", false)
        if (logged) {
            val id = p.getString("id", null)
            val name = p.getString("name", null)
            val phone = p.getString("phone", null)
            val email = p.getString("email", null)
            val password = p.getString("password", null)
            if (id != null && name != null && phone != null && email != null && password != null) {
                return User(id, name, phone, email, password)
            }
        }
        val default = User(
            id = "1",
            name = "Usuário Padrão",
            phone = "+351 900 000 000",
            email = "user@email.com",
            password = "password123"
        )
        persistUser(context, default)
        return default
    }

}

private fun persistUser(context: Context, user: User?) {
    if (user == null) return
    val p = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
    p.edit()
        .putBoolean("logged_in", true)
        .putString("id", user.id)
        .putString("name", user.name)
        .putString("phone", user.phone)
        .putString("email", user.email)
        .putString("password", user.password)
        .apply()
}

private fun clearPersistedUser(context: Context) {
    val p = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
    p.edit().clear().apply()
}