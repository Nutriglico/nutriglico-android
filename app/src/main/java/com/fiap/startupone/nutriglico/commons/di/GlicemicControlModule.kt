package com.fiap.startupone.nutriglico.commons.di

import com.fiap.startupone.nutriglico.features.glicemiccontrol.register.data.RegisterGlicemicControlRepositoryImpl
import com.fiap.startupone.nutriglico.features.glicemiccontrol.register.domain.usecase.SaveGlicemicControlMeasurementUseCase
import com.fiap.startupone.nutriglico.features.glicemiccontrol.register.repository.RegisterGlicemicControlRepository
import com.fiap.startupone.nutriglico.features.glicemiccontrol.register.viewmodel.RegisterGlicemicControlViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val glicemicControlModule = module {
    single<RegisterGlicemicControlRepository> { RegisterGlicemicControlRepositoryImpl(get()) }
    single { SaveGlicemicControlMeasurementUseCase(get()) }
    viewModel { RegisterGlicemicControlViewModel(get()) }
}