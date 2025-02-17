package com.fiap.startupone.nutriglico.features.usermanagement.signup.data.service

import com.fiap.startupone.nutriglico.features.usermanagement.signup.data.model.UserManagementRequest
import com.fiap.startupone.nutriglico.features.usermanagement.signup.data.model.UserManagementResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserManagementService {

    @POST("/user-registration-service/users/patients")
    suspend fun registerPatient(@Body userManagementRequest: UserManagementRequest): Response<Unit>

    @POST("/user-registration-service/users/nutritionists")
    suspend fun registerNutritionist(@Body userManagementRequest: UserManagementRequest): Response<Unit>

    @PUT("/user-registration-service/users/{id}")
    suspend fun updateUser(
        @Path("id") userId: String,
        @Body updatedUserManagementRequest: UserManagementRequest
    ): Response<Unit>

    @DELETE("/user-registration-service/users/{id}")
    suspend fun deleteUser(@Path("id") userId: String): Response<Unit>

    @GET("/user-registration-service/users/{id}")
    suspend fun getUserDetails(@Path("id") userId: String): Response<UserManagementResponse>

}
