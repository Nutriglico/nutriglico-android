package com.fiap.startupone.nutriglico.commons.di

import com.fiap.startupone.nutriglico.features.usermanagement.auth.data.UserAuthRepositoryImpl
import com.fiap.startupone.nutriglico.features.usermanagement.auth.domain.usecase.UserAuthUseCase
import com.fiap.startupone.nutriglico.features.usermanagement.auth.repository.UserAuthRepository
import com.fiap.startupone.nutriglico.features.usermanagement.auth.viewmodel.UserAuthViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val userAuthModule = module {
    single<UserAuthRepository> { UserAuthRepositoryImpl() }
    single { UserAuthUseCase(get()) }
    viewModel { UserAuthViewModel(get()) }
}