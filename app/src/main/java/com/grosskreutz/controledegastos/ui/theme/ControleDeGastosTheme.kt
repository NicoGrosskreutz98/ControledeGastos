package com.grosskreutz.controledegastos.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val Scheme =
    lightColorScheme()

@Composable
fun ControleDeGastosTheme(
    content: @Composable () -> Unit
) {

    MaterialTheme(
        colorScheme = Scheme,
        content = content
    )
}