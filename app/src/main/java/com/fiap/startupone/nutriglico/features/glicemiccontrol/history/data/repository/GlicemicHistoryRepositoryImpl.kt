package com.fiap.startupone.nutriglico.features.glicemiccontrol.history.data.repository

import android.util.Log
import com.fiap.startupone.nutriglico.commons.util.FeatureFlags
import com.fiap.startupone.nutriglico.features.glicemiccontrol.history.data.GlicemicHistoryRepository
import com.fiap.startupone.nutriglico.features.glicemiccontrol.history.data.model.GlicemicHistoryResponse
import com.fiap.startupone.nutriglico.features.glicemiccontrol.history.data.service.GlicemicHistoryService
import com.fiap.startupone.nutriglico.features.glicemiccontrol.history.data.service.MockGlicemicHistoryService
import retrofit2.HttpException
import java.io.IOException

class GlicemicHistoryRepositoryImpl(
    service: GlicemicHistoryService
) : GlicemicHistoryRepository {

    private val actualService: GlicemicHistoryService = if (FeatureFlags.USE_MOCKS) {
        MockGlicemicHistoryService()
    } else {
        service
    }

    override suspend fun fetchHistory(): Result<List<GlicemicHistoryResponse>> {
        return try {
            val response = actualService.getHistory()
            Result.success(response.ifEmpty { emptyList() })
        } catch (e: IOException) {
            Log.e("API Error", "IOException: ${e.message}", e)
            Result.failure(e)
        } catch (e: HttpException) {
            Log.e("API Error", "HttpException: ${e.message}", e)
            Result.failure(e)
        }
    }

    override suspend fun fetchDetails(id: String): Result<GlicemicHistoryResponse> {
        return try {
            val response = actualService.getDetails(id)
            Result.success(response)
        } catch (e: IOException) {
            Log.e("API Error", "IOException: ${e.message}", e)
            Result.failure(e)
        } catch (e: HttpException) {
            Log.e("API Error", "HttpException: ${e.message}", e)
            Result.failure(e)
        }
    }

    override suspend fun updateRecord(record: GlicemicHistoryResponse): Result<Unit> {
        return try {
            actualService.updateRecord(record.id, record)
            Result.success(Unit)
        } catch (e: IOException) {
            Log.e("API Error", "IOException: ${e.message}", e)
            Result.failure(e)
        } catch (e: HttpException) {
            Log.e("API Error", "HttpException: ${e.message}", e)
            Result.failure(e)
        }
    }

    override suspend fun deleteRecord(id: String): Result<Unit> {
        return try {
            actualService.deleteRecord(id)
            Result.success(Unit)
        } catch (e: IOException) {
            Log.e("API Error", "IOException: ${e.message}", e)
            Result.failure(e)
        } catch (e: HttpException) {
            Log.e("API Error", "HttpException: ${e.message}", e)
            Result.failure(e)
        }
    }
}
