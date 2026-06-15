package com.grosskreutz.controledegastos.database.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.grosskreutz.controledegastos.database.dao.ContaDao
import com.grosskreutz.controledegastos.database.entity.ContaEntity
import com.grosskreutz.controledegastos.util.DateUtils

class ContaRepository(
    private val dao: ContaDao
) {

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun salvarConta(
        descricao: String,
        valorTotal: Double,
        parcelas: Int,
        competencia: String,
        cartaoId: Long
    ) {

        val valorParcela =
            valorTotal / parcelas

        val lista = mutableListOf<ContaEntity>()

        repeat(parcelas) { indice ->

            lista.add(
                ContaEntity(
                    descricao = descricao,
                    valorParcela = valorParcela,
                    numeroParcela = indice + 1,
                    totalParcelas = parcelas,
                    competencia = DateUtils.adicionarMeses(
                        competencia,
                        indice
                    ),
                    cartaoId = cartaoId
                )
            )
        }

        dao.inserirTodas(lista)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun simularConta(
        descricao: String,
        valorTotal: Double,
        parcelas: Int,
        competencia: String,
        cartaoId: Long
    ): List<ContaEntity> {

        val valorParcela =
            valorTotal / parcelas

        val lista =
            mutableListOf<ContaEntity>()

        repeat(parcelas) { indice ->

            lista.add(
                ContaEntity(
                    descricao = descricao,
                    valorParcela = valorParcela,
                    numeroParcela = indice + 1,
                    totalParcelas = parcelas,
                    competencia = DateUtils.adicionarMeses(
                        competencia,
                        indice
                    ),
                    cartaoId = cartaoId
                )
            )
        }

        return lista
    }

    fun getResumoMes(
        competencia: String
    ) = dao.getResumoMes(
        competencia)

    fun getCompetencias() = dao.getCompetencias()
}
