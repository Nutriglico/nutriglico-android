package com.fiap.startupone.nutriglico.commons.di

import com.fiap.startupone.nutriglico.features.glicemiccontrol.register.data.RegisterGlicemicControlRepositoryImpl
import com.fiap.startupone.nutriglico.features.glicemiccontrol.register.domain.usecase.SaveGlicemicControlMeasurementUseCase
import com.fiap.startupone.nutriglico.features.glicemiccontrol.register.repository.RegisterGlicemicControlRepository
import com.fiap.startupone.nutriglico.features.glicemiccontrol.register.viewmodel.RegisterGlicemicControlViewModel
import com.fiap.startupone.nutriglico.features.glicemiccontrol.history.data.GlicemicHistoryRepositoryImpl
import com.fiap.startupone.nutriglico.features.glicemiccontrol.history.data.service.GlicemicHistoryService
import com.fiap.startupone.nutriglico.features.glicemiccontrol.history.domain.usecase.*
import com.fiap.startupone.nutriglico.features.glicemiccontrol.history.repository.GlicemicHistoryRepository
import com.fiap.startupone.nutriglico.features.glicemiccontrol.history.viewmodel.GlicemicHistoryViewModel
import com.fiap.startupone.nutriglico.features.glicemiccontrol.history.viewmodel.GlicemicRecordDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val glicemicControlModule = module {
    // Register Feature
    single<RegisterGlicemicControlRepository> { RegisterGlicemicControlRepositoryImpl(get()) }
    single { SaveGlicemicControlMeasurementUseCase(get()) }
    viewModel { RegisterGlicemicControlViewModel(get()) }

    // History Feature
    single<GlicemicHistoryRepository> { GlicemicHistoryRepositoryImpl(get()) }
//    single {
//        get<Retrofit>().create(GlicemicHistoryService::class.java)
//    }
    factory { FetchGlicemicHistoryUseCase(get()) }
    factory { FetchGlicemicDetailsUseCase(get()) }
    factory { UpdateGlicemicRecordUseCase(get()) }
    factory { DeleteGlicemicRecordUseCase(get()) }
    viewModel { GlicemicHistoryViewModel(get()) }
    viewModel {
        GlicemicRecordDetailViewModel(
            fetchGlicemicDetailsUseCase = get(),
            updateGlicemicRecordUseCase = get(),
            deleteGlicemicRecordUseCase = get()
        )
    }
}
