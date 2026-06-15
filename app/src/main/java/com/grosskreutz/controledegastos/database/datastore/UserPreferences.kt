package com.grosskreutz.controledegastos.database.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(
    name = "controle_gastos"
)

class UserPreferences(
    private val context: Context
) {

    companion object {

        private val USER_NAME =
            stringPreferencesKey("user_name")

        private val SIMULACAO =
            stringPreferencesKey("simulacao")
    }

    suspend fun salvarNome(
        nome: String
    ) {

        context.dataStore.edit {

            it[USER_NAME] = nome
        }
    }

    fun getNome() =

        context.dataStore.data.map {

            it[USER_NAME] ?: ""
        }

    suspend fun salvarSimulacao(
        json: String
    ) {

        context.dataStore.edit {

            it[SIMULACAO] = json
        }
    }
}