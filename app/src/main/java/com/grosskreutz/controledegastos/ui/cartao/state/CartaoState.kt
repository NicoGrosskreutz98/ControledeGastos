package com.grosskreutz.controledegastos.ui.cartao.state

import com.grosskreutz.controledegastos.database.entity.CartaoEntity

data class CartaoState(
    val cartoes: List<CartaoEntity> = emptyList()
)