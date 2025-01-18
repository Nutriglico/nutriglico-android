package com.fiap.startupone.nutriglico.features.glicemiccontrol.history.data.service

import com.fiap.startupone.nutriglico.features.glicemiccontrol.history.data.model.GlicemicHistoryResponse

class MockGlicemicHistoryService : GlicemicHistoryService {

    override suspend fun getHistory(): List<GlicemicHistoryResponse> {
        return listOf(
            GlicemicHistoryResponse(
                id = "1",
                level = 85,
                registerDate = "2025-01-01",
                type = "FAST",
                rate = "NORMAL",
                colorRate = "GREEN"
            ),
            GlicemicHistoryResponse(
                id = "2",
                level = 140,
                registerDate = "2025-01-02",
                type = "RANDOM",
                rate = "ALERT",
                colorRate = "YELLOW"
            ),
            GlicemicHistoryResponse(
                id = "3",
                level = 200,
                registerDate = "2025-01-03",
                type = "FAST",
                rate = "CRITICAL",
                colorRate = "RED"
            )
        )
    }

    override suspend fun getDetails(id: String): GlicemicHistoryResponse {
        return GlicemicHistoryResponse(
            id = id,
            level = 120,
            registerDate = "2025-01-04",
            type = "FAST",
            rate = "ALERT",
            colorRate = "YELLOW"
        )
    }

    override suspend fun updateRecord(id: String, record: GlicemicHistoryResponse) {
        // Simulação de atualização, sem operação real.
    }

    override suspend fun deleteRecord(id: String) {
        // Simulação de exclusão, sem operação real.
    }
}
