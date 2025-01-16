package com.fiap.startupone.nutriglico.features.glicemiccontrol.history.domain.usecase

import com.fiap.startupone.nutriglico.features.glicemiccontrol.history.repository.GlicemicHistoryRepository

class DeleteGlicemicRecordUseCase(
    private val repository: GlicemicHistoryRepository
) {
    suspend operator fun invoke(id: String) {
        repository.deleteRecord(id)
    }
}
