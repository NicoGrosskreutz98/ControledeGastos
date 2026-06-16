package com.grosskreutz.controledegastos.ui.navigation.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.grosskreutz.controledegastos.ui.navigation.Destinations
import com.grosskreutz.controledegastos.ui.usuario.screen.UsuarioScreen
import com.grosskreutz.controledegastos.ui.usuario.viewmodel.UsuarioViewModel

fun NavGraphBuilder.usuarioGraph(
    navController: NavController,
    viewModel: UsuarioViewModel
) {

    composable(
        route = Destinations.USER
    ) {

        UsuarioScreen(

            onSalvar = { nome ->

                viewModel.salvarNome(nome)

                navController.navigate(
                    Destinations.RESUMO
                ) {

                    popUpTo(
                        Destinations.USER
                    ) {
                        inclusive = true
                    }
                }
            }
        )
    }
}