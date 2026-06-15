package com.grosskreutz.controledegastos.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.grosskreutz.controledegastos.database.dao.CartaoDao
import com.grosskreutz.controledegastos.database.dao.ContaDao
import com.grosskreutz.controledegastos.database.entity.CartaoEntity
import com.grosskreutz.controledegastos.database.entity.ContaEntity

@Database(
    entities = [
        CartaoEntity::class,
        ContaEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun cartaoDao(): CartaoDao

    abstract fun contaDao(): ContaDao
}