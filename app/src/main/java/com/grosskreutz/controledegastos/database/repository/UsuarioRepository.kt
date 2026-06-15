package com.grosskreutz.controledegastos.database.repository

import com.grosskreutz.controledegastos.database.datastore.UserPreferences

class UsuarioRepository(
    private val preferences: UserPreferences
) {

    suspend fun salvarNome(
        nome: String
    ) {

        preferences.salvarNome(nome)
    }

    fun getNomeUsuario() =
        preferences.getNome()
}