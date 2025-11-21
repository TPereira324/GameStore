package pt.iade.ei.gamestore.controller

import pt.iade.ei.gamestore.model.GameItem

interface GameItemRepository {
    fun itemsForStreetFootball(): List<GameItem>
    fun itemsForGalaxyExplorers(): List<GameItem>
}

class LocalGameItemRepository : GameItemRepository {
    override fun itemsForStreetFootball(): List<GameItem> = listOf(
        GameItem("Pacote de Celebrações", "10 celebrações exclusivas após o gol", 4.99),
        GameItem("Bola de Ouro Lendária", "Bola banhada a ouro com efeitos", 6.99),
        GameItem("Camisa Legendária Brasil", "Design exclusivo da seleção brasileira", 9.99),
        GameItem("Estádio Noturno Iluminado", "Iluminação espetacular e arquibancadas", 11.99)
    )

    override fun itemsForGalaxyExplorers(): List<GameItem> = listOf(
        GameItem("Companheiro Robótico AX-7", "Assistente robótico para exploração", 7.99),
        GameItem("Traje Espacial Quantum", "Proteção avançada com resistência à radiação", 8.99),
        GameItem("Expansão: Fronteira Alien", "Sistema estelar com planetas inexplorados", 12.99),
        GameItem("Nave Millennium X1", "Nave premium com hiperpropulsão", 15.99)
    )
}

interface GameItemApi {
    fun itemsForStreetFootball(): List<GameItem>
    fun itemsForGalaxyExplorers(): List<GameItem>
}

class RemoteGameItemRepository(private val api: GameItemApi) : GameItemRepository {
    override fun itemsForStreetFootball(): List<GameItem> = api.itemsForStreetFootball()
    override fun itemsForGalaxyExplorers(): List<GameItem> = api.itemsForGalaxyExplorers()
}