package com.pdfa.pdfa_app.data.repository

import com.pdfa.pdfa_app.api.ApiClient
import com.pdfa.pdfa_app.api.HelloResponse
import com.pdfa.pdfa_app.api.RecipeForShoplist
import com.pdfa.pdfa_app.api.RecipeMultipleResponse
import com.pdfa.pdfa_app.api.RecipeResponse
import com.pdfa.pdfa_app.api.RecipeWithFood
import com.pdfa.pdfa_app.api.RecipeWithFoodPrompt
import com.pdfa.pdfa_app.api.RecipeWithoutFoodPrompt
import com.pdfa.pdfa_app.api.Recipe
import com.pdfa.pdfa_app.api.Ingredient
import com.pdfa.pdfa_app.api.Tags
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*

@ExperimentalCoroutinesApi
class ApiRepositoryTest {

    private lateinit var mockApiClient: ApiClient
    private lateinit var apiRepository: ApiRepository
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        // Mock Android Log
        mockkStatic(android.util.Log::class)
        every { android.util.Log.d(any(), any()) } returns 0
        every { android.util.Log.w(any(), any(), any()) } returns 0
        every { android.util.Log.e(any(), any(), any()) } returns 0

        mockApiClient = mockk(relaxed = true)

        // Mock the ApiClient constructor
        mockkConstructor(ApiClient::class)
        apiRepository = ApiRepository()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkStatic(android.util.Log::class)
        unmockkConstructor(ApiClient::class)
        clearAllMocks()
    }

    @Test
    fun `testConnection returns HelloResponse on success`() = runTest {
        // Given
        val expectedResponse = HelloResponse("Hello from API!")
        clearAllMocks()
        coEvery { anyConstructed<ApiClient>().testConnection() } returns Result.success(expectedResponse)
        apiRepository = ApiRepository()
        // When
        val result = apiRepository.testConnection()

        // Then
        assertEquals(expectedResponse, result)
        coVerify { anyConstructed<ApiClient>().testConnection() }
    }

    @Test
    fun `testConnection throws exception on failure`() = runTest {
        // Given
        val exception = Exception("Connection failed")
        clearAllMocks()
        coEvery { anyConstructed<ApiClient>().testConnection() } returns Result.failure(exception)
        apiRepository = ApiRepository()
        // When & Then
        try {
            apiRepository.testConnection()
            fail("Expected exception to be thrown")
        } catch (e: Exception) {
            assertEquals("Connection failed", e.message)
        }
        coVerify { anyConstructed<ApiClient>().testConnection() }
    }

    @Test
    fun `generateRecipe returns RecipeResponse on success`() = runTest {
        // Given
        val requestData = RecipeWithFood(
            prompt = RecipeWithFoodPrompt(
                title = "Pasta",
                ingredients = listOf(Ingredient("Pasta", 200.0, "g")),
                utensils = listOf("pot", "spoon"),
                tags = Tags(tag = listOf("Italian"))
            ),
            excludedTitles = emptyList()
        )

        // Create a proper Recipe object
        val recipe = Recipe(
            title = "Delicious Pasta",
            subTitle = "A simple Italian dish",
            preparationTime = "10 minutes",
            totalCookingTime = "20 minutes",
            tags = Tags(tag = listOf("Italian")),
            ingredients = listOf(Ingredient("Pasta", 200.0, "g")),
            steps = listOf("Boil water", "Cook pasta", "Serve")
        )
        val expectedResponse = RecipeResponse(recipe)
        clearAllMocks()
        coEvery { anyConstructed<ApiClient>().getRecipeWithFood(requestData) } returns Result.success(expectedResponse)
        apiRepository = ApiRepository()
        // When
        val result = apiRepository.generateRecipe(requestData)

        // Then
        assertEquals(expectedResponse, result)
        coVerify { anyConstructed<ApiClient>().getRecipeWithFood(requestData) }
    }

    @Test
    fun `generateMultipleRecipWithFood returns RecipeMultipleResponse on success`() = runTest {
        // Given
        val requestData = RecipeWithFood(
            prompt = RecipeWithFoodPrompt(
                title = null,
                ingredients = listOf(
                    Ingredient("Chicken", 300.0, "g"),
                    Ingredient("Rice", 150.0, "g")
                ),
                utensils = listOf("pan"),
                tags = Tags(diet = listOf("Protein"))
            ),
            excludedTitles = listOf("Fried Rice")
        )

        // Create proper Recipe objects
        val recipe1 = Recipe(
            title = "Chicken Rice Bowl",
            subTitle = "Protein-rich meal",
            preparationTime = "15 minutes",
            totalCookingTime = "25 minutes",
            tags = Tags(diet = listOf("Protein")),
            ingredients = listOf(
                Ingredient("Chicken", 300.0, "g"),
                Ingredient("Rice", 150.0, "g")
            ),
            steps = listOf("Cook rice", "Cook chicken", "Combine")
        )

        val recipe2 = Recipe(
            title = "Chicken Stir Fry",
            subTitle = "Quick and easy",
            preparationTime = "10 minutes",
            totalCookingTime = "15 minutes",
            tags = Tags(diet = listOf("Protein")),
            ingredients = listOf(
                Ingredient("Chicken", 300.0, "g"),
                Ingredient("Rice", 150.0, "g")
            ),
            steps = listOf("Heat pan", "Stir fry chicken", "Add rice")
        )

        val expectedResponse = RecipeMultipleResponse(listOf(recipe1, recipe2))
        clearAllMocks()
        coEvery { anyConstructed<ApiClient>().getMultipleRecipeWithFood(requestData) } returns Result.success(expectedResponse)
        apiRepository = ApiRepository()
        // When
        val result = apiRepository.generateMultipleRecipWithFood(requestData)

        // Then
        assertEquals(expectedResponse, result)
        coVerify { anyConstructed<ApiClient>().getMultipleRecipeWithFood(requestData) }
    }

    @Test
    fun `generateMultipleRecipWithoutFood returns RecipeMultipleResponse on success`() = runTest {
        // Given
        val requestData = RecipeForShoplist(
            prompt = RecipeWithoutFoodPrompt(
                title = "Quick Meal",
                utensils = listOf("microwave"),
                tags = Tags(tag = listOf("Quick", "Easy"))
            ),
            excludedTitles = listOf("Instant Noodles")
        )

        // Create proper Recipe objects
        val recipe1 = Recipe(
            title = "Microwave Mac and Cheese",
            subTitle = "Quick comfort food",
            preparationTime = "2 minutes",
            totalCookingTime = "5 minutes",
            tags = Tags(tag = listOf("Quick", "Easy")),
            ingredients = listOf(
                Ingredient("Pasta", 100.0, "g"),
                Ingredient("Cheese", 50.0, "g")
            ),
            steps = listOf("Add pasta to bowl", "Microwave", "Add cheese")
        )

        val recipe2 = Recipe(
            title = "Microwave Baked Potato",
            subTitle = "Simple side dish",
            preparationTime = "1 minute",
            totalCookingTime = "8 minutes",
            tags = Tags(tag = listOf("Quick", "Easy")),
            ingredients = listOf(
                Ingredient("Potato", 1.0, "piece"),
                Ingredient("Butter", 10.0, "g")
            ),
            steps = listOf("Pierce potato", "Microwave", "Add butter")
        )

        val expectedResponse = RecipeMultipleResponse(listOf(recipe1, recipe2))
        clearAllMocks()
        coEvery { anyConstructed<ApiClient>().getMultipleRecipeWithoutFood(requestData) } returns Result.success(expectedResponse)
        apiRepository = ApiRepository()
        // When
        val result = apiRepository.generateMultipleRecipWithoutFood(requestData)

        // Then
        assertEquals(expectedResponse, result)
        coVerify { anyConstructed<ApiClient>().getMultipleRecipeWithoutFood(requestData) }
    }

    @Test
    fun `cleanup calls apiClient close`() = runTest {
        clearAllMocks()
        // Given
        every { anyConstructed<ApiClient>().close() } just Runs
        apiRepository = ApiRepository()
        // When
        apiRepository.cleanup()

        // Then
        verify { anyConstructed<ApiClient>().close() }
    }

    @Test
    fun `generateRecipe throws exception on api failure`() = runTest {
        // Given
        val requestData = RecipeWithFood(
            prompt = RecipeWithFoodPrompt(
                title = "Failed Recipe",
                ingredients = listOf(Ingredient("Test", 1.0, "piece")),
                utensils = emptyList(),
                tags = Tags()
            ),
            excludedTitles = emptyList()
        )
        val exception = Exception("API Error")
        clearAllMocks()
        coEvery { anyConstructed<ApiClient>().getRecipeWithFood(requestData) } returns Result.failure(exception)
        apiRepository = ApiRepository()
        // When & Then
        try {
            apiRepository.generateRecipe(requestData)
            fail("Expected exception to be thrown")
        } catch (e: Exception) {
            assertEquals("API Error", e.message)
        }
        coVerify { anyConstructed<ApiClient>().getRecipeWithFood(requestData) }
    }
}