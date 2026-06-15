package com.grosskreutz.controledegastos.ui.navigation.graph

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.grosskreutz.controledegastos.ui.navigation.Destinations
import com.grosskreutz.controledegastos.ui.spash.screen.SplashScreen
import com.grosskreutz.controledegastos.ui.usuario.viewmodel.UsuarioViewModel

fun NavGraphBuilder.splashGraph(
    navController: NavController,
    usuarioViewModel: UsuarioViewModel,
) {

    composable(
        Destinations.SPLASH
    ) {

        val state by usuarioViewModel
            .state
            .collectAsState()

        LaunchedEffect(state.carregando) {

            if (!state.carregando) {

                navController.navigate(

                    if (state.nome.isBlank())
                        Destinations.USER
                    else
                        Destinations.RESUMO

                ) {

                    popUpTo(
                        Destinations.SPLASH
                    ) {
                        inclusive = true
                    }
                }
            }
        }

        SplashScreen()
    }
}