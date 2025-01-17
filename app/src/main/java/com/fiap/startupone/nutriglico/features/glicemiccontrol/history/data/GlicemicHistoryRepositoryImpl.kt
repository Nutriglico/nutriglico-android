package com.fiap.startupone.nutriglico.features.glicemiccontrol.history.data

import android.util.Log
import com.fiap.startupone.nutriglico.features.glicemiccontrol.history.data.model.GlicemicHistoryResponse
import com.fiap.startupone.nutriglico.features.glicemiccontrol.history.data.repository.GlicemicHistoryRepository
import com.fiap.startupone.nutriglico.features.glicemiccontrol.history.data.service.GlicemicHistoryService
import retrofit2.HttpException
import java.io.IOException

class GlicemicHistoryRepositoryImpl(
    private val service: GlicemicHistoryService
) : GlicemicHistoryRepository {

//    private val mockList = listOf(
//        GlicemicHistoryResponse(
//            id = "1",
//            level = 100,
//            registerDate = "01/01/2025",
//            type = "FAST",
//            rate = "NORMAL",
//            colorRate = "GREEN"
//        ),
//        GlicemicHistoryResponse(
//            id = "2",
//            level = 200,
//            registerDate = "10/12/2024",
//            type = "RANDOM",
//            rate = "CRITICAL",
//            colorRate = "RED"
//        ),
//        GlicemicHistoryResponse(
//            id = "3",
//            level = 65,
//            registerDate = "10/10/2024",
//            type = "FAST",
//            rate = "ALERT",
//            colorRate = "YELLOW"
//        )
//    )

    override suspend fun fetchHistory(): List<GlicemicHistoryResponse> {
        return try {
            val response = service.getHistory()
            response.ifEmpty {
                emptyList()
            }
        } catch (e: IOException) {
            Log.e("API Error", "IOException: ${e.message}", e)
            //mockList
            emptyList()
        } catch (e: HttpException) {
            Log.e("API Error", "HttpException: ${e.message}", e)
            emptyList()
        }
    }

    override suspend fun fetchDetails(id: String): GlicemicHistoryResponse {
        return try {
            service.getDetails(id)
        } catch (e: IOException) {
            Log.e("API Error", "IOException: ${e.message}", e)
            //mockList.find { it.id == id } ?: throw e
            throw e
        } catch (e: HttpException) {
            Log.e("API Error", "HttpException: ${e.message}", e)
            throw e
        }
    }

    override suspend fun updateRecord(record: GlicemicHistoryResponse) {
        try {
            service.updateRecord(record.id, record)
        } catch (e: IOException) {
            Log.e("API Error", "IOException: ${e.message}", e)
            //throw e
        } catch (e: HttpException) {
            Log.e("API Error", "HttpException: ${e.message}", e)
            throw e
        }
    }

    override suspend fun deleteRecord(id: String) {
        try {
            service.deleteRecord(id)
        } catch (e: IOException) {
            Log.e("API Error", "IOException: ${e.message}", e)
            throw e
        } catch (e: HttpException) {
            Log.e("API Error", "HttpException: ${e.message}", e)
            throw e
        }
    }
}