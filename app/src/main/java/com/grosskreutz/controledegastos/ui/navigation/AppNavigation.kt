package com.grosskreutz.controledegastos.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.grosskreutz.controledegastos.ui.navigation.graph.cartaoGraph
import com.grosskreutz.controledegastos.ui.navigation.graph.homeGraph
import com.grosskreutz.controledegastos.ui.navigation.graph.novaContaGraph
import com.grosskreutz.controledegastos.ui.navigation.graph.resumoGraph
import com.grosskreutz.controledegastos.ui.navigation.graph.simulacaoGraph
import com.grosskreutz.controledegastos.ui.navigation.graph.splashGraph
import com.grosskreutz.controledegastos.ui.navigation.graph.usuarioGraph
import com.grosskreutz.controledegastos.ui.usuario.viewmodel.UsuarioViewModel
import org.koin.androidx.compose.koinViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    val usuarioViewModel: UsuarioViewModel =
        koinViewModel()

    val usuarioState by usuarioViewModel
        .state
        .collectAsState()

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val rotaAtual = navBackStackEntry?.destination?.route


    val telasComMenu = listOf(
        Destinations.HOME,
        Destinations.CARTAO,
        Destinations.NOVA_CONTA,
        Destinations.SIMULACAO,
        Destinations.RESUMO
    )

    val mostrarMenu = rotaAtual in telasComMenu


    Scaffold(

        bottomBar = {

            if (mostrarMenu) {

                NavigationBar {

                    NavigationBarItem(
                        selected = rotaAtual == Destinations.RESUMO,
                        onClick = {
                            if (usuarioState.nome.isNotEmpty()) {
                                navController.navigate(Destinations.RESUMO) {

                                    popUpTo(navController.currentBackStackEntry!!.destination.route!!) {
                                        inclusive = true
                                    }

                                    launchSingleTop = true
                                }
                            }
                        },
                        icon = {
                            Icon(
                                Icons.AutoMirrored.Filled.List,
                                null
                            )
                        },
                        label = {
                            Text("Resumo")
                        }
                    )

                    NavigationBarItem(
                        selected = rotaAtual == Destinations.NOVA_CONTA,
                        onClick = {
                            if (usuarioState.nome.isNotEmpty()) {
                                navController.navigate(Destinations.NOVA_CONTA) {

                                    popUpTo(navController.currentBackStackEntry!!.destination.route!!) {
                                        inclusive = true
                                    }

                                    launchSingleTop = true
                                }
                            }
                        },
                        icon = {
                            Icon(
                                Icons.Default.Add,
                                null
                            )
                        },
                        label = {
                            Text("Nova conta")
                        }
                    )

                    
                }
            }
        }

    ) { padding ->


        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {


            NavHost(
                navController = navController,
                startDestination = Destinations.SPLASH
            ) {


                splashGraph(
                    navController,
                    usuarioViewModel
                )


                usuarioGraph(
                    navController,
                    usuarioViewModel
                )


                homeGraph(
                    navController,
                    usuarioViewModel
                )


                cartaoGraph()


                novaContaGraph(navController)


                simulacaoGraph(navController)


                resumoGraph(navController)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
private fun AppNavigationPrev() {
    AppNavigation()
}