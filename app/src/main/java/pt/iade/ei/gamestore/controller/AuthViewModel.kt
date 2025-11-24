package pt.iade.ei.gamestore.controller

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import pt.iade.ei.gamestore.model.User

class AuthViewModel(
    private val repo: AuthRepository = LocalAuthRepository()
) : ViewModel() {
    private val _loading = mutableStateOf(false)
    val loading: State<Boolean> get() = _loading

    private val _error = mutableStateOf<String?>(null)
    val error: State<String?> get() = _error

    private val _currentUser = mutableStateOf<User?>(null)
    val currentUser: State<User?> get() = _currentUser

    fun login(context: Context, email: String, password: String, callback: (Boolean) -> Unit) {
        _loading.value = true
        _error.value = null
        val user = repo.login(context, email, password)
        _loading.value = false
        if (user != null) {
            _currentUser.value = user
            callback(true)
        } else {
            _error.value = "Email ou senha incorretos"
            callback(false)
        }
    }

    fun register(
        context: Context,
        name: String,
        phone: String,
        email: String,
        password: String,
        callback: (Boolean, String) -> Unit
    ) {
        _loading.value = true
        _error.value = null
        val (user, error) = repo.register(context, name, phone, email, password)
        _loading.value = false
        if (user == null) {
            _error.value = error
            callback(false, error ?: "Erro")
        } else {
            _currentUser.value = user
            callback(true, "Registro bem-sucedido")
        }
    }

    fun logout(context: Context) {
        repo.logout(context)
        _currentUser.value = null
    }

    fun restore(context: Context) {
        _currentUser.value = repo.restore(context)
    }


}