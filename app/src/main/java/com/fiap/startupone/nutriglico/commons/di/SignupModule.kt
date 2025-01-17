package com.fiap.startupone.nutriglico.commons.di

import com.fiap.startupone.nutriglico.features.usermanagement.signup.data.UserSignUpRepositoryImpl
import com.fiap.startupone.nutriglico.features.usermanagement.signup.domain.usecase.CreateUserUseCase
import com.fiap.startupone.nutriglico.features.usermanagement.signup.data.repository.UserSignUpRepository
import com.fiap.startupone.nutriglico.features.usermanagement.signup.ui.viewmodel.UserSignUpViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val signupModule = module {
    // Repositório de cadastro
    single<UserSignUpRepository> { UserSignUpRepositoryImpl() }

    // Caso de uso para criação de usuários
    single { CreateUserUseCase(get()) }

    // ViewModel para gerenciamento da tela de cadastro
    viewModel { UserSignUpViewModel(get()) }
}