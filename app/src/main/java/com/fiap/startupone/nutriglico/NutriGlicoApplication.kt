package com.fiap.startupone.nutriglico

import android.app.Application
import com.fiap.startupone.nutriglico.commons.network.di.networkModule
import com.fiap.startupone.nutriglico.features.glicemiccontrol.di.glicemicControlModule
import com.fiap.startupone.nutriglico.features.home.di.homeModule
import com.fiap.startupone.nutriglico.features.usermanagement.auth.di.userAuthModule
import com.fiap.startupone.nutriglico.features.usermanagement.profile.di.profileModule
import com.fiap.startupone.nutriglico.features.usermanagement.signup.di.userManagementModule
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
            homeModule,
            glicemicControlModule,
            profileModule
        )

        // Init koin
        startKoin {
            androidContext(this@NutriGlicoApplication)
            modules(appModules)
            printLogger(level = Level.ERROR)
        }
    }

}