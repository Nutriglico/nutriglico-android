package com.fiap.startupone.nutriglico.commons.di

import com.fiap.startupone.nutriglico.features.usermanagement.profile.data.ProfileRepository
import com.fiap.startupone.nutriglico.features.usermanagement.profile.data.repository.ProfileRepositoryImpl
import com.fiap.startupone.nutriglico.features.usermanagement.profile.domain.usecase.DeleteUserUseCase
import com.fiap.startupone.nutriglico.features.usermanagement.profile.domain.usecase.GetUserDetailsUseCase
import com.fiap.startupone.nutriglico.features.usermanagement.profile.domain.usecase.UpdateUserUseCase
import com.fiap.startupone.nutriglico.features.usermanagement.profile.ui.viewmodel.EditProfileViewModel
import com.fiap.startupone.nutriglico.features.usermanagement.profile.ui.viewmodel.ProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val profileModule = module {

    // Repositório
    single<ProfileRepository> { ProfileRepositoryImpl(get()) }

    // Casos de Uso
    factory { GetUserDetailsUseCase(get()) }
    factory { UpdateUserUseCase(get()) }
    factory { DeleteUserUseCase(get()) }

    // ViewModels
    viewModel { ProfileViewModel(get(), get()) }
    viewModel { EditProfileViewModel(get()) }
}