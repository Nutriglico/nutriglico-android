package com.fiap.startupone.nutriglico.features.usermanagement.profile.data.repository

import android.util.Log
import com.fiap.startupone.nutriglico.features.usermanagement.profile.data.model.ProfileUserRequest
import com.fiap.startupone.nutriglico.features.usermanagement.profile.data.model.ProfileUserResponse
import com.fiap.startupone.nutriglico.features.usermanagement.profile.data.service.ProfileService
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class ProfileRepositoryImplTest {

    private lateinit var profileService: ProfileService
    private lateinit var profileRepository: ProfileRepositoryImpl

    @Before
    fun setUp() {
        mockkStatic(Log::class)
        every { Log.d(any(), any()) } returns 0
        every { Log.e(any(), any()) } returns 0
        every { Log.e(any(), any(), any()) } returns 0

        profileService = mockk(relaxed = true)
        profileRepository = ProfileRepositoryImpl(profileService)
    }

    @Test
    fun `getUserDetails should return Success when service returns success`() = runTest {
        val userId = "123"
        val userDetails = ProfileUserResponse(
            id = "1",
            name = "John Doe",
            email = "john.doe@example.com",
            createdAt = "2021-10-01T00:00:00Z",
            updatedAt = "2021-10-01T00:00:00Z"
        )
        coEvery { profileService.getUserDetails(userId) } returns Response.success(userDetails)

        val result = profileRepository.getUserDetails(userId)

        assertEquals(Result.success(userDetails), result)
    }

    @Test
    fun `getUserDetails should return Error with detailed message when service returns failure`() = runTest {
        val userId = "123"
        val errorResponse = Response.error<ProfileUserResponse>(404, "User not found".toResponseBody())
        coEvery { profileService.getUserDetails(userId) } returns errorResponse

        val result = profileRepository.getUserDetails(userId)

        assert(result.isFailure)
        assertEquals("User not found", result.exceptionOrNull()?.message)
    }

    @Test
    fun `getUserDetails should handle exceptions`() = runTest {
        val userId = "123"
        coEvery { profileService.getUserDetails(userId) } throws RuntimeException("Unexpected error")

        val result = profileRepository.getUserDetails(userId)

        assert(result.isFailure)
        assertEquals("Unexpected error", result.exceptionOrNull()?.message)
    }

    @Test
    fun `updateUser should return Success when service returns success`() = runTest {
        val userId = "123"
        val profileUserRequest = ProfileUserRequest(
            name = "John Doe",
            email = "john.doe@example.com",
            cpf = "123.456.789-00"
        )
        coEvery { profileService.updateUser(userId, profileUserRequest) } returns Response.success(Unit)

        val result = profileRepository.updateUser(userId, profileUserRequest)

        assertEquals(Result.success(Unit), result)
    }

    @Test
    fun `updateUser should return Error with detailed message when service returns failure`() = runTest {
        val userId = "123"
        val profileUserRequest = ProfileUserRequest(
            name = "John Doe",
            email = "john.doe@example.com",
            cpf = "123.456.789-00"
        )
        val errorResponse = Response.error<Unit>(400, "Invalid data".toResponseBody())
        coEvery { profileService.updateUser(userId, profileUserRequest) } returns errorResponse

        val result = profileRepository.updateUser(userId, profileUserRequest)

        assert(result.isFailure)
        assertEquals("Invalid data", result.exceptionOrNull()?.message)
    }

    @Test
    fun `deleteUser should return Success when service returns success`() = runTest {
        val userId = "123"
        coEvery { profileService.deleteUser(userId) } returns Response.success(Unit)

        val result = profileRepository.deleteUser(userId)

        assertEquals(Result.success(Unit), result)
    }

    @Test
    fun `deleteUser should return Error with detailed message when service returns failure`() = runTest {
        val userId = "123"
        val errorResponse = Response.error<Unit>(404, "User not found".toResponseBody())
        coEvery { profileService.deleteUser(userId) } returns errorResponse

        val result = profileRepository.deleteUser(userId)

        assert(result.isFailure)
        assertEquals("User not found", result.exceptionOrNull()?.message)
    }
}
