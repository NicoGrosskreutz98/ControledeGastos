package com.grosskreutz.controledegastos.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object DateUtils {

    @RequiresApi(Build.VERSION_CODES.O)
    private val formatter =
        DateTimeFormatter.ofPattern("MM/yyyy")

    @RequiresApi(Build.VERSION_CODES.O)
    fun adicionarMeses(
        competencia: String,
        meses: Int
    ): String {

        val data = try {
            LocalDate.parse(
                "01/$competencia",
                DateTimeFormatter.ofPattern("dd/MM/yyyy")
            )
        } catch (e: Exception){
            LocalDate.parse(
                "01/$competencia",
                DateTimeFormatter.ofPattern("dd/MM/yy")
            )
        }

        return data
            .plusMonths(meses.toLong())
            .format(formatter)
    }
}