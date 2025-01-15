package com.example.bottomnavigationandbottomsheet.screens.login

import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class LoginScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    lateinit var navController: TestNavHostController
    lateinit var sharedPreferencesHelper: FakeSharedPreferencesHelper
    lateinit var sharedViewModel: FakeSharedViewModel

    @Before
    fun setup() {
        hiltRule.inject()
        MockitoAnnotations.openMocks(this)
        navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        sharedPreferencesHelper = mock(FakeSharedPreferencesHelper::class.java)
        sharedViewModel = mock(FakeSharedViewModel::class.java)
    }

    @Test
    fun testLoginScreenDisplaysCorrectly() {
        composeTestRule.setContent {
            Login(
                navController = navController,
                viewModel = sharedViewModel,
                preferencesHelper = sharedPreferencesHelper
            )
        }

        composeTestRule.onNodeWithContentDescription("Logo").assertExists()
        composeTestRule.onNodeWithText("Username").assertExists()
        composeTestRule.onNodeWithText("Password").assertExists()
        composeTestRule.onNodeWithText("Login").assertExists()

        composeTestRule.waitForIdle()
    }

    @Test
    fun testLoginButtonClick() {
        // Set the content to load the Login screen
        composeTestRule.setContent {
            Login(
                navController = navController,
                viewModel = sharedViewModel,
                preferencesHelper = sharedPreferencesHelper
            )
        }

        // Perform text input in the username and password fields
        composeTestRule.onNodeWithTag("uname").performTextInput("prashant")
        composeTestRule.onNodeWithTag("password").performTextInput("andhale")

        // Wait for the idle state (UI updates to complete)
        composeTestRule.waitForIdle()

       /* println(
            "Username field text: ${
                composeTestRule.onNodeWithTag("uname")
                    .fetchSemanticsNode().config.contentDescription
            }"
        )
        println(
            "Password field text: ${
                composeTestRule.onNodeWithTag("password")
                    .fetchSemanticsNode().config.contentDescription
            }"
        )*/

      /*  // Assert that the text in the username field is correct
        composeTestRule.onNodeWithTag("uname")
            .assertTextEquals("prashant")

        // Assert that the text in the password field is correct
        composeTestRule.onNodeWithTag("password")
            .assertTextEquals("andhale")*/

        // Click the login button
        composeTestRule.onNodeWithTag("login").performClick()

        // Verify that the login function was called in the ViewModel
        verify(sharedViewModel).setLogin(true)

        // Verify that the SharedPreferences helper was updated with the login status
        verify(sharedPreferencesHelper).isLoggedIn = true
    }

}

