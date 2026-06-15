package com.grosskreutz.controledegastos.ui.navigation.graph

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.grosskreutz.controledegastos.ui.cartao.viewmodel.CartaoViewModel
import com.grosskreutz.controledegastos.ui.conta.screen.NovaContaScreen
import com.grosskreutz.controledegastos.ui.conta.viewmodel.ContaViewModel
import com.grosskreutz.controledegastos.ui.navigation.Destinations
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.novaContaGraph(
    navController: NavController
) {

    composable(
        route = Destinations.NOVA_CONTA
    ) {

        val cartaoViewModel: CartaoViewModel =
            koinViewModel()

        val contaViewModel: ContaViewModel =
            koinViewModel()

        val cartoes by cartaoViewModel
            .state
            .collectAsState()

        val context = LocalContext.current

        NovaContaScreen(

            cartoes = cartoes.cartoes,

            onSalvar = { descricao,
                         valor,
                         parcelas,
                         competencia,
                         cartao ->

                if (descricao.isEmpty())
                    Toast.makeText(context, "Favor digitar uma descrição", Toast.LENGTH_LONG).show()

                if (valor == null)
                    Toast.makeText(context, "Favor digitar um valor valido", Toast.LENGTH_LONG).show()

                else if (cartao == null)
                    Toast.makeText(context, "Favor selecionar o tipo da conta/cartão", Toast.LENGTH_LONG).show()

                else if (!contaViewModel.isDataValida(competencia))
                    Toast.makeText(context, "Favor digitar uma data de cobrança válida (MM/YYYY)", Toast.LENGTH_LONG).show()

                else {
                    contaViewModel.salvarConta(
                        descricao,
                        valor,
                        parcelas,
                        competencia,
                        cartao.id
                    )

                    navController.navigate(Destinations.RESUMO) {

                        popUpTo(Destinations.NOVA_CONTA) {
                            inclusive = true
                        }

                        launchSingleTop = true
                    }
                }
            },
            onSimular = { descricao,
                         valor,
                         parcelas,
                         competencia,
                         cartao ->

                if (descricao.isEmpty())
                    Toast.makeText(context, "Favor digitar uma descrição", Toast.LENGTH_LONG).show()

                if (valor == null)
                    Toast.makeText(context, "Favor digitar um valor válido", Toast.LENGTH_LONG).show()

                else if (cartao == null)
                    Toast.makeText(context, "Favor selecionar o tipo da conta/cartão", Toast.LENGTH_LONG).show()

                else if (!contaViewModel.isDataValida(competencia))
                    Toast.makeText(context, "Favor digitar uma data de cobrança válida (MM/YYYY)", Toast.LENGTH_LONG).show()

                else {

                    contaViewModel.viewModelScope.launch {
                        val simulacao = contaViewModel.getSimulacao(
                            descricao,
                            valor,
                            parcelas,
                            competencia,
                            cartao.id
                        )

                        navController.currentBackStackEntry
                            ?.savedStateHandle
                            ?.set(
                                "simulacao",
                                simulacao
                            )
                        navController.currentBackStackEntry
                            ?.savedStateHandle
                            ?.set(
                                "cartaoName",
                                cartao.nome
                            )

                        navController.navigate(Destinations.RESUMO)
                    }
                }

            }, onCadastrarCartao = {
                navController.navigate(Destinations.CARTAO)
            }
        )
    }
}