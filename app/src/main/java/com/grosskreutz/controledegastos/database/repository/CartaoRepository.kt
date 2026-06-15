package com.grosskreutz.controledegastos.database.repository

import com.grosskreutz.controledegastos.database.dao.CartaoDao
import com.grosskreutz.controledegastos.database.entity.CartaoEntity

class CartaoRepository(
    private val dao: CartaoDao
) {

    fun getCartoes() =
        dao.getCartoes()

    suspend fun salvar(
        nome: String
    ) {

        dao.inserir(
            CartaoEntity(
                nome = nome
            )
        )
    }

    suspend fun getLista() =
        dao.getCartoesList()
}