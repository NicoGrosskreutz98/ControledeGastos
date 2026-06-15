package com.grosskreutz.controledegastos.ui.conta.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grosskreutz.controledegastos.database.datastore.UserPreferences
import com.grosskreutz.controledegastos.database.entity.ContaEntity
import com.grosskreutz.controledegastos.database.model.ResumoConta
import com.grosskreutz.controledegastos.database.repository.ContaRepository
import com.grosskreutz.controledegastos.ui.conta.state.ContaState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class ContaViewModel(
    private val repository: ContaRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ContaState())
    val state: StateFlow<ContaState> = _state

    @RequiresApi(Build.VERSION_CODES.O)
    fun salvarConta(
        descricao: String,
        valor: Double,
        parcelas: Int,
        competencia: String,
        cartaoId: Long
    ) {

        if (parcelas <= 0) {
            return
        }

        viewModelScope.launch {

            _state.value = _state.value.copy(
                loading = true
            )

            repository.salvarConta(
                descricao,
                valor,
                parcelas,
                competencia,
                cartaoId
            )

            _state.value = ContaState(
                sucesso = true
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getSimulacao(
        descricao: String,
        valor: Double,
        parcelas: Int,
        competencia: String,
        cartaoId: Long
    ): List<ContaEntity> {

        return repository.simularConta(
            descricao,
            valor,
            parcelas,
            competencia,
            cartaoId
        )
    }

    fun isDataValida(data: String): Boolean {

        return try {

            val sdf = SimpleDateFormat(
                "MM/yyyy",
                Locale.getDefault()
            )

            sdf.isLenient = false

            sdf.parse(data)

            true

        } catch (e: Exception) {

            false
        }
    }
}