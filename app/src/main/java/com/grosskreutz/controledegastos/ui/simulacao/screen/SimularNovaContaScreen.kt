package com.grosskreutz.controledegastos.ui.simulacao.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.grosskreutz.controledegastos.database.entity.CartaoEntity

@Composable
fun SimularNovaContaScreen(
    cartoes: List<CartaoEntity>,
    onSimular: (
        descricao: String,
        valor: Double,
        parcelas: Int,
        competencia: String,
        cartaoId: Long
    ) -> Unit
) {

    var descricao by remember { mutableStateOf("") }
    var valor by remember { mutableStateOf("") }
    var parcelas by remember { mutableStateOf("") }
    var competencia by remember { mutableStateOf("") }

    var cartaoSelecionado by remember {
        mutableStateOf<CartaoEntity?>(null)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        OutlinedTextField(
            value = descricao,
            onValueChange = { descricao = it },
            label = { Text("Descrição") }
        )

        OutlinedTextField(
            value = valor,
            onValueChange = { valor = it },
            label = { Text("Valor") }
        )

        OutlinedTextField(
            value = parcelas,
            onValueChange = { parcelas = it },
            label = { Text("Parcelas") }
        )

        OutlinedTextField(
            value = competencia,
            onValueChange = { competencia = it },
            label = { Text("Primeira cobrança (MM/YYYY)") }
        )

        Spacer(Modifier.height(16.dp))

        cartoes.forEach { cartao ->

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    cartaoSelecionado = cartao
                }
            ) {
                Text(cartao.nome)
            }

            Spacer(Modifier.height(8.dp))
        }

        Spacer(Modifier.height(16.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {

                cartaoSelecionado?.let {

                    onSimular(
                        descricao,
                        valor.toDouble(),
                        parcelas.toInt(),
                        competencia,
                        it.id
                    )
                }
            }
        ) {
            Text("Salvar")
        }
    }
}