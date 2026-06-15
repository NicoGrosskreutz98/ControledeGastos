package com.grosskreutz.controledegastos.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.grosskreutz.controledegastos.database.entity.ContaEntity
import com.grosskreutz.controledegastos.database.model.ResumoConta
import kotlinx.coroutines.flow.Flow

@Dao
interface ContaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserir(conta: ContaEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserirTodas(contas: List<ContaEntity>)

    @Query(
        """
        SELECT
            c.descricao,
            c.valorParcela,
            c.competencia,
            t.nome as cartaoNome
        FROM contas c
        INNER JOIN cartoes t
            ON t.id = c.cartaoId
        WHERE competencia = :competencia
        ORDER BY t.nome
        """
    )
    fun getResumoMes(
        competencia: String
    ): Flow<List<ResumoConta>>

    @Query(
        """
        SELECT DISTINCT
        competencia
        FROM contas c
        INNER JOIN cartoes t
            ON t.id = c.cartaoId
        ORDER BY competencia
        """
    )
    fun getCompetencias(
    ): Flow<List<String>>
}