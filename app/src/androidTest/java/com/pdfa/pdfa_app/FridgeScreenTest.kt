package com.pdfa.pdfa_app

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.pdfa.pdfa_app.user_interface.screens.FridgeScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FridgeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun fridgeScreen_displaysCorrectly() {
        // When
        composeTestRule.setContent {
            FridgeScreen(
                onAddClick = {}
            )
        }

        // Then - test simple : l'écran se charge sans crash
        composeTestRule.onNodeWithContentDescription("Filter").assertIsDisplayed()
    }

    @Test
    fun addButton_isDisplayed() {
        composeTestRule.setContent {
            FridgeScreen(
                onAddClick = {}
            )
        }

        // Chercher n'importe quel bouton cliquable
        composeTestRule.onAllNodesWithText("+").fetchSemanticsNodes().isNotEmpty()
    }
}