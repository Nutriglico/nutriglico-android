package com.fiap.startupone.nutriglico.commons.di

import com.fiap.startupone.nutriglico.features.home.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    // ViewModel para a tela Home
    viewModel { HomeViewModel() }
}