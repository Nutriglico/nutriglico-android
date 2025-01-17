package com.fiap.startupone.nutriglico.features.glicemiccontrol.history.data.repository

import com.fiap.startupone.nutriglico.features.glicemiccontrol.history.data.model.GlicemicHistoryResponse

interface GlicemicHistoryRepository {
    suspend fun fetchHistory(): List<GlicemicHistoryResponse>
    suspend fun fetchDetails(id: String): GlicemicHistoryResponse
    suspend fun updateRecord(record: GlicemicHistoryResponse)
    suspend fun deleteRecord(id: String)
}
