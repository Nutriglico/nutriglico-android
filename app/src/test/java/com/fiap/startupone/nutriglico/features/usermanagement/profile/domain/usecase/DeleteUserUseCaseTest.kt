package com.fiap.startupone.nutriglico.features.usermanagement.profile.domain.usecase

import com.fiap.startupone.nutriglico.features.usermanagement.profile.data.ProfileRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DeleteUserUseCaseTest {

    private lateinit var repository: ProfileRepository
    private lateinit var deleteUserUseCase: DeleteUserUseCase

    @Before
    fun setUp() {
        repository = mockk()
        deleteUserUseCase = DeleteUserUseCase(repository)
    }

    @Test
    fun `invoke should return Success when repository returns success`() = runTest {
        val userId = "123"
        coEvery { repository.deleteUser(userId) } returns Result.success(Unit)

        val result = deleteUserUseCase(userId)

        assertEquals(ProfileResult.Success(Unit), result)
    }

    @Test
    fun `invoke should return Error when repository returns failure`() = runTest {
        val userId = "123"
        val exception = Exception("Erro desconhecido")
        coEvery { repository.deleteUser(userId) } returns Result.failure(exception)

        val result = deleteUserUseCase(userId)

        assert(result is ProfileResult.Error)
        assertEquals("Erro desconhecido", (result as ProfileResult.Error).exception.message)
    }

    @Test
    fun `invoke should return Error when userId is empty`() = runTest {
        val userId = ""
        val result = deleteUserUseCase(userId)

        assert(result is ProfileResult.Error)
        assertEquals("User ID não pode ser vazio", (result as ProfileResult.Error).exception.message)
    }

    @Test
    fun `invoke should return Error when unexpected exception occurs`() = runTest {
        val userId = "123"
        coEvery { repository.deleteUser(userId) } throws RuntimeException("Erro inesperado")

        val result = deleteUserUseCase(userId)

        assert(result is ProfileResult.Error)
        assertEquals("Erro inesperado", (result as ProfileResult.Error).exception.message)
    }
}
