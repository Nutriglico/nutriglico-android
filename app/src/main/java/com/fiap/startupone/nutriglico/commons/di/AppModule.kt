package com.fiap.startupone.nutriglico.commons.di

import com.fiap.startupone.nutriglico.features.auth.data.AuthRepositoryImpl
import com.fiap.startupone.nutriglico.features.auth.domain.usecase.AuthUseCase
import com.fiap.startupone.nutriglico.features.auth.repository.AuthRepository
import com.fiap.startupone.nutriglico.features.auth.viewmodel.AuthViewModel
import com.fiap.startupone.nutriglico.features.glucose.register.domain.usecase.SaveGlucoseMeasurementUseCase
import com.fiap.startupone.nutriglico.features.glucose.register.viewmodel.RegisterGlucoseViewModel
import com.fiap.startupone.nutriglico.features.home.viewmodel.HomeViewModel
import com.fiap.startupone.nutriglico.features.signup.data.SignupRepositoryImpl
import com.fiap.startupone.nutriglico.features.signup.domain.usecase.CreateUserUseCase
import com.fiap.startupone.nutriglico.features.signup.repository.SignupRepository
import com.fiap.startupone.nutriglico.features.signup.viewmodel.SignupViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModules = listOf(
    authModule(),
    homeModule(),
    signupModule(),
    glucoseModule()
)

fun authModule() = module {
    // Repositório de autenticação
    single<AuthRepository> { AuthRepositoryImpl() }

    // Caso de uso para autenticação
    single { AuthUseCase(get()) }

    // ViewModel para gerenciamento da tela de autenticação
    viewModel { AuthViewModel(get()) }
}

fun homeModule() = module {
    // ViewModel para a tela Home
    viewModel { HomeViewModel() }
}

fun signupModule() = module {
    // Repositório de cadastro
    single<SignupRepository> { SignupRepositoryImpl() }

    // Caso de uso para criação de usuários
    single { CreateUserUseCase(get()) }

    // ViewModel para gerenciamento da tela de cadastro
    viewModel { SignupViewModel(get()) }
}

fun glucoseModule() = module {
    // Caso de uso para salvar medições de glicose
    single { SaveGlucoseMeasurementUseCase() }

    // ViewModel para gerenciamento da tela de registro de glicose
    viewModel { RegisterGlucoseViewModel(get()) }
}