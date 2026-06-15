package com.grosskreutz.controledegastos

import android.app.Application
import com.grosskreutz.controledegastos.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class ControleDeGastosApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@ControleDeGastosApplication)
            modules(appModule)
        }
    }
}