package com.grosskreutz.controledegastos.ui.conta.intent

sealed interface ContaIntent {

    data object Carregar : ContaIntent

    data class SalvarConta(
        val descricao: String,
        val valor: Double,
        val parcelas: Int,
        val competencia: String,
        val cartaoId: Long
    ) : ContaIntent
}