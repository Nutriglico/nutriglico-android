package com.fiap.startupone.nutriglico.features.glicemiccontrol.history.domain.usecase

import com.fiap.startupone.nutriglico.features.glicemiccontrol.history.data.model.GlicemicHistoryResponse
import com.fiap.startupone.nutriglico.features.glicemiccontrol.history.data.GlicemicHistoryRepository

class FetchGlicemicHistoryUseCase(
    private val repository: GlicemicHistoryRepository
) {
    suspend operator fun invoke(): Result<List<GlicemicHistoryResponse>> {
        return repository.fetchHistory()
    }
}
