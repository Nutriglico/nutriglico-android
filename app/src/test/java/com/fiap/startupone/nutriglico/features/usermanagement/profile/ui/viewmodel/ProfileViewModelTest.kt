package com.fiap.startupone.nutriglico.features.usermanagement.profile.ui.viewmodel

import android.util.Log
import com.fiap.startupone.nutriglico.MainDispatcherRule
import com.fiap.startupone.nutriglico.features.usermanagement.profile.data.model.ProfileUserResponse
import com.fiap.startupone.nutriglico.features.usermanagement.profile.domain.usecase.DeleteUserUseCase
import com.fiap.startupone.nutriglico.features.usermanagement.profile.domain.usecase.GetUserDetailsUseCase
import com.fiap.startupone.nutriglico.features.usermanagement.profile.domain.usecase.ProfileResult
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ProfileViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var getUserDetailsUseCase: GetUserDetailsUseCase
    private lateinit var deleteUserUseCase: DeleteUserUseCase
    private lateinit var viewModel: ProfileViewModel

    @Before
    fun setUp() {
        mockkStatic(Log::class)
        every { Log.d(any(), any()) } returns 0
        every { Log.e(any(), any()) } returns 0
        every { Log.e(any(), any(), any()) } returns 0

        getUserDetailsUseCase = mockk()
        deleteUserUseCase = mockk()
        viewModel = ProfileViewModel(getUserDetailsUseCase, deleteUserUseCase)
    }

    @Test
    fun `loadUserDetails should update uiState with Success when use case returns success`() = runTest {
        val userId = "123"
        val userDetails = mockk<ProfileUserResponse>()
        coEvery { getUserDetailsUseCase(userId) } returns ProfileResult.Success(userDetails)

        viewModel.loadUserDetails(userId)

        assertEquals(ProfileUIState.Success(userDetails), viewModel.uiState.value)
    }

    @Test
    fun `loadUserDetails should update uiState with Error when use case returns error`() = runTest {
        val userId = "123"
        val errorMessage = "Erro desconhecido"
        coEvery { getUserDetailsUseCase(userId) } returns ProfileResult.Error(Exception(errorMessage))

        viewModel.loadUserDetails(userId)

        assertEquals(ProfileUIState.Error(errorMessage), viewModel.uiState.value)
    }

    @Test
    fun `deleteAccount should call onSuccess when use case returns success`() = runTest {
        val userId = "123"
        coEvery { deleteUserUseCase(userId) } returns ProfileResult.Success(Unit)
        val onSuccess = mockk<() -> Unit>(relaxed = true)
        val onError = mockk<(String) -> Unit>(relaxed = true)

        viewModel.deleteAccount(userId, onSuccess, onError)

        coVerify { onSuccess() }
    }

    @Test
    fun `deleteAccount should call onError when use case returns error`() = runTest {
        val userId = "123"
        val errorMessage = "Erro desconhecido"
        coEvery { deleteUserUseCase(userId) } returns ProfileResult.Error(Exception(errorMessage))
        val onSuccess = mockk<() -> Unit>(relaxed = true)
        val onError = mockk<(String) -> Unit>(relaxed = true)

        viewModel.deleteAccount(userId, onSuccess, onError)

        coVerify { onError(errorMessage) }
    }
}
