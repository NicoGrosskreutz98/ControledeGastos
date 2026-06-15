package com.grosskreutz.controledegastos.ui.conta.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.ExposedDropdownMenuDefaults.TrailingIcon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.grosskreutz.controledegastos.database.entity.CartaoEntity
import com.grosskreutz.controledegastos.ui.theme.ControleDeGastosTheme
import org.koin.core.component.getScopeName

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NovaContaScreen(
    cartoes: List<CartaoEntity>,
    onSalvar: (
        descricao: String,
        valor: Double?,
        parcelas: Int,
        competencia: String,
        cartao: CartaoEntity?
    ) -> Unit,
    onSimular: (
        descricao: String,
        valor: Double?,
        parcelas: Int,
        competencia: String,
        cartao: CartaoEntity?
    ) -> Unit,
    onCadastrarCartao : () -> Unit
) {

    var descricao by remember { mutableStateOf("") }
    var valor by remember { mutableStateOf("") }
    var parcelas by remember { mutableStateOf("") }
    var competencia by remember { mutableStateOf("") }

    var cartaoSelecionado by remember {
        mutableStateOf<CartaoEntity?>(null)
    }


    val context = LocalContext.current
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Nova Conta",
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(16.dp))

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


        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            SpinnerCartao(
                modifier = Modifier
                    .weight(1f)
                    .width(200.dp),
                cartoes = cartoes,
                cartaoSelecionado = cartaoSelecionado,
                onSelecionar = {
                    cartaoSelecionado = it

                }
            )

            Button(
                modifier = Modifier.width(150.dp),
                onClick = {
                    onCadastrarCartao()
                }
            ) {
                Text("Novo Cartão")
            }
        }


        Spacer(Modifier.height(16.dp))

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                modifier = Modifier.width(150.dp),
                onClick = {

                    val valorDouble =
                        valor.toDoubleOrNull()

                    val parcelasInt =
                        parcelas.toIntOrNull()?: 1

                    onSalvar(
                        descricao,
                        valorDouble,
                        parcelasInt,
                        competencia,
                        cartaoSelecionado
                    )
                }
            ) {
                Text("Salvar")
            }
            Button(
                modifier = Modifier.width(150.dp),
                onClick = {

                    val valorDouble =
                        valor.toDoubleOrNull()

                    val parcelasInt =
                        parcelas.toIntOrNull()?: 1


                    onSimular(
                        descricao,
                        valorDouble,
                        parcelasInt,
                        competencia,
                        cartaoSelecionado
                    )
                }
            ) {
                Text("Simular")
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpinnerCartao(
    modifier: Modifier = Modifier,
    cartoes: List<CartaoEntity>,
    cartaoSelecionado: CartaoEntity?,
    onSelecionar: (CartaoEntity) -> Unit
) {

    var expandido by remember {
        mutableStateOf(false)
    }

    ExposedDropdownMenuBox(
        expanded = expandido,
        onExpandedChange = {
            expandido = !expandido
        }
    ) {

        OutlinedTextField(
            value = cartaoSelecionado?.nome.takeIf { cartaoSelecionado != null }  ?: "",
            onValueChange = {},
            readOnly = true,
            label = {
                Text("Cartão")
            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expandido
                )
            },
            modifier = modifier
                .menuAnchor()
        )


        ExposedDropdownMenu(
            expanded = expandido,
            onDismissRequest = {
                expandido = false
            }
        ) {

            cartoes.forEach { cartao ->

                DropdownMenuItem(
                    text = {
                        Text(cartao.nome)
                    },
                    onClick = {

                        onSelecionar(cartao)

                        expandido = false
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun NovaContaScreenPrev() {

    val cartoes = mutableListOf(
        CartaoEntity(0,"Nubank"),
        CartaoEntity(1,"Itaú")
    )

    NovaContaScreen(cartoes = cartoes,
        onSalvar = {
                descricao,
                valor,
                parcelas,
                competencia,
                cartao ->
        },
        onSimular = {
                descricao,
                valor,
                parcelas,
                competencia,
                cartao ->
        },
        onCadastrarCartao = {

        }
    )
}