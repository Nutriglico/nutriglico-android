package com.fiap.startupone.nutriglico.features.glicemiccontrol.register.data.service

import com.fiap.startupone.nutriglico.features.glicemiccontrol.register.data.model.GlicemicLevelDetails
import com.fiap.startupone.nutriglico.features.glicemiccontrol.register.data.model.GlicemicLevelResponse
import com.fiap.startupone.nutriglico.features.glicemiccontrol.register.data.model.RegisterGlicemicLevelRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RegisterGlicemicControlService {

    @POST("/glicemic-level")
    suspend fun registerGlicemicLevel(@Body request: RegisterGlicemicLevelRequest): GlicemicLevelResponse

    @GET("/glicemic-level")
    suspend fun getGlicemicHistory(): List<GlicemicLevelDetails>
}
