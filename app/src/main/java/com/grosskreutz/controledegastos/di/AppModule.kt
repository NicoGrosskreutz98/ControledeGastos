package com.grosskreutz.controledegastos.di

import androidx.room.Room
import com.grosskreutz.controledegastos.database.AppDatabase
import com.grosskreutz.controledegastos.database.datastore.UserPreferences
import com.grosskreutz.controledegastos.database.repository.CartaoRepository
import com.grosskreutz.controledegastos.database.repository.ContaRepository
import com.grosskreutz.controledegastos.database.repository.UsuarioRepository
import com.grosskreutz.controledegastos.ui.cartao.viewmodel.CartaoViewModel
import com.grosskreutz.controledegastos.ui.conta.viewmodel.ContaViewModel
import com.grosskreutz.controledegastos.ui.resumo.viewmodel.ResumoViewModel
import com.grosskreutz.controledegastos.ui.usuario.viewmodel.UsuarioViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            "controle_de_gastos.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    single {
        get<AppDatabase>().cartaoDao()
    }

    single {
        get<AppDatabase>().contaDao()
    }

    single {
        UserPreferences(get())
    }

    single {
        UsuarioRepository(get())
    }

    single {
        CartaoRepository(get())
    }

    single {
        ContaRepository(get())
    }

    viewModel {
        UsuarioViewModel(get())
    }

    viewModel {
        CartaoViewModel(get())
    }

    viewModel {
        ContaViewModel(get())
    }

    viewModel {
        ResumoViewModel(get())
    }
}