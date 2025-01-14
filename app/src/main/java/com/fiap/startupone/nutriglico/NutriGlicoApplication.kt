package com.fiap.startupone.nutriglico

import android.app.Application
import com.fiap.startupone.nutriglico.commons.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class NutriGlicoApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Init koin
        startKoin {
            androidContext(this@NutriGlicoApplication)
            modules(appModules)
        }
    }

}