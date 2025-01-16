package com.fiap.startupone.nutriglico.features.glicemiccontrol.history.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fiap.startupone.nutriglico.features.glicemiccontrol.history.data.model.GlicemicHistoryResponse
import com.fiap.startupone.nutriglico.features.glicemiccontrol.history.domain.usecase.DeleteGlicemicRecordUseCase
import com.fiap.startupone.nutriglico.features.glicemiccontrol.history.domain.usecase.FetchGlicemicDetailsUseCase
import com.fiap.startupone.nutriglico.features.glicemiccontrol.history.domain.usecase.UpdateGlicemicRecordUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GlicemicRecordDetailViewModel(
    private val fetchGlicemicDetailsUseCase: FetchGlicemicDetailsUseCase,
    private val updateGlicemicRecordUseCase: UpdateGlicemicRecordUseCase,
    private val deleteGlicemicRecordUseCase: DeleteGlicemicRecordUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val recordId: String = savedStateHandle["recordId"] ?: ""

    private val _recordState = MutableStateFlow<RecordState>(RecordState.Idle)
    val recordState: StateFlow<RecordState> get() = _recordState

    init {
        loadRecord(recordId)
    }

    fun loadRecord(id: String) {
        _recordState.value = RecordState.Loading

        viewModelScope.launch {
            try {
                val record = fetchGlicemicDetailsUseCase(id)
                _recordState.value = RecordState.Success(record)
            } catch (e: Exception) {
                _recordState.value = RecordState.Error(e.message ?: "Erro ao carregar o registro")
            }
        }
    }

    fun updateRecord(record: GlicemicHistoryResponse) {
        viewModelScope.launch {
            try {
                updateGlicemicRecordUseCase(record)
                _recordState.value = RecordState.Success(record)
            } catch (e: Exception) {
                _recordState.value = RecordState.Error(e.message ?: "Erro ao atualizar o registro")
            }
        }
    }

    fun deleteRecord(id: String) {
        viewModelScope.launch {
            try {
                deleteGlicemicRecordUseCase(id)
                _recordState.value = RecordState.Deleted
            } catch (e: Exception) {
                _recordState.value = RecordState.Error(e.message ?: "Erro ao excluir o registro")
            }
        }
    }

    sealed class RecordState {
        object Idle : RecordState()
        object Loading : RecordState()
        data class Success(val record: GlicemicHistoryResponse) : RecordState()
        data class Error(val message: String) : RecordState()
        object Deleted : RecordState()
    }
}
