package pt.iade.ei.gamestore.controller

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class SecurityViewModel : ViewModel() {
    private val _twoFactorEnabled = mutableStateOf(false)
    val twoFactorEnabled: State<Boolean> get() = _twoFactorEnabled

    fun setTwoFactorEnabled(value: Boolean) {
        _twoFactorEnabled.value = value
    }

    fun updatePassword(newPassword: String): Boolean {
        return newPassword.isNotBlank() && newPassword.length >= 6
    }
}