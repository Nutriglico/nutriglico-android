package com.fiap.startupone.nutriglico.features.usermanagement.profile.data.service

import com.fiap.startupone.nutriglico.features.usermanagement.profile.data.model.ProfileUserRequest
import com.fiap.startupone.nutriglico.features.usermanagement.profile.data.model.ProfileUserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProfileService {

    @GET("/users/{id}")
    suspend fun getUserDetails(@Path("id") userId: String): Response<ProfileUserResponse>

    @PUT("/users/{id}")
    suspend fun updateUser(
        @Path("id") userId: String,
        @Body profileUserRequest: ProfileUserRequest
    ): Response<Unit>

    @DELETE("/users/{id}")
    suspend fun deleteUser(@Path("id") userId: String): Response<Unit>
}
