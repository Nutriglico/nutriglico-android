package com.fiap.startupone.nutriglico.features.glicemiccontrol.register.data.service

import com.fiap.startupone.nutriglico.features.glicemiccontrol.register.data.model.GlicemicLevelResponse
import com.fiap.startupone.nutriglico.features.glicemiccontrol.register.data.model.RegisterGlicemicLevelRequest
import retrofit2.Response

class MockRegisterGlicemicControlService : RegisterGlicemicControlService {

    private val mockHistory = mutableListOf(
        GlicemicLevelResponse("1", 90, "2025-01-01T10:00:00Z"),
        GlicemicLevelResponse("2", 120, "2025-01-02T12:00:00Z"),
        GlicemicLevelResponse("3", 140, "2025-01-03T14:00:00Z")
    )

    override suspend fun registerGlicemicLevel(request: RegisterGlicemicLevelRequest): Response<GlicemicLevelResponse> {
        val newRecord = GlicemicLevelResponse(
            id = (mockHistory.size + 1).toString(),
            value = request.level,
            timestamp = request.registerDate ?: "2025-01-04T16:00:00Z"
        )
        mockHistory.add(newRecord)
        return Response.success(newRecord)
    }

    override suspend fun getGlicemicHistory(): Response<List<GlicemicLevelResponse>> {
        return Response.success(mockHistory)
    }
}
