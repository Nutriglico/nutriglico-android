package com.fiap.startupone.nutriglico.features.glicemiccontrol.history.domain.usecase

import com.fiap.startupone.nutriglico.features.glicemiccontrol.history.data.model.GlicemicHistoryResponse
import com.fiap.startupone.nutriglico.features.glicemiccontrol.history.repository.GlicemicHistoryRepository

class FetchGlicemicDetailsUseCase(
    private val repository: GlicemicHistoryRepository
) {
    suspend operator fun invoke(id: String): GlicemicHistoryResponse {
        return repository.fetchDetails(id)
    }
}
