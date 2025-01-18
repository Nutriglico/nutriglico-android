package com.fiap.startupone.nutriglico.features.usermanagement.signup.data.service

import com.fiap.startupone.nutriglico.features.usermanagement.signup.data.model.UserManagementRequest
import com.fiap.startupone.nutriglico.features.usermanagement.signup.data.model.UserManagementResponse
import retrofit2.Response

class MockUserManagementService : UserManagementService {

    private var mockUsers = mutableMapOf(
        "1" to UserManagementResponse(
            id = "1",
            name = "Renan Coelho",
            email = "renan.coelho@example.com",
            birthDate = "1990-01-01"
        ),
        "2" to UserManagementResponse(
            id = "2",
            name = "Amanda Oliveira",
            email = "amanda.oliveira@example.com",
            licenseNumber = "CRN123456"
        )
    )

    override suspend fun registerPatient(userManagementRequest: UserManagementRequest): Response<Unit> {
        val newId = (mockUsers.size + 1).toString()
        mockUsers[newId] = UserManagementResponse(
            id = newId,
            name = userManagementRequest.name,
            email = userManagementRequest.email,
            birthDate = userManagementRequest.birthDate
        )
        return Response.success(Unit)
    }

    override suspend fun registerNutritionist(userManagementRequest: UserManagementRequest): Response<Unit> {
        val newId = (mockUsers.size + 1).toString()
        mockUsers[newId] = UserManagementResponse(
            id = newId,
            name = userManagementRequest.name,
            email = userManagementRequest.email,
            licenseNumber = userManagementRequest.licenseNumber
        )
        return Response.success(Unit)
    }

    override suspend fun updateUser(userId: String, updatedUserManagementRequest: UserManagementRequest): Response<Unit> {
        val existingUser = mockUsers[userId]
        return if (existingUser != null) {
            mockUsers[userId] = UserManagementResponse(
                id = userId,
                name = updatedUserManagementRequest.name,
                email = updatedUserManagementRequest.email,
                birthDate = updatedUserManagementRequest.birthDate,
                licenseNumber = updatedUserManagementRequest.licenseNumber
            )
            Response.success(Unit)
        } else {
            Response.error(404, okhttp3.ResponseBody.create(null, "User not found"))
        }
    }

    override suspend fun deleteUser(userId: String): Response<Unit> {
        return if (mockUsers.remove(userId) != null) {
            Response.success(Unit)
        } else {
            Response.error(404, okhttp3.ResponseBody.create(null, "User not found"))
        }
    }

    override suspend fun getUserDetails(userId: String): Response<UserManagementResponse> {
        val user = mockUsers[userId]
        return if (user != null) {
            Response.success(user)
        } else {
            Response.error(404, okhttp3.ResponseBody.create(null, "User not found"))
        }
    }
}
