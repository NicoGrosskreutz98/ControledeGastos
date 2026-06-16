package com.grosskreutz.controledegastos.ui.cartao.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.grosskreutz.controledegastos.database.entity.CartaoEntity

@Composable
fun CadastroCartaoScreen(
    cartoes: List<CartaoEntity>,
    onSalvar: (CartaoEntity) -> Unit
) {

    var cartao by remember {
        mutableStateOf(CartaoEntity(0, ""))
    }

    var nome by remember {
        mutableStateOf(cartao.nome)
    }

    var nomeButton by remember {
        mutableStateOf("Salvar")
    }

    var visibilityButton by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier.run {
            fillMaxSize()
                .padding(16.dp)
        }
    ) {

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Cadastrar tipo cobrança",
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = nome,
            onValueChange = {
                nome = it
            },
            label = {
                Text("Nome da cobrança")
            }
        )

        Spacer(Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            Button(
                onClick = {

                    if (nome.isNotBlank()) {

                        cartao = CartaoEntity(cartao.id , nome)

                        onSalvar(cartao)

                        nome = ""
                    }
                }
            ) {
                Text(nomeButton)
            }

            if (visibilityButton)
                Button(
                    onClick = {

                        cartao = CartaoEntity(0, "")
                        nome = ""
                        visibilityButton = false
                        nomeButton = "Salvar"
                    }
                ) {
                    Text("Limpar")
                }
        }

        Spacer(Modifier.height(16.dp))

        LazyColumn {

            items(cartoes) {

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable(onClick = {
                            cartao = CartaoEntity(it.id, it.nome)
                            nome = it.nome
                            visibilityButton = true
                            nomeButton = "Atualizar"
                        })
                ) {

                    Text(
                        text = it.nome,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun CadastroCartaoScreenPrev() {
    CadastroCartaoScreen(
        cartoes = mutableListOf(
            CartaoEntity(0, "NuBank"),
            CartaoEntity(1, "Itaú")
        )
    ) { }
}