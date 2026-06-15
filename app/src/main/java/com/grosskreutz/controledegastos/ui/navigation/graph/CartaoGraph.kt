package com.grosskreutz.controledegastos.ui.navigation.graph

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.grosskreutz.controledegastos.ui.cartao.screen.CadastroCartaoScreen
import com.grosskreutz.controledegastos.ui.cartao.viewmodel.CartaoViewModel
import com.grosskreutz.controledegastos.ui.navigation.Destinations
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.cartaoGraph() {

    composable(
        route = Destinations.CARTAO
    ) {

        val viewModel: CartaoViewModel =
            koinViewModel()

        val state by viewModel
            .state
            .collectAsState()

        CadastroCartaoScreen(

            cartoes = state.cartoes,

            onSalvar = {

                viewModel.salvar(it)
            }
        )
    }
}