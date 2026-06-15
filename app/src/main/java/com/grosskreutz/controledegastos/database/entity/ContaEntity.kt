package com.grosskreutz.controledegastos.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "contas",
    foreignKeys = [
        ForeignKey(
            entity = CartaoEntity::class,
            parentColumns = ["id"],
            childColumns = ["cartaoId"]
        )
    ]
)
data class ContaEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val descricao: String,

    val valorParcela: Double,

    val numeroParcela: Int,

    val totalParcelas: Int,

    val competencia: String,

    val cartaoId: Long
)