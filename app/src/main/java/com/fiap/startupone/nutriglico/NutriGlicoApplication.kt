package com.fiap.startupone.nutriglico

import android.app.Application
import com.fiap.startupone.nutriglico.commons.di.userAuthModule
import com.fiap.startupone.nutriglico.commons.di.glicemicControlModule
import com.fiap.startupone.nutriglico.commons.di.homeModule
import com.fiap.startupone.nutriglico.commons.di.networkModule
import com.fiap.startupone.nutriglico.commons.di.signupModule
import com.fiap.startupone.nutriglico.commons.di.userManagementModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class NutriGlicoApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        val appModules = listOf(
            networkModule,
            userAuthModule,
            userManagementModule,
            signupModule,
            homeModule,
            glicemicControlModule
        )

        // Init koin
        startKoin {
            androidContext(this@NutriGlicoApplication)
            modules(appModules)
            printLogger(level = Level.ERROR)
        }
    }

}