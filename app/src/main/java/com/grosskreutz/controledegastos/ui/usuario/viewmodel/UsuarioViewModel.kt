package com.grosskreutz.controledegastos.ui.usuario.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grosskreutz.controledegastos.database.repository.UsuarioRepository
import com.grosskreutz.controledegastos.ui.usuario.state.UsuarioState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UsuarioViewModel(
    private val repository: UsuarioRepository
) : ViewModel() {

    private val _state = MutableStateFlow(UsuarioState())
    val state: StateFlow<UsuarioState> = _state

    init {
        carregarUsuario()
    }

    private fun carregarUsuario() {
        viewModelScope.launch {
            repository.getNomeUsuario().collect {
                _state.value = UsuarioState(
                    nome = it,
                    carregando = false
                )
            }
        }
    }

    fun salvarNome(nome: String) {
        viewModelScope.launch {
            repository.salvarNome(nome)
        }
    }
}