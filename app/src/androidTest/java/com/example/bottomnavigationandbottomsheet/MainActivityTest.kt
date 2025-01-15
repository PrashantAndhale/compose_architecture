package com.example.bottomnavigationandbottomsheet

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.bottomnavigationandbottomsheet.activities.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class) // Ensure the test runner is specified
class MainActivityTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        hiltRule.inject() // Ensure Hilt injection before tests run
    }

    @Test
    fun testNavigationDrawerOpens() {
        // Verify that the drawer is closed initially
        composeTestRule.onNodeWithTag("Navigation Drawer")
            .assertIsNotDisplayed()

        // Perform a click on the menu icon to open the drawer
        composeTestRule.onNodeWithContentDescription("Menu")
            .performClick()

        // Wait for the UI to settle if necessary
        composeTestRule.waitForIdle()

        // Verify that the drawer is open
        composeTestRule.onNodeWithTag("Navigation Drawer")
            .assertIsDisplayed()
    }

    @Test
    fun testBottomNavigationAndFab() {
        // Perform a click on the FloatingActionButton
        composeTestRule.onNodeWithContentDescription("Add")
            .performClick()

        // Wait for the UI to settle after the click
        composeTestRule.waitForIdle()

        // Example assertion (ensure the FAB click triggers expected behavior)
        // composeTestRule.onNodeWithText("New Expense Added").assertIsDisplayed()
    }
}
