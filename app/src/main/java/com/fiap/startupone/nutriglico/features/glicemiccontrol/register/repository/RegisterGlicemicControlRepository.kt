package com.fiap.startupone.nutriglico.features.glicemiccontrol.register.repository

import com.fiap.startupone.nutriglico.features.glicemiccontrol.register.data.model.GlicemicLevelDetails
import com.fiap.startupone.nutriglico.features.glicemiccontrol.register.data.model.GlicemicLevelResponse
import com.fiap.startupone.nutriglico.features.glicemiccontrol.register.data.model.RegisterGlicemicLevelRequest

interface RegisterGlicemicControlRepository {
    suspend fun registerGlicemicLevel(request: RegisterGlicemicLevelRequest): GlicemicLevelResponse
    suspend fun getGlicemicHistory(): List<GlicemicLevelDetails>
}
