package com.fiap.startupone.nutriglico.commons.di

import com.fiap.startupone.nutriglico.features.glucose.register.domain.usecase.SaveGlucoseMeasurementUseCase
import com.fiap.startupone.nutriglico.features.glucose.register.viewmodel.RegisterGlucoseViewModel
import com.fiap.startupone.nutriglico.features.signup.domain.usecase.CreateUserUseCase
import com.fiap.startupone.nutriglico.features.signup.repository.SignupRepository
import com.fiap.startupone.nutriglico.features.signup.repository.SignupRepositoryImpl
import com.fiap.startupone.nutriglico.features.signup.viewmodel.SignupViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { SaveGlucoseMeasurementUseCase() }
    viewModel { RegisterGlucoseViewModel(get()) }

    // Repositório
    single<SignupRepository> { SignupRepositoryImpl() }

    // Caso de uso
    single { CreateUserUseCase(get()) }

    // ViewModel
    viewModel { SignupViewModel(get()) }
}
