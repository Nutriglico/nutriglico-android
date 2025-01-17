package com.fiap.startupone.nutriglico.features.usermanagement.profile.domain.usecase

import com.fiap.startupone.nutriglico.features.usermanagement.profile.data.ProfileRepository
import com.fiap.startupone.nutriglico.features.usermanagement.profile.data.model.ProfileUserResponse
import com.fiap.startupone.nutriglico.features.usermanagement.profile.domain.usecase.GetUserDetailsUseCase
import com.fiap.startupone.nutriglico.features.usermanagement.profile.domain.usecase.ProfileResult
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
class GetUserDetailsUseCaseTest {

    private lateinit var repository: ProfileRepository
    private lateinit var getUserDetailsUseCase: GetUserDetailsUseCase

    @Before
    fun setUp() {
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
}