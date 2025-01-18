package com.fiap.startupone.nutriglico.features.usermanagement.profile.domain.usecase

import com.fiap.startupone.nutriglico.features.usermanagement.profile.data.ProfileRepository
import com.fiap.startupone.nutriglico.features.usermanagement.profile.data.model.ProfileUserRequest
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UpdateUserUseCaseTest {

    private lateinit var repository: ProfileRepository
    private lateinit var updateUserUseCase: UpdateUserUseCase

    @Before
    fun setUp() {
        repository = mockk()
        updateUserUseCase = UpdateUserUseCase(repository)
    }

    @Test
    fun `invoke should return Success when repository returns success`() = runTest {
        val userId = "123"
        val profileUserRequest = ProfileUserRequest(
            name = "John Doe",
            email = "john.doe@example.com",
            cpf = "111.222.333-44"
        )
        coEvery { repository.updateUser(userId, profileUserRequest) } returns Result.success(Unit)

        val result = updateUserUseCase(userId, profileUserRequest)

        assertEquals(ProfileResult.Success(Unit), result)
    }

    @Test
    fun `invoke should return Error when repository returns failure`() = runTest {
        val userId = "123"
        val profileUserRequest = ProfileUserRequest(
            name = "John Doe",
            email = "john.doe@example.com",
            cpf = "111.222.333-44"
        )
        val exception = Exception("Erro desconhecido")
        coEvery { repository.updateUser(userId, profileUserRequest) } returns Result.failure(exception)

        val result = updateUserUseCase(userId, profileUserRequest)

        assert(result is ProfileResult.Error)
        assertEquals("Erro desconhecido", (result as ProfileResult.Error).exception.message)
    }

    @Test
    fun `invoke should return Error when userId is blank`() = runTest {
        val userId = ""
        val profileUserRequest = ProfileUserRequest(
            name = "John Doe",
            email = "john.doe@example.com",
            cpf = "111.222.333-44"
        )

        val result = updateUserUseCase(userId, profileUserRequest)

        assert(result is ProfileResult.Error)
        assertEquals("User ID não pode ser vazio", (result as ProfileResult.Error).exception.message)
    }

    @Test
    fun `invoke should return Error when profileUserRequest is invalid`() = runTest {
        val userId = "123"
        val profileUserRequest = ProfileUserRequest(
            name = "",
            email = "",
            cpf = ""
        )

        val result = updateUserUseCase(userId, profileUserRequest)

        assert(result is ProfileResult.Error)
        assertEquals("Dados do usuário inválidos", (result as ProfileResult.Error).exception.message)
    }

    @Test
    fun `invoke should return Error when unexpected exception occurs`() = runTest {
        val userId = "123"
        val profileUserRequest = ProfileUserRequest(
            name = "John Doe",
            email = "john.doe@example.com",
            cpf = "111.222.333-44"
        )
        coEvery { repository.updateUser(userId, profileUserRequest) } throws RuntimeException("Erro inesperado")

        val result = updateUserUseCase(userId, profileUserRequest)

        assert(result is ProfileResult.Error)
        assertEquals("Erro inesperado", (result as ProfileResult.Error).exception.message)
    }
}
