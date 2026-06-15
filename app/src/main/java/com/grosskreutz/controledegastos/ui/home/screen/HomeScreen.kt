package com.grosskreutz.controledegastos.ui.home.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.grosskreutz.controledegastos.ui.theme.ControleDeGastosTheme

@Composable
fun HomeScreen(
    nome: String,
    onCartao: () -> Unit,
    onNovaConta: () -> Unit,
    onResumo: () -> Unit,
    onSimulacao: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Olá, $nome",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )

        Spacer(Modifier.height(24.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ){
            Box( modifier = Modifier
                .background(color = Color(0xFF00D556))
                .padding(2.dp)
                .clickable {
                    onCartao()
                }
                .size(100.dp)
                .shadow(4.dp, RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    text = "Cadastrar tipo cobrança",
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

            }

            Spacer(Modifier.width(12.dp))

            Box( modifier = Modifier
                .background(color = Color(0xFF00D556))
                .padding(2.dp)
                .clickable {
                    onResumo()
                }
                .size(100.dp)
                .shadow(4.dp, RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    text = "Consultar gastos",
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

            }
        }

        Spacer(Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ){
            Box( modifier = Modifier
                .background(color = Color(0xFF00D556))
                .padding(2.dp)
                .clickable {
                    onNovaConta()
                }
                .size(100.dp)
                .shadow(4.dp, RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    text = "Nova conta",
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

            }

            Spacer(Modifier.width(12.dp))

            Box( modifier = Modifier
                .background(color = Color(0xFF00D556))
                .padding(2.dp)
                .clickable {
                    onSimulacao()
                }
                .size(100.dp)
                .shadow(4.dp, RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    text = "Simular compra",
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

            }
        }
    }
}

@Preview
@Composable
private fun HomeScreenPrev() {
    ControleDeGastosTheme {
        HomeScreen("Nícolas",
            {

            },
            {

            },
            {

            },
            {

            }
        )
    }
}