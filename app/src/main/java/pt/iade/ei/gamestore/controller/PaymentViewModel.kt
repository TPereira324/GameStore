package pt.iade.ei.gamestore.controller

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import pt.iade.ei.gamestore.model.PaymentMethod

class PaymentViewModel(
    private val repo: PaymentsRepository = LocalPaymentsRepository()
) : ViewModel() {
    val methods: SnapshotStateList<PaymentMethod> = mutableStateListOf(*repo.list().toTypedArray())

    fun add(method: PaymentMethod) {
        repo.add(method)
        methods.add(method)
    }

    fun remove(id: String) {
        repo.remove(id)
        methods.removeAll { it.id == id }
    }

    fun setDefault(id: String) {
        repo.setDefault(id)
        for (i in methods.indices) {
            val m = methods[i]
            methods[i] = m.copy(isDefault = m.id == id)
        }
    }
}