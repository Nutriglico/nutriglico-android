package com.fiap.startupone.nutriglico.features.glicemiccontrol.di

import com.fiap.startupone.nutriglico.features.glicemiccontrol.history.data.repository.GlicemicHistoryRepositoryImpl
import com.fiap.startupone.nutriglico.features.glicemiccontrol.history.domain.usecase.DeleteGlicemicRecordUseCase
import com.fiap.startupone.nutriglico.features.glicemiccontrol.history.domain.usecase.FetchGlicemicDetailsUseCase
import com.fiap.startupone.nutriglico.features.glicemiccontrol.history.domain.usecase.FetchGlicemicHistoryUseCase
import com.fiap.startupone.nutriglico.features.glicemiccontrol.history.domain.usecase.UpdateGlicemicRecordUseCase
import com.fiap.startupone.nutriglico.features.glicemiccontrol.history.data.GlicemicHistoryRepository
import com.fiap.startupone.nutriglico.features.glicemiccontrol.history.ui.viewmodel.GlicemicHistoryViewModel
import com.fiap.startupone.nutriglico.features.glicemiccontrol.history.ui.viewmodel.GlicemicRecordDetailViewModel
import com.fiap.startupone.nutriglico.features.glicemiccontrol.register.data.repository.RegisterGlicemicControlRepositoryImpl
import com.fiap.startupone.nutriglico.features.glicemiccontrol.register.domain.factory.RegisterGlicemicLevelRequestFactory
import com.fiap.startupone.nutriglico.features.glicemiccontrol.register.domain.usecase.SaveGlicemicControlMeasurementUseCase
import com.fiap.startupone.nutriglico.features.glicemiccontrol.register.data.RegisterGlicemicControlRepository
import com.fiap.startupone.nutriglico.features.glicemiccontrol.register.ui.viewmodel.RegisterGlicemicControlViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val glicemicControlModule = module {
    // Register Feature
    single<RegisterGlicemicControlRepository> { RegisterGlicemicControlRepositoryImpl(get()) }
    single { SaveGlicemicControlMeasurementUseCase(get(), get()) }
    single { RegisterGlicemicLevelRequestFactory() }
    viewModel { RegisterGlicemicControlViewModel(get()) }

    // History Feature
    single<GlicemicHistoryRepository> { GlicemicHistoryRepositoryImpl(get()) }

    factory { FetchGlicemicHistoryUseCase(get()) }
    factory { FetchGlicemicDetailsUseCase(get()) }
    factory { UpdateGlicemicRecordUseCase(get()) }
    factory { DeleteGlicemicRecordUseCase(get()) }
    viewModel { GlicemicHistoryViewModel(get()) }
    viewModel {
        GlicemicRecordDetailViewModel(
            fetchGlicemicDetailsUseCase = get(),
            updateGlicemicRecordUseCase = get(),
            deleteGlicemicRecordUseCase = get(),
            savedStateHandle = get()
        )
    }
}