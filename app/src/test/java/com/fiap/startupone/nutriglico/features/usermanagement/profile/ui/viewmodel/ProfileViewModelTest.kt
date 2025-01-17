package com.fiap.startupone.nutriglico.features.usermanagement.profile.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fiap.startupone.nutriglico.features.usermanagement.profile.data.model.ProfileUserResponse
import com.fiap.startupone.nutriglico.features.usermanagement.profile.domain.usecase.DeleteUserUseCase
import com.fiap.startupone.nutriglico.features.usermanagement.profile.domain.usecase.GetUserDetailsUseCase
import com.fiap.startupone.nutriglico.features.usermanagement.profile.domain.usecase.ProfileResult
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
class ProfileViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    private lateinit var getUserDetailsUseCase: GetUserDetailsUseCase
    private lateinit var deleteUserUseCase: DeleteUserUseCase
    private lateinit var viewModel: ProfileViewModel

    @Before
    fun setUp() {
        getUserDetailsUseCase = mockk()
        deleteUserUseCase = mockk()
        viewModel = ProfileViewModel(getUserDetailsUseCase, deleteUserUseCase)
    }

    @Test
    fun `loadUserDetails should update uiState with Success when use case returns success`() = testScope.runTest {
        val userId = "123"
        val userDetails = mockk<ProfileUserResponse>()
        coEvery { getUserDetailsUseCase(userId) } returns ProfileResult.Success(userDetails)

        viewModel.loadUserDetails(userId)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(ProfileUIState.Success(userDetails), viewModel.uiState.value)
    }

    @Test
    fun `loadUserDetails should update uiState with Error when use case returns error`() = testScope.runTest {
        val userId = "123"
        val errorMessage = "Erro desconhecido"
        coEvery { getUserDetailsUseCase(userId) } returns ProfileResult.Error(Exception(errorMessage))

        viewModel.loadUserDetails(userId)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(ProfileUIState.Error(errorMessage), viewModel.uiState.value)
    }

    @Test
    fun `deleteAccount should call onSuccess when use case returns success`() = testScope.runTest {
        val userId = "123"
        coEvery { deleteUserUseCase(userId) } returns ProfileResult.Success(Unit)
        val onSuccess = mockk<() -> Unit>(relaxed = true)
        val onError = mockk<(String) -> Unit>(relaxed = true)

        viewModel.deleteAccount(userId, onSuccess, onError)
        testDispatcher.scheduler.advanceUntilIdle()

        coVerify { onSuccess() }
    }

    @Test
    fun `deleteAccount should call onError when use case returns error`() = testScope.runTest {
        val userId = "123"
        val errorMessage = "Erro desconhecido"
        coEvery { deleteUserUseCase(userId) } returns ProfileResult.Error(Exception(errorMessage))
        val onSuccess = mockk<() -> Unit>(relaxed = true)
        val onError = mockk<(String) -> Unit>(relaxed = true)

        viewModel.deleteAccount(userId, onSuccess, onError)
        testDispatcher.scheduler.advanceUntilIdle()

        coVerify { onError(errorMessage) }
    }
}