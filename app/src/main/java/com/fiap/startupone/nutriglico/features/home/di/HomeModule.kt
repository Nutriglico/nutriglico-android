package com.fiap.startupone.nutriglico.features.home.di

import com.fiap.startupone.nutriglico.features.home.ui.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    // ViewModel para a tela Home
    viewModel { HomeViewModel() }
}