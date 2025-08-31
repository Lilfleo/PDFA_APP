package com.pdfa.pdfa_app.data.repository

import com.pdfa.pdfa_app.data.dao.RecipeDao
import com.pdfa.pdfa_app.data.model.Recipe
import com.pdfa.pdfa_app.api.Ingredient
import com.pdfa.pdfa_app.api.Tags
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*

@ExperimentalCoroutinesApi
class RecipeRepositoryTest {

    private lateinit var recipeDao: RecipeDao
    private lateinit var recipeRepository: RecipeRepository
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        recipeDao = mockk(relaxed = true)
        recipeRepository = RecipeRepository(recipeDao)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearMocks(recipeDao)
    }

    @Test
    fun `getAllRecip returns flow of recipes from dao`() = runTest {
        // Given
        val expectedRecipes = listOf(
            Recipe(
                id = 1,
                title = "Pasta Carbonara",
                subTitle = "Classic Italian dish",
                preparationTime = "10 min",
                totalCookingTime = "20 min",
                tags = Tags(diet = listOf("Italian"), tag = listOf("Quick")),
                ingredients = listOf(
                    Ingredient("Pasta", 200.0, "g"),
                    Ingredient("Eggs", 2.0, "pieces")
                ),
                steps = listOf("Boil pasta", "Mix eggs", "Combine")
            ),
            Recipe(
                id = 2,
                title = "Green Salad",
                subTitle = "Fresh and healthy",
                preparationTime = "5 min",
                totalCookingTime = "0 min",
                tags = Tags(diet = listOf("Vegetarian"), tag = listOf("Healthy")),
                ingredients = listOf(
                    Ingredient("Lettuce", 100.0, "g"),
                    Ingredient("Tomato", 1.0, "piece")
                ),
                steps = listOf("Wash vegetables", "Chop", "Mix")
            )
        )
        every { recipeDao.getAllRecipes() } returns flowOf(expectedRecipes)

        // When
        val result = recipeRepository.getAllRecip().first()

        // Then
        assertEquals(expectedRecipes, result)
        verify { recipeDao.getAllRecipes() }
    }

    @Test
    fun `getAllRecip returns empty list when dao returns empty`() = runTest {
        // Given
        every { recipeDao.getAllRecipes() } returns flowOf(emptyList())

        // When
        val result = recipeRepository.getAllRecip().firstOrNull()

        // Then
        assertTrue(result.isNullOrEmpty())
        verify { recipeDao.getAllRecipes() }
    }

    @Test
    fun `insertRecipe calls dao insertRecipe`() = runTest {
        // Given
        val recipe = Recipe(
            title = "Margherita Pizza",
            subTitle = "Classic pizza",
            preparationTime = "30 min",
            totalCookingTime = "45 min",
            tags = Tags(diet = listOf("Italian"), tag = listOf("Pizza")),
            ingredients = listOf(
                Ingredient("Dough", 1.0, "piece"),
                Ingredient("Tomato sauce", 200.0, "ml")
            ),
            steps = listOf("Make dough", "Add toppings", "Bake")
        )
        val expectedId = 123L
        coEvery { recipeDao.insertRecipe(recipe) } returns expectedId

        // When
        val result = recipeRepository.insertRecipe(recipe)

        // Then
        assertEquals(expectedId, result)
        coVerify { recipeDao.insertRecipe(recipe) }
    }

    @Test
    fun `insertRecipe with minimal data`() = runTest {
        // Given
        val recipe = Recipe(
            title = "Basic Recipe",
            subTitle = "",
            preparationTime = "0 min",
            totalCookingTime = "0 min",
            tags = Tags(),
            ingredients = emptyList(),
            steps = emptyList()
        )
        val expectedId = 456L
        coEvery { recipeDao.insertRecipe(recipe) } returns expectedId

        // When
        val result = recipeRepository.insertRecipe(recipe)

        // Then
        assertEquals(expectedId, result)
        coVerify { recipeDao.insertRecipe(recipe) }
    }

    @Test
    fun `deleteRecipe calls dao deleteRecipe`() = runTest {
        // Given
        val recipe = Recipe(
            id = 1,
            title = "Old Recipe",
            subTitle = "To be deleted",
            preparationTime = "5 min",
            totalCookingTime = "10 min",
            tags = Tags(tag = listOf("Old")),
            ingredients = listOf(Ingredient("Something", 1.0, "piece")),
            steps = listOf("Delete me")
        )
        coEvery { recipeDao.deleteRecipe(recipe) } just Runs

        // When
        recipeRepository.deleteRecipe(recipe)

        // Then
        coVerify { recipeDao.deleteRecipe(recipe) }
    }

    @Test
    fun `multiple operations work correctly`() = runTest {
        // Given
        val recipe = Recipe(
            title = "Test Recipe",
            subTitle = "For testing",
            preparationTime = "15 min",
            totalCookingTime = "30 min",
            tags = Tags(tag = listOf("Test")),
            ingredients = listOf(Ingredient("Test ingredient", 1.0, "piece")),
            steps = listOf("Test step")
        )
        val insertedRecipe = Recipe(
            id = 1,
            title = "Test Recipe",
            subTitle = "For testing",
            preparationTime = "15 min",
            totalCookingTime = "30 min",
            tags = Tags(tag = listOf("Test")),
            ingredients = listOf(Ingredient("Test ingredient", 1.0, "piece")),
            steps = listOf("Test step")
        )
        val insertId = 1L

        coEvery { recipeDao.insertRecipe(recipe) } returns insertId
        coEvery { recipeDao.deleteRecipe(insertedRecipe) } just Runs
        every { recipeDao.getAllRecipes() } returns flowOf(listOf(insertedRecipe))

        // When & Then
        val id = recipeRepository.insertRecipe(recipe)
        assertEquals(insertId, id)

        val recipes = recipeRepository.getAllRecip().first()
        assertEquals(1, recipes.size)

        recipeRepository.deleteRecipe(insertedRecipe)

        // Verify all calls
        coVerify { recipeDao.insertRecipe(recipe) }
        coVerify { recipeDao.deleteRecipe(insertedRecipe) }
        verify { recipeDao.getAllRecipes() }
    }
}