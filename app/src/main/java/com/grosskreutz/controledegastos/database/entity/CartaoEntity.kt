package com.grosskreutz.controledegastos.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cartoes")
data class CartaoEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val nome: String
)