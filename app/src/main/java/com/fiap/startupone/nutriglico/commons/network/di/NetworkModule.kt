package com.fiap.startupone.nutriglico.commons.network.di

import com.fiap.startupone.nutriglico.commons.network.LoggingInterceptor
import com.fiap.startupone.nutriglico.features.glicemiccontrol.history.data.service.GlicemicHistoryService
import com.fiap.startupone.nutriglico.features.glicemiccontrol.register.data.service.RegisterGlicemicControlService
import com.fiap.startupone.nutriglico.features.usermanagement.profile.data.service.ProfileService
import com.fiap.startupone.nutriglico.features.usermanagement.signup.data.service.UserManagementService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import org.koin.dsl.module

// Constantes para as URLs base
private const val BASE_URL_GLICEMIC_CONTROL = "http://10.0.2.2:3000"
private const val BASE_URL_USER_MANAGEMENT = "https://10.0.2.2:5238/"

// Função genérica para criar Retrofit com uma base URL
private fun createRetrofit(baseUrl: String): Retrofit {
    val client = OkHttpClient.Builder()
        .addInterceptor(LoggingInterceptor())
        .build()
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

// Serviços para cada microserviço
private fun provideGlicemicControlService(): RegisterGlicemicControlService =
    createRetrofit(BASE_URL_GLICEMIC_CONTROL).create(RegisterGlicemicControlService::class.java)

private fun provideGlicemicHistoryService(): GlicemicHistoryService =
    createRetrofit(BASE_URL_GLICEMIC_CONTROL).create(GlicemicHistoryService::class.java)

private fun provideUserManagementService(): UserManagementService =
    createRetrofit(BASE_URL_USER_MANAGEMENT).create(UserManagementService::class.java)

private fun provideProfileService(): ProfileService =
    createRetrofit(BASE_URL_USER_MANAGEMENT).create(ProfileService::class.java)

// Koin module
val networkModule = module {
    single { provideGlicemicControlService() }
    single { provideGlicemicHistoryService() }
    single { provideUserManagementService() }
    single { provideProfileService() }
}
