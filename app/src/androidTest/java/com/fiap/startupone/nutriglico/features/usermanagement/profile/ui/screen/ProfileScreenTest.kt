package com.fiap.startupone.nutriglico.features.usermanagement.profile.ui.screen


import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.NavType
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import com.fiap.startupone.nutriglico.features.usermanagement.profile.data.model.ProfileUserResponse
import com.fiap.startupone.nutriglico.features.usermanagement.profile.ui.viewmodel.FakeProfileViewModel
import com.fiap.startupone.nutriglico.features.usermanagement.profile.ui.viewmodel.ProfileUIState
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalTestApi::class)
class ProfileScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    val fakeUser = ProfileUserResponse(
        id = "123",
        name = "John Doe",
        email = "john.doe@example.com",
        createdAt = "2023-01-01T00:00:00Z",
        updatedAt = "2023-01-01T00:00:00Z"
    )

    @Test
    fun profileScreenShouldDisplayUserDetailsWhenStateIsSuccess() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())

        // Usar a Fake ViewModel
        val fakeViewModel = FakeProfileViewModel(ProfileUIState.Success(fakeUser))

        composeTestRule.setContent {
            ProfileScreen(
                navController = navController,
                userId = "123",
                viewModel = fakeViewModel,
                navigateToLogin = { }
            )
        }

        // Verificar os textos do usuário
        composeTestRule.onNodeWithText("Nome: John Doe").assertIsDisplayed()
        composeTestRule.onNodeWithText("Email: john.doe@example.com").assertIsDisplayed()

        // Verificar os botões
        composeTestRule.onNodeWithText("Alterar Dados").assertIsDisplayed()
        composeTestRule.onNodeWithText("Deletar Conta").assertIsDisplayed()
    }

    @Test
    fun loadUserDetailsShouldTrackMethodCallAndLastUserId() {
        val fakeViewModel = FakeProfileViewModel(ProfileUIState.Idle)

        fakeViewModel.loadUserDetails("123")

        assertTrue(fakeViewModel.loadUserDetailsCalled)
        assertEquals("123", fakeViewModel.lastUserIdLoaded)
    }

    @Test
    fun deleteAccountShouldTriggerOnSuccessCallback() {
        val fakeViewModel = FakeProfileViewModel(ProfileUIState.Idle)
        var onSuccessCalled = false

        fakeViewModel.deleteAccount(
            userId = "123",
            onSuccess = { onSuccessCalled = true },
            onError = {}
        )

        fakeViewModel.triggerDeleteAccountSuccess()

        assertTrue(fakeViewModel.deleteAccountCalled)
        assertEquals("123", fakeViewModel.lastDeletedUserId)
        assertTrue(onSuccessCalled)
    }

    @Test
    fun deleteAccountShouldTriggerOnErrorCallback() {
        val fakeViewModel = FakeProfileViewModel(ProfileUIState.Idle)
        var errorMessage: String? = null

        fakeViewModel.deleteAccount(
            userId = "123",
            onSuccess = {},
            onError = { errorMessage = it }
        )

        fakeViewModel.triggerDeleteAccountError("Erro simulado")

        assertTrue(fakeViewModel.deleteAccountCalled)
        assertEquals("123", fakeViewModel.lastDeletedUserId)
        assertEquals("Erro simulado", errorMessage)
    }

    @Test
    fun profileScreenShouldNavigateToLoginWhenAccountDeleted() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext()).apply {
            navigatorProvider.addNavigator(ComposeNavigator())
        }

        var navigateToLoginCalled = false

        val fakeViewModel = FakeProfileViewModel(ProfileUIState.Success(fakeUser))

        composeTestRule.setContent {
            ProfileScreen(
                navController = navController,
                userId = "123",
                viewModel = fakeViewModel,
                navigateToLogin = { navigateToLoginCalled = true }
            )
        }

        // Simula o clique no botão "Deletar Conta"
        composeTestRule.onNodeWithText("Deletar Conta").assertIsDisplayed().performClick()

        // Aciona o sucesso manualmente
        fakeViewModel.triggerDeleteAccountSuccess()

        // Verifica se o callback foi chamado
        assertTrue("navigateToLogin não foi chamado.", navigateToLoginCalled)
    }

}
