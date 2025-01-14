package com.fiap.startupone.nutriglico.features.home.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel : ViewModel() {

    private val _homeState = MutableStateFlow(HomeState())
    val homeState: StateFlow<HomeState> get() = _homeState

    fun onCardClick(action: HomeAction) {
        _homeState.value = _homeState.value.copy(action = action)
    }

    fun resetAction() {
        _homeState.value = _homeState.value.copy(action = null)
    }
}

data class HomeState(
    val action: HomeAction? = null
)

sealed class HomeAction {
    data object OpenRegisterGlucose : HomeAction()
    data object OpenHistory : HomeAction()
    data object OpenFood : HomeAction()
    data object OpenMedication : HomeAction()
}
