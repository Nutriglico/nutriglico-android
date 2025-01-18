package com.fiap.startupone.nutriglico.features.glicemiccontrol.register.data.repository

import android.util.Log
import com.fiap.startupone.nutriglico.features.glicemiccontrol.register.data.RegisterGlicemicControlRepository
import com.fiap.startupone.nutriglico.features.glicemiccontrol.register.data.model.GlicemicLevelResponse
import com.fiap.startupone.nutriglico.features.glicemiccontrol.register.data.model.RegisterGlicemicLevelRequest
import com.fiap.startupone.nutriglico.features.glicemiccontrol.register.data.service.RegisterGlicemicControlService
import retrofit2.HttpException
import java.io.IOException

class RegisterGlicemicControlRepositoryImpl(
    private val service: RegisterGlicemicControlService
) : RegisterGlicemicControlRepository {

    override suspend fun registerGlicemicLevel(request: RegisterGlicemicLevelRequest): Result<Unit> {
        return try {
            val response = service.registerGlicemicLevel(request)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(HttpException(response))
            }
        } catch (e: IOException) {
            Log.e("API Error", "IOException: ${e.message}", e)
            Result.failure(e)
        } catch (e: HttpException) {
            Log.e("API Error", "HttpException: ${e.message}", e)
            Result.failure(e)
        }
    }

    override suspend fun getGlicemicHistory(): Result<List<GlicemicLevelResponse>> {
        return try {
            val response = service.getGlicemicHistory()
            if (response.isSuccessful) {
                Result.success(response.body() ?: emptyList())
            } else {
                Result.failure(HttpException(response))
            }
        } catch (e: IOException) {
            Log.e("API Error", "IOException: ${e.message}", e)
            Result.failure(e)
        } catch (e: HttpException) {
            Log.e("API Error", "HttpException: ${e.message}", e)
            Result.failure(e)
        }
    }
}