package pt.iade.ei.gamestore.controller

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import pt.iade.ei.gamestore.model.User

class AuthViewModel : ViewModel() {
    private val _loading = mutableStateOf(false)
    val loading: State<Boolean> get() = _loading

    private val _error = mutableStateOf<String?>(null)
    val error: State<String?> get() = _error

    private val _currentUser = mutableStateOf<User?>(null)
    val currentUser: State<User?> get() = _currentUser

    fun login(email: String, password: String, callback: (Boolean) -> Unit) {
        _loading.value = true
        _error.value = null
        val valid = listOf(
            "user@email.com" to "password123",
            "test@test.com" to "test123",
            "admin@admin.com" to "admin123"
        ).any { it.first == email && it.second == password }
        _loading.value = false
        if (valid) {
            _currentUser.value = User(
                id = "1",
                name = "José Silva",
                phone = "+351 912 345 678",
                email = email,
                password = password
            )
            callback(true)
        } else {
            _error.value = "Email ou senha incorretos"
            callback(false)
        }
    }

    fun register(name: String, phone: String, email: String, password: String, callback: (Boolean, String) -> Unit) {
        _loading.value = true
        _error.value = null
        val exists = listOf("existing@email.com", "test@test.com").contains(email)
        _loading.value = false
        if (exists) {
            _error.value = "Este email já está em uso"
            callback(false, "Este email já está em uso")
        } else {
            _currentUser.value = User(
                id = "2",
                name = name,
                phone = phone,
                email = email,
                password = password
            )
            callback(true, "Registro bem-sucedido")
        }
    }
}