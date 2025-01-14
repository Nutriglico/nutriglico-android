package com.fiap.startupone.nutriglico.features.glicemiccontrol.register.data

import com.fiap.startupone.nutriglico.features.glicemiccontrol.register.data.model.GlicemicLevelDetails
import com.fiap.startupone.nutriglico.features.glicemiccontrol.register.data.service.RegisterGlicemicControlService
import com.fiap.startupone.nutriglico.features.glicemiccontrol.register.data.model.RegisterGlicemicLevelRequest
import com.fiap.startupone.nutriglico.features.glicemiccontrol.register.repository.RegisterGlicemicControlRepository

class RegisterGlicemicControlRepositoryImpl(
    private val service: RegisterGlicemicControlService
) : RegisterGlicemicControlRepository {
    override suspend fun registerGlicemicLevel(request: RegisterGlicemicLevelRequest) =
        service.registerGlicemicLevel(request)

    override suspend fun getGlicemicHistory(): List<GlicemicLevelDetails> = service.getGlicemicHistory()
}
