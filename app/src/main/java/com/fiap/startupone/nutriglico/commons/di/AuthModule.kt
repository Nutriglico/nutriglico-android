package com.fiap.startupone.nutriglico.commons.di

import com.fiap.startupone.nutriglico.features.usermanagement.auth.data.AuthRepositoryImpl
import com.fiap.startupone.nutriglico.features.usermanagement.auth.domain.usecase.AuthUseCase
import com.fiap.startupone.nutriglico.features.usermanagement.auth.repository.AuthRepository
import com.fiap.startupone.nutriglico.features.usermanagement.auth.viewmodel.AuthViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val authModule = module {
    // Repositório de autenticação
    single<AuthRepository> { AuthRepositoryImpl() }

    // Caso de uso para autenticação
    single { AuthUseCase(get()) }

    // ViewModel para gerenciamento da tela de autenticação
    viewModel { AuthViewModel(get()) }
}