package com.fiap.startupone.nutriglico.features.usermanagement.profile.domain.usecase

import android.util.Log
import com.fiap.startupone.nutriglico.features.usermanagement.profile.data.ProfileRepository
import com.fiap.startupone.nutriglico.features.usermanagement.profile.data.model.ProfileUserResponse
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetUserDetailsUseCaseTest {

    private lateinit var repository: ProfileRepository
    private lateinit var getUserDetailsUseCase: GetUserDetailsUseCase

    @Before
    fun setUp() {
        mockkStatic(Log::class)
        every { Log.d(any(), any()) } returns 0
        every { Log.e(any(), any()) } returns 0
        every { Log.e(any(), any(), any()) } returns 0

        repository = mockk()
        getUserDetailsUseCase = GetUserDetailsUseCase(repository)
    }

    @Test
    fun `invoke should return Success when repository returns success`() = runTest {
        val userId = "123"
        val userDetails = ProfileUserResponse(
            id = "1",
            name = "John Doe",
            email = "john.doe@example.com",
            createdAt = "2021-10-01T00:00:00Z",
            updatedAt = "2021-10-01T00:00:00Z"
        )
        coEvery { repository.getUserDetails(userId) } returns Result.success(userDetails)

        val result = getUserDetailsUseCase(userId)

        assertEquals(ProfileResult.Success(userDetails), result)
    }

    @Test
    fun `invoke should return Error when repository returns failure`() = runTest {
        val userId = "123"
        val exception = Exception("Erro desconhecido")
        coEvery { repository.getUserDetails(userId) } returns Result.failure(exception)

        val result = getUserDetailsUseCase(userId)

        assertEquals(ProfileResult.Error(exception), result)
    }

    @Test
    fun `invoke should handle generic exception`() = runTest {
        val userId = "123"
        coEvery { repository.getUserDetails(userId) } throws RuntimeException("Erro genérico")

        val result = getUserDetailsUseCase(userId)

        assert(result is ProfileResult.Error)
        assertEquals("Erro genérico", (result as ProfileResult.Error).exception.message)
    }

    @Test
    fun `invoke should return Error when userId is empty`() = runTest {
        val userId = ""
        val result = getUserDetailsUseCase(userId)

        assert(result is ProfileResult.Error)
        assertEquals("User ID não pode ser vazio", (result as ProfileResult.Error).exception.message)
    }

    @Test
    fun `invoke should return Error when there is a network failure`() = runTest {
        val userId = "123"
        val exception = Exception("Falha de conexão")
        coEvery { repository.getUserDetails(userId) } throws exception

        val result = getUserDetailsUseCase(userId)

        assert(result is ProfileResult.Error)
        assertEquals("Falha de conexão", (result as ProfileResult.Error).exception.message)
    }

}