package com.fiap.startupone.nutriglico

import android.app.Application
import com.fiap.startupone.nutriglico.commons.di.appModule
import org.koin.core.context.startKoin

class NutriGlicoApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Init koin
        startKoin {
            modules(appModule)
        }
    }

}