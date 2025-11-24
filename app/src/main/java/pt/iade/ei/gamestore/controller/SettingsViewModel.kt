package pt.iade.ei.gamestore.controller

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class SettingsViewModel : ViewModel() {
    private val _pushEnabled = mutableStateOf(true)
    val pushEnabled: State<Boolean> get() = _pushEnabled

    private val _promotionsEnabled = mutableStateOf(false)
    val promotionsEnabled: State<Boolean> get() = _promotionsEnabled

    fun setPushEnabled(value: Boolean) {
        _pushEnabled.value = value
    }

    fun setPromotionsEnabled(value: Boolean) {
        _promotionsEnabled.value = value
    }
}