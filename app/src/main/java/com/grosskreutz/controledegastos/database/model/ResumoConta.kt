package com.grosskreutz.controledegastos.database.model

import androidx.room.ColumnInfo

data class ResumoConta(

    @ColumnInfo(name = "descricao")
    val descricao: String,

    @ColumnInfo(name = "valorParcela")
    val valorParcela: Double,

    @ColumnInfo(name = "competencia")
    val competencia: String,

    @ColumnInfo(name = "cartaoNome")
    val cartaoNome: String
)