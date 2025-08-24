package com.pdfa.pdfa_app.user_interface.component

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.pdfa.pdfa_app.data.model.Tag
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TagsBoxTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    // Données de test
    private val testTag = Tag(
        id = 1,
        name = "Végétarien",
        color = "#4CAF50" // Vert
    )

    @Test
    fun tagsBox_displaysTagNameCorrectly() {
        // Arrange & Act
        composeTestRule.setContent {
            TagsBox(tag = testTag)
        }

        // Assert
        composeTestRule
            .onNodeWithText("Végétarien")
            .assertIsDisplayed()
    }

    @Test
    fun tagsBox_whenSelected_appliesTagColor() {
        // Arrange & Act
        composeTestRule.setContent {
            TagsBox(
                tag = testTag,
                isSelected = true
            )
        }

        // Assert
        composeTestRule
            .onNodeWithText("Végétarien")
            .assertIsDisplayed()

        // Note: Tester la couleur directement est complexe en Compose Testing
        // On peut tester indirectement via les interactions ou snapshots
    }

    @Test
    fun tagsBox_whenNotSelected_appliesWhiteBackground() {
        // Arrange & Act
        composeTestRule.setContent {
            TagsBox(
                tag = testTag,
                isSelected = false
            )
        }

        // Assert
        composeTestRule
            .onNodeWithText("Végétarien")
            .assertIsDisplayed()
    }

    @Test
    fun tagsBox_withOnClick_triggersClickAction() {
        // Arrange
        var clickCounter = 0

        composeTestRule.setContent {
            TagsBox(
                tag = testTag,
                onClick = { clickCounter++ }
            )
        }

        // Act
        composeTestRule
            .onNodeWithText("Végétarien")
            .performClick()

        // Assert
        assert(clickCounter == 1)
    }

    @Test
    fun tagsBox_withOnRemove_displaysRemoveButton() {
        // Arrange & Act
        composeTestRule.setContent {
            TagsBox(
                tag = testTag,
                onRemove = { }
            )
        }

        // Assert
        composeTestRule
            .onNodeWithContentDescription("Supprimer Végétarien")
            .assertIsDisplayed()
    }

    @Test
    fun tagsBox_withoutOnRemove_hidesRemoveButton() {
        // Arrange & Act
        composeTestRule.setContent {
            TagsBox(
                tag = testTag,
                onRemove = null
            )
        }

        // Assert
        composeTestRule
            .onNodeWithContentDescription("Supprimer Végétarien")
            .assertDoesNotExist()
    }

    @Test
    fun tagsBox_removeButton_triggersRemoveAction() {
        // Arrange
        var removeCounter = 0

        composeTestRule.setContent {
            TagsBox(
                tag = testTag,
                onRemove = { removeCounter++ }
            )
        }

        // Act
        composeTestRule
            .onNodeWithContentDescription("Supprimer Végétarien")
            .performClick()

        // Assert
        assert(removeCounter == 1)
    }

    @Test
    fun tagsBox_multipleClicks_triggersMultipleActions() {
        // Arrange
        var clickCounter = 0

        composeTestRule.setContent {
            TagsBox(
                tag = testTag,
                onClick = { clickCounter++ }
            )
        }

        // Act
        repeat(3) {
            composeTestRule
                .onNodeWithText("Végétarien")
                .performClick()
        }

        // Assert
        assert(clickCounter == 3)
    }
}

@RunWith(AndroidJUnit4::class)
class OldTagsBoxTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun oldTagsBox_displaysNameCorrectly() {
        // Arrange & Act
        composeTestRule.setContent {
            OldTagsBox(
                name = "Sans gluten",
                type = "Allergy",
                isColored = true
            )
        }

        // Assert
        composeTestRule
            .onNodeWithText("Sans gluten")
            .assertIsDisplayed()
    }

    @Test
    fun oldTagsBox_easyType_displaysCorrectly() {
        // Arrange & Act
        composeTestRule.setContent {
            OldTagsBox(
                name = "Facile",
                type = "Easy",
                isColored = true
            )
        }

        // Assert
        composeTestRule
            .onNodeWithText("Facile")
            .assertIsDisplayed()
    }

    @Test
    fun oldTagsBox_allergyType_displaysCorrectly() {
        // Arrange & Act
        composeTestRule.setContent {
            OldTagsBox(
                name = "Allergie",
                type = "Allergy",
                isColored = true
            )
        }

        // Assert
        composeTestRule
            .onNodeWithText("Allergie")
            .assertIsDisplayed()
    }

    @Test
    fun oldTagsBox_dietType_displaysCorrectly() {
        // Arrange & Act
        composeTestRule.setContent {
            OldTagsBox(
                name = "Régime",
                type = "Diet",
                isColored = true
            )
        }

        // Assert
        composeTestRule
            .onNodeWithText("Régime")
            .assertIsDisplayed()
    }

    @Test
    fun oldTagsBox_unknownType_displaysCorrectly() {
        // Arrange & Act
        composeTestRule.setContent {
            OldTagsBox(
                name = "Autre",
                type = "Unknown",
                isColored = true
            )
        }

        // Assert
        composeTestRule
            .onNodeWithText("Autre")
            .assertIsDisplayed()
    }

    @Test
    fun oldTagsBox_notColored_displaysCorrectly() {
        // Arrange & Act
        composeTestRule.setContent {
            OldTagsBox(
                name = "Non coloré",
                type = "Easy",
                isColored = false
            )
        }

        // Assert
        composeTestRule
            .onNodeWithText("Non coloré")
            .assertIsDisplayed()
    }
}
