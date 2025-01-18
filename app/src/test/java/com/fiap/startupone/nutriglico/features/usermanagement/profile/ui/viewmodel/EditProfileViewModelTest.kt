package com.fiap.startupone.nutriglico.features.usermanagement.profile.ui.viewmodel

import com.fiap.startupone.nutriglico.MainDispatcherRule
import com.fiap.startupone.nutriglico.features.usermanagement.profile.data.model.ProfileUserRequest
import com.fiap.startupone.nutriglico.features.usermanagement.profile.domain.usecase.ProfileResult
import com.fiap.startupone.nutriglico.features.usermanagement.profile.domain.usecase.UpdateUserUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class EditProfileViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var updateUserUseCase: UpdateUserUseCase
    private lateinit var viewModel: EditProfileViewModel

    @Before
    fun setUp() {
        updateUserUseCase = mockk()
        viewModel = EditProfileViewModel(updateUserUseCase)
    }

    @Test
    fun `updateUser should update uiState with Success and call onSuccess`() = runTest {
        val userId = "123"
        val profileUserRequest = ProfileUserRequest("John Doe", "john.doe@example.com", "111.222.333-44")
        coEvery { updateUserUseCase(userId, profileUserRequest) } returns ProfileResult.Success(Unit)

        val onSuccess = mockk<() -> Unit>(relaxed = true)
        val onError = mockk<(String) -> Unit>(relaxed = true)

        viewModel.updateUser(userId, profileUserRequest, onSuccess, onError)

        assertEquals(EditProfileUIState.Success, viewModel.uiState.value)
        coVerify { onSuccess() }
    }
}
