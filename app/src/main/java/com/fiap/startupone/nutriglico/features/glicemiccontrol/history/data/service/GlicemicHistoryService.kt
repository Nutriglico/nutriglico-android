package com.fiap.startupone.nutriglico.features.glicemiccontrol.history.data.service

import com.fiap.startupone.nutriglico.features.glicemiccontrol.history.data.model.GlicemicHistoryResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface GlicemicHistoryService {

    @GET("/glicemic-level")
    suspend fun getHistory(): List<GlicemicHistoryResponse>

    @GET("/glicemic-level/{id}")
    suspend fun getDetails(@Path("id") id: String): GlicemicHistoryResponse

    @PUT("/glicemic-level/{id}")
    suspend fun updateRecord(@Path("id") id: String, @Body record: GlicemicHistoryResponse)

    @DELETE("/glicemic-level/{id}")
    suspend fun deleteRecord(@Path("id") id: String)
}

