package com.fiap.startupone.nutriglico

import android.app.Application
import com.fiap.startupone.nutriglico.commons.di.authModule
import com.fiap.startupone.nutriglico.commons.di.glicemicControlModule
import com.fiap.startupone.nutriglico.commons.di.homeModule
import com.fiap.startupone.nutriglico.commons.di.networkModule
import com.fiap.startupone.nutriglico.commons.di.signupModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class NutriGlicoApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        val appModules = listOf(
            networkModule,
            authModule,
            homeModule,
            signupModule,
            glicemicControlModule
        )

        // Init koin
        startKoin {
            androidContext(this@NutriGlicoApplication)
            modules(appModules)
        }
    }

}