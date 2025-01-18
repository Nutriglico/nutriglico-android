package com.fiap.startupone.nutriglico.features.glicemiccontrol.history.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fiap.startupone.nutriglico.features.glicemiccontrol.history.data.model.GlicemicHistoryResponse
import com.fiap.startupone.nutriglico.features.glicemiccontrol.history.domain.usecase.FetchGlicemicHistoryUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GlicemicHistoryViewModel(
    private val fetchGlicemicHistoryUseCase: FetchGlicemicHistoryUseCase
) : ViewModel() {

    private val _historyState = MutableStateFlow<HistoryState>(HistoryState.Idle)
    val historyState: StateFlow<HistoryState> get() = _historyState

    fun loadHistory() {
        _historyState.value = HistoryState.Loading

        viewModelScope.launch {
            try {
                val result = fetchGlicemicHistoryUseCase()
                if (result.isSuccess) {
                    _historyState.value = HistoryState.Success(result.getOrDefault(emptyList()))
                } else {
                    _historyState.value = HistoryState.Error(result.exceptionOrNull()?.message ?: "Unknown error")
                }
            } catch (e: Exception) {
                _historyState.value = HistoryState.Error(e.message ?: "Unknown error")
            }
        }
    }

    sealed class HistoryState {
        object Idle : HistoryState()
        object Loading : HistoryState()
        data class Success(val history: List<GlicemicHistoryResponse>) : HistoryState()
        data class Error(val message: String) : HistoryState()
    }
}