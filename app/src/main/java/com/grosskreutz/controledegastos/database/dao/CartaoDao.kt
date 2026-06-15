package com.grosskreutz.controledegastos.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.grosskreutz.controledegastos.database.entity.CartaoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CartaoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserir(cartao: CartaoEntity)

    @Query("SELECT * FROM cartoes ORDER BY nome")
    fun getCartoes(): Flow<List<CartaoEntity>>

    @Query("SELECT * FROM cartoes")
    suspend fun getCartoesList(): List<CartaoEntity>
}