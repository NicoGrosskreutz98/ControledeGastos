package com.grosskreutz.controledegastos.ui.navigation.graph

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.grosskreutz.controledegastos.ui.cartao.viewmodel.CartaoViewModel
import com.grosskreutz.controledegastos.ui.conta.screen.NovaContaScreen
import com.grosskreutz.controledegastos.ui.conta.viewmodel.ContaViewModel
import com.grosskreutz.controledegastos.ui.navigation.Destinations
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.simulacaoGraph(
    navController: NavController
) {

    composable(
        route = Destinations.SIMULACAO
    ) {

        val cartaoViewModel: CartaoViewModel =
            koinViewModel()

        val cartoes by cartaoViewModel
            .state
            .collectAsState()

        val contaViewModel: ContaViewModel =
            koinViewModel()
    }
}