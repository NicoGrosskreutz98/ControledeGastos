package com.grosskreutz.controledegastos.ui.navigation.graph

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.grosskreutz.controledegastos.database.entity.ContaEntity
import com.grosskreutz.controledegastos.ui.navigation.Destinations
import com.grosskreutz.controledegastos.ui.resumo.model.ResumoItem
import com.grosskreutz.controledegastos.ui.resumo.screen.ResumoScreen
import com.grosskreutz.controledegastos.ui.resumo.viewmodel.ResumoViewModel
import org.koin.androidx.compose.koinViewModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

fun NavGraphBuilder.resumoGraph(
    navController: NavController
) {

    composable(
        route = Destinations.RESUMO
    ) {

        val viewModel: ResumoViewModel =
            koinViewModel()

        var cartaoName = remember { "" }

        LaunchedEffect(Unit) {

            val mesAtual = SimpleDateFormat(
                "MM/yyyy",
                Locale.getDefault()
            ).format(Date())


            val objeto = navController
                .previousBackStackEntry
                ?.savedStateHandle
                ?.get<List<ContaEntity>>(
                    "simulacao"
                ) ?: emptyList()

            cartaoName = navController
                .previousBackStackEntry
                ?.savedStateHandle
                ?.get<String>(
                    "cartaoName"
                ) ?: ""

            viewModel.init(
                objeto,
                mesAtual,
                cartaoName
            )
        }

        val state by viewModel
            .state
            .collectAsState()

        val agrupado = state.contas.groupBy { it.cartaoNome }

        val total =
            state.contas.map{
                it.valorParcela
            }.sum()

        val competenciasOrdenadas = state.competencias.sortedBy {

            LocalDate.parse(
                "01/$it",
                DateTimeFormatter.ofPattern("dd/MM/yyyy")
            )
        }

        ResumoScreen(
            meses = competenciasOrdenadas,
            itens = agrupado,
            total = total,
            onMesSelecionado = {
                viewModel.carregarWithScope(
                    it,
                    state.cartaoNameSimulacao
                )
            }
        )
    }
}