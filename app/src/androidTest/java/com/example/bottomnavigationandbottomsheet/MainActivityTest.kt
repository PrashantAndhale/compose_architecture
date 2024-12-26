package com.example.bottomnavigationandbottomsheet

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testNavigationDrawerOpens() {
        // Verify that the drawer is closed initially
        composeTestRule.onNodeWithTag("Navigation Drawer")
            .assertIsNotDisplayed()

        // Perform a click on the menu icon to open the drawer
        composeTestRule.onNodeWithContentDescription("Menu")
            .performClick()

        // Verify that the drawer is open
        composeTestRule.onNodeWithTag("Navigation Drawer")
            .assertIsDisplayed()
    }

    @Test
    fun testBottomNavigationAndFab() {
        // Perform a click on the FloatingActionButton
        composeTestRule.onNodeWithContentDescription("Add")
            .performClick()
    }

}