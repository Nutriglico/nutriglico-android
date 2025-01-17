package com.fiap.startupone.nutriglico.commons.di

import com.fiap.startupone.nutriglico.features.usermanagement.signup.data.UserManagementRepositoryImpl
import com.fiap.startupone.nutriglico.features.usermanagement.signup.domain.usecase.RegisterNutritionistUseCase
import com.fiap.startupone.nutriglico.features.usermanagement.signup.domain.usecase.RegisterPatientUseCase
import com.fiap.startupone.nutriglico.features.usermanagement.signup.data.repository.UserManagementRepository
import com.fiap.startupone.nutriglico.features.usermanagement.signup.ui.viewmodel.UserManagementViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val userManagementModule = module {

    // Repositório
    single<UserManagementRepository> { UserManagementRepositoryImpl(get()) }

    // Casos de Uso
    factory { RegisterPatientUseCase(get()) }
    factory { RegisterNutritionistUseCase(get()) }

    // ViewModel
    viewModel {
        UserManagementViewModel(
            registerPatientUseCase = get(),
            registerNutritionistUseCase = get()
        )
    }
}