package com.grosskreutz.controledegastos.ui.navigation.graph

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.grosskreutz.controledegastos.ui.home.screen.HomeScreen
import com.grosskreutz.controledegastos.ui.navigation.Destinations
import com.grosskreutz.controledegastos.ui.usuario.viewmodel.UsuarioViewModel

fun NavGraphBuilder.homeGraph(
    navController: NavController,
    usuarioViewModel: UsuarioViewModel
) {

    composable(
        route = Destinations.HOME
    ) {

        val state by usuarioViewModel
            .state
            .collectAsState()

        HomeScreen(

            nome = state.nome,

            onCartao = {

                navController.navigate(
                    Destinations.CARTAO
                )
            },

            onNovaConta = {

                navController.navigate(
                    Destinations.NOVA_CONTA
                )
            },

            onResumo = {

                navController.navigate(
                    Destinations.RESUMO
                )
            },

            onSimulacao = {

                navController.navigate(
                    Destinations.SIMULACAO
                )
            }
        )
    }
}