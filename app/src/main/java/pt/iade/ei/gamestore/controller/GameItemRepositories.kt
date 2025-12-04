package pt.iade.ei.gamestore.controller

import pt.iade.ei.gamestore.R
import pt.iade.ei.gamestore.model.GameItem

interface GameItemRepository {
    fun itemsForStreetFootball(): List<GameItem>
    fun itemsForGalaxyExplorers(): List<GameItem>
}

class LocalGameItemRepository : GameItemRepository {
    override fun itemsForStreetFootball(): List<GameItem> = listOf(
        GameItem(
            title = "Pacote de Celebrações",
            description = "10 celebrações exclusivas após o gol",
            shortDescription = "Celebrações exclusivas",
            seller = "Urban Sports Co.",
            price = 4.99,
            imageResId = R.drawable.celebrator
        ),
        GameItem(
            title = "Bola de Ouro Lendária",
            description = "Bola banhada a ouro com efeitos",
            shortDescription = "Bola com efeitos brilhantes",
            seller = "Legacy Gear",
            price = 6.99,
            imageResId = R.drawable.bola_de_ouro
        ),
        GameItem(
            title = "Camisa Legendária Brasil",
            description = "Design exclusivo da seleção brasileira",
            shortDescription = "Camisa lendária da seleção",
            seller = "Brasil Classics",
            price = 9.99,
            imageResId = R.drawable.camisa_do_brasil
        )
    )

    override fun itemsForGalaxyExplorers(): List<GameItem> = listOf(
        GameItem(
            title = "Companheiro Robótico AX-7",
            description = "Assistente robótico para exploração",
            shortDescription = "Assistente robótico inteligente",
            seller = "Nova Labs",
            price = 7.99,
            imageResId = R.drawable.robo
        ),
        GameItem(
            title = "Traje Espacial Quantum",
            description = "Proteção avançada com resistência à radiação",
            shortDescription = "Traje espacial de alta proteção",
            seller = "Quantum Outfitters",
            price = 8.99,
            imageResId = R.drawable.traje
        ),
        GameItem(
            title = "Expansão: Fronteira Alien",
            description = "Sistema estelar com planetas inexplorados",
            shortDescription = "Nova fronteira intergaláctica",
            seller = "Frontier Studios",
            price = 12.99,
            imageResId = R.drawable.expanse
        )
    )
}

