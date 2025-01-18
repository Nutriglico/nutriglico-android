package com.fiap.startupone.nutriglico.features.usermanagement.profile.data.service

import com.fiap.startupone.nutriglico.features.usermanagement.profile.data.model.ProfileUserRequest
import com.fiap.startupone.nutriglico.features.usermanagement.profile.data.model.ProfileUserResponse
import retrofit2.Response

class MockProfileService : ProfileService {

    private val mockUsers = mutableMapOf(
        "1" to ProfileUserResponse(
            id = "1",
            name = "Renan Coelho",
            email = "renan.coelho@example.com",
            createdAt = "2025-01-01T10:00:00Z",
            updatedAt = "2025-01-01T12:00:00Z"
        ),
        "2" to ProfileUserResponse(
            id = "2",
            name = "Amanda Oliveira",
            email = "amanda.oliveira@example.com",
            createdAt = "2025-01-02T14:00:00Z",
            updatedAt = "2025-01-02T16:00:00Z"
        )
    )

    override suspend fun getUserDetails(userId: String): Response<ProfileUserResponse> {
        val user = mockUsers[userId]
        return if (user != null) {
            Response.success(user)
        } else {
            Response.error(404, okhttp3.ResponseBody.create(null, "User not found"))
        }
    }

    override suspend fun updateUser(userId: String, profileUserRequest: ProfileUserRequest): Response<Unit> {
        val existingUser = mockUsers[userId]
        return if (existingUser != null) {
            mockUsers[userId] = ProfileUserResponse(
                id = userId,
                name = profileUserRequest.name,
                email = profileUserRequest.email,
                createdAt = existingUser.createdAt,
                updatedAt = "2025-01-05T10:00:00Z"
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
}
