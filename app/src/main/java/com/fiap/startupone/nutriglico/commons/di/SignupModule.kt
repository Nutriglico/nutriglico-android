package com.fiap.startupone.nutriglico.commons.di

import com.fiap.startupone.nutriglico.features.usermanagement.signup.data.SignupRepositoryImpl
import com.fiap.startupone.nutriglico.features.usermanagement.signup.domain.usecase.CreateUserUseCase
import com.fiap.startupone.nutriglico.features.usermanagement.signup.repository.SignupRepository
import com.fiap.startupone.nutriglico.features.usermanagement.signup.viewmodel.SignupViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val signupModule = module {
    // Repositório de cadastro
    single<SignupRepository> { SignupRepositoryImpl() }

    // Caso de uso para criação de usuários
    single { CreateUserUseCase(get()) }

    // ViewModel para gerenciamento da tela de cadastro
    viewModel { SignupViewModel(get()) }
}