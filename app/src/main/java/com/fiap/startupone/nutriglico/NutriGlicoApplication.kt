package com.fiap.startupone.nutriglico

import android.app.Application
import com.fiap.startupone.nutriglico.commons.di.appModules
import org.koin.core.context.startKoin

class NutriGlicoApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Init koin
        startKoin {
            modules(appModules)
        }
    }

}