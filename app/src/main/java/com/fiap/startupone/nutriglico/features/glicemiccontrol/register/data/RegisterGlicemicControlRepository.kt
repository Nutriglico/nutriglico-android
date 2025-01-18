package com.fiap.startupone.nutriglico.features.glicemiccontrol.register.data

import com.fiap.startupone.nutriglico.features.glicemiccontrol.register.data.model.GlicemicLevelResponse
import com.fiap.startupone.nutriglico.features.glicemiccontrol.register.data.model.RegisterGlicemicLevelRequest

interface RegisterGlicemicControlRepository {
    suspend fun registerGlicemicLevel(request: RegisterGlicemicLevelRequest): Result<Unit>
    suspend fun getGlicemicHistory(): Result<List<GlicemicLevelResponse>>
}
