package com.fiap.startupone.nutriglico.commons.di

import com.fiap.startupone.nutriglico.features.glucose.register.domain.usecase.SaveGlucoseMeasurementUseCase
import com.fiap.startupone.nutriglico.features.glucose.register.viewmodel.RegisterGlucoseViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { SaveGlucoseMeasurementUseCase() }
    viewModel { RegisterGlucoseViewModel(get()) }
}
