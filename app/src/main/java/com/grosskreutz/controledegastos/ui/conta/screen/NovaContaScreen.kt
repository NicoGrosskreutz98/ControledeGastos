package com.grosskreutz.controledegastos.ui.conta.screen

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.ExposedDropdownMenuDefaults.TrailingIcon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.grosskreutz.controledegastos.database.entity.CartaoEntity
import com.grosskreutz.controledegastos.ui.theme.ControleDeGastosTheme
import org.koin.core.component.getScopeName
import java.util.Calendar

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
    var parcelas by remember { mutableStateOf("1") }
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
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Default,
                keyboardType = KeyboardType.Decimal
            ),
            value = valor,
            onValueChange = { valor = it },
            label = { Text("Valor") }
        )

        OutlinedTextField(
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Default,
                keyboardType = KeyboardType.Number
            ),
            value = parcelas,
            onValueChange = { parcelas = it },
            label = { Text("Parcelas") }
        )

        MonthYearPicker { month, year ->
            competencia = String.format(
                "%02d/%d",
                month,
                year
            )
        }

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

@Composable
fun MonthYearPicker(
    initialMonth: Int = Calendar.getInstance().get(Calendar.MONTH) + 1,
    initialYear: Int = Calendar.getInstance().get(Calendar.YEAR),
    onSelected: (month: Int, year: Int) -> Unit
) {
    var month by remember { mutableStateOf(initialMonth) }
    var year by remember { mutableStateOf(initialYear) }
    var showDialog by remember { mutableStateOf(false) }

    var competencia by remember { mutableStateOf("") }

    OutlinedTextField(
        modifier = Modifier.clickable {
            showDialog = true
        },
        enabled = false,
        value = competencia,
        onValueChange = {

        },
        label = { Text("Primeira cobrança (MM/YYYY)") }
    )

    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                showDialog = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        competencia = String.format(
                            "%02d/%d",
                            month,
                            year
                        )
                        onSelected(month, year)
                        showDialog = false
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDialog = false
                    }
                ) {
                    Text("Cancelar")
                }
            },
            text = {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {

                    NumberPicker(
                        value = month,
                        range = 1..12,
                        onValueChange = {
                            month = it
                        },
                        formatter = {
                            String.format("%02d", it)
                        }
                    )

                    Spacer(
                        modifier = Modifier.width(32.dp)
                    )

                    NumberPicker(
                        value = year,
                        range = 2020..2030,
                        onValueChange = {
                            year = it
                        }
                    )
                }
            }
        )
    }
}

@Composable
fun NumberPicker(
    value: Int,
    range: IntRange,
    onValueChange: (Int) -> Unit,
    formatter: ((Int) -> String)? = null
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        IconButton(
            onClick = {
                if (value < range.last) {
                    onValueChange(value + 1)
                }
            }
        ) {
            Text("▲")
        }


        Text(
            text = formatter?.invoke(value)
                ?: value.toString(),
            fontSize = 22.sp
        )


        IconButton(
            onClick = {
                if (value > range.first) {
                    onValueChange(value - 1)
                }
            }
        ) {
            Text("▼")
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