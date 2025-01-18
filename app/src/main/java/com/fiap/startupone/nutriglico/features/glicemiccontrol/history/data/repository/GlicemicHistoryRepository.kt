package com.fiap.startupone.nutriglico.features.glicemiccontrol.history.data.repository

import com.fiap.startupone.nutriglico.features.glicemiccontrol.history.data.model.GlicemicHistoryResponse

interface GlicemicHistoryRepository {
    suspend fun fetchHistory(): Result<List<GlicemicHistoryResponse>>
    suspend fun fetchDetails(id: String): Result<GlicemicHistoryResponse>
    suspend fun updateRecord(record: GlicemicHistoryResponse): Result<Unit>
    suspend fun deleteRecord(id: String): Result<Unit>
}
