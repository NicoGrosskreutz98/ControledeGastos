package com.grosskreutz.controledegastos.ui.resumo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grosskreutz.controledegastos.database.entity.ContaEntity
import com.grosskreutz.controledegastos.database.model.ResumoConta
import com.grosskreutz.controledegastos.database.repository.ContaRepository
import com.grosskreutz.controledegastos.ui.resumo.state.ResumoState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ResumoViewModel(
    private val repository: ContaRepository
) : ViewModel() {

    private val _state =
        MutableStateFlow(
            ResumoState()
        )

    val state: StateFlow<ResumoState>
        get() = _state

    fun carregarWithScope(
        competencia: String,
        cartaoName: String
    ) {
        viewModelScope.launch {
            carregar(competencia, cartaoName)
        }
    }

    suspend fun carregar(
        competencia: String,
        cartaoName: String
    ) {
        repository
            .getResumoMes(competencia)
            .collect { c ->

                _state.update {
                    it.copy(contas = c)
                }

                val simulacoes =_state.value.simulacoes
                if (simulacoes.isNotEmpty()){
                    val simulacoesCurrentMonth = simulacoes.filter { it.competencia == competencia }
                    val resumo = mutableListOf<ResumoConta>()

                    if (simulacoesCurrentMonth.isNotEmpty()){
                        simulacoesCurrentMonth.forEach{
                            resumo.add(ResumoConta(
                                it.descricao,
                                it.valorParcela,
                                it.competencia,
                                cartaoName
                            ))
                        }
                        resumo.addAll(c)

                        _state.update {
                            it.copy(contas = resumo)
                        }
                    }
                }
            }
    }

    suspend fun carregarCompetencias(){
        repository
            .getCompetencias()
            .first().let { c ->

                _state.update {
                    it.copy(competencias = c)
                }
            }
    }

    fun setSimulacoes(simlacoes: List<ContaEntity>){
        _state.update {
            it.copy(simulacoes = simlacoes)
        }

        val competenciasSimulacao = simlacoes.map { it.competencia }
        val competencias = _state.value.competencias.toMutableList()
        competenciasSimulacao.forEach {
            if (!competencias.contains(it))
                competencias.add(it)
        }

        _state.update {
            it.copy(competencias = competencias.sorted())
        }
    }

    fun init(
        simlacoes: List<ContaEntity>,
        competencia: String,
        cartaoName: String){

        viewModelScope.launch {
            carregarCompetencias()
            if (simlacoes.isNotEmpty())
                setSimulacoes(simlacoes)

            _state.update {
                it.copy(cartaoNameSimulacao = cartaoName)
            }

            carregar(
                competencia,
                cartaoName
            )
        }
    }
}