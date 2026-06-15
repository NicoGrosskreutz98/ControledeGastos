package com.grosskreutz.controledegastos.ui.cartao.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grosskreutz.controledegastos.database.repository.CartaoRepository
import com.grosskreutz.controledegastos.ui.cartao.state.CartaoState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CartaoViewModel(
    private val repository: CartaoRepository
) : ViewModel() {

    private val _state = MutableStateFlow(CartaoState())
    val state: StateFlow<CartaoState> = _state

    init {
        carregar()
    }

    fun carregar() {
        viewModelScope.launch {
            repository.getCartoes().collect {
                _state.value = _state.value.copy(
                    cartoes = it
                )
            }
        }
    }

    fun salvar(nome: String) {
        viewModelScope.launch {
            repository.salvar(nome)

            carregar()
        }
    }
}