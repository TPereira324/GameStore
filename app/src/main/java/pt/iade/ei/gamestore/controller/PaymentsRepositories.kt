package pt.iade.ei.gamestore.controller

import pt.iade.ei.gamestore.model.PaymentMethod

interface PaymentsRepository {
    fun list(): List<PaymentMethod>
    fun add(method: PaymentMethod)
    fun remove(id: String)
    fun setDefault(id: String)
}

class LocalPaymentsRepository : PaymentsRepository {
    private val methods = mutableListOf(
        PaymentMethod("pm1", "Visa", "1234", 8, 2026, true),
        PaymentMethod("pm2", "Mastercard", "5678", 11, 2027, false)
    )

    override fun list(): List<PaymentMethod> = methods
    override fun add(method: PaymentMethod) {
        methods.add(method)
    }

    override fun remove(id: String) {
        methods.removeAll { it.id == id }
    }

    override fun setDefault(id: String) {
        for (i in methods.indices) {
            val m = methods[i]
            methods[i] = m.copy(isDefault = m.id == id)
        }
    }
}

interface PaymentsApi {
    fun list(): List<PaymentMethod>
    fun add(method: PaymentMethod)
    fun remove(id: String)
    fun setDefault(id: String)
}

class RemotePaymentsRepository(private val api: PaymentsApi) : PaymentsRepository {
    override fun list(): List<PaymentMethod> = api.list()
    override fun add(method: PaymentMethod) {
        api.add(method)
    }

    override fun remove(id: String) {
        api.remove(id)
    }

    override fun setDefault(id: String) {
        api.setDefault(id)
    }
}