package com.grosskreutz.controledegastos.ui.resumo.screen

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.math.MathUtils
import com.grosskreutz.controledegastos.database.model.ResumoConta
import com.grosskreutz.controledegastos.ui.resumo.model.ResumoItem
import com.grosskreutz.controledegastos.ui.resumo.state.ResumoState
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ResumoScreen(
    meses: List<String> = mutableListOf(),
    itens: Map<String, List<ResumoConta>>,
    total: Double = 0.0,
    onMesSelecionado: (String) -> Unit
) {
    val mesAtual = SimpleDateFormat(
        "MM/yyyy",
        Locale.getDefault()
    ).format(Date())

    var mesSelecionado by remember {
        mutableStateOf(mesAtual)
    }

    val indexMesAtual = meses.indexOf(mesAtual)

    val listState = rememberLazyListState()

    LaunchedEffect(Unit) {
        if (indexMesAtual >= 0) {
            listState.scrollToItem(indexMesAtual)
        }
    }

    Box(
        Modifier.fillMaxSize()
    ){
        Column(
            Modifier
                .padding(5.dp)
        ) {
            LazyRow(
                state = listState,
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {

                items(meses) { mes ->

                    val selecionado = mes == mesSelecionado

                    Box(
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(
                                if (selecionado)
                                    Color(0xFF00D556)
                                else
                                    Color.LightGray
                            )
                            .clickable {

                                mesSelecionado = mes

                                // recarrega dados do banco
                                onMesSelecionado(mes)
                            }
                            .padding(
                                horizontal = 20.dp,
                                vertical = 12.dp
                            ),
                        contentAlignment = Alignment.Center
                    ) {

                        Text(
                            text = mes,
                            color = if (selecionado)
                                Color.White
                            else
                                Color.Black,
                            fontSize = 16.sp
                        )
                    }
                }
            }

            Spacer(Modifier.height(6.dp))

            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                itens.forEach {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    ) {

                        Text(
                            modifier = Modifier
                                .fillMaxWidth(),
                            text = it.key,
                            textAlign = TextAlign.Center
                        )

                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {

                            items(it.value) { r->

                                Row(modifier = Modifier.padding(5.dp)) {

                                    Text(
                                        text = r.descricao,
                                        modifier = Modifier.weight(1f)
                                    )

                                    val arredondado = BigDecimal(r.valorParcela)
                                        .setScale(2, RoundingMode.HALF_UP)
                                        .toDouble()

                                    Text(
                                        text = "R$ $arredondado"
                                    )
                                }
                            }
                        }

                        Spacer(Modifier.height(5.dp))
                        Row(modifier = Modifier
                            .padding(5.dp)
                            .fillMaxWidth(),
                            horizontalArrangement = Arrangement.End) {

                            Text(
                                text = "Total:  ",
                            )

                            val arredondado = BigDecimal(it.value.map { v -> v.valorParcela }.sum())
                                .setScale(2, RoundingMode.HALF_UP)
                                .toDouble()

                            Text(
                                text = "R$ $arredondado"
                            )
                        }
                    }
                }
            }
        }

        val arredondado = BigDecimal(total)
            .setScale(2, RoundingMode.HALF_UP)
            .toDouble()

        Text(
            text = "TOTAL: R$ $arredondado",
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomEnd),
            style = MaterialTheme.typography.headlineSmall,
        )
    }
}

@Preview
@Composable
private fun ResumoScrennPrev() {
    val list = mapOf<String, List<ResumoConta>>(
        "Nubank" to mutableListOf(
            ResumoConta(
                "Marmita",
                20.0,
                "06/2026",
                "Nubank"
            ),
            ResumoConta(
                "Gasolina",
                100.0,
                "06/2026",
                "Nubank"
            )
        ),
        "Itaú" to mutableListOf(
            ResumoConta(
                "Marmita",
                20.0,
                "06/2026",
                "Nubank"
            ),
            ResumoConta(
                "Gasolina",
                100.0,
                "06/2026",
                "Nubank"
            )
        )
    )
    val listMeses = mutableListOf<String>()


    listMeses.addAll(
        mutableListOf(
        "02/2026", "03/2026", "04/2026", "05/2026", "06/2026", "07/2026", "08/2026")
    )

    ResumoScreen(listMeses, list, 200.0, {

    })
}