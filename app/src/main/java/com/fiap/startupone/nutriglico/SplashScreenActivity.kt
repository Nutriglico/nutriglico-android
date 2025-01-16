package com.fiap.startupone.nutriglico

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.fiap.startupone.nutriglico.features.usermanagement.auth.AuthActivity

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Instala a SplashScreen
        installSplashScreen()

        super.onCreate(savedInstanceState)

        // Inicia a próxima tela (Login)
        startActivity(Intent(this, AuthActivity::class.java))
        finish() // Finaliza a SplashScreen para não ficar no back stack
    }
}
