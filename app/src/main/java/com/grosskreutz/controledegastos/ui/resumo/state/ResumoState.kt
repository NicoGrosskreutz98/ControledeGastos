package com.grosskreutz.controledegastos.ui.resumo.state

import com.grosskreutz.controledegastos.database.entity.ContaEntity
import com.grosskreutz.controledegastos.database.model.ResumoConta

data class ResumoState(

    val contas: List<ResumoConta> = emptyList(),
    val competencias: List<String> = emptyList(),
    val simulacoes: List<ContaEntity> = emptyList(),
    val cartaoNameSimulacao : String = ""
)