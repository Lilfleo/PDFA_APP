package com.pdfa.pdfa_app.data.repository

import com.pdfa.pdfa_app.data.model.Food
import com.pdfa.pdfa_app.data.model.Recipe
import com.pdfa.pdfa_app.data.model.Shoplist
import com.pdfa.pdfa_app.data.model.Cookbook
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
class RecipeToShoplistServiceTest {

    private lateinit var foodRepository: FoodRepository
    private lateinit var shoplistRepository: ShoplistRepository
    private lateinit var cookbookRepository: CookbookRepository
    private lateinit var recipeToShoplistService: RecipeToShoplistService
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        // Mock Android Log
        mockkStatic(android.util.Log::class)
        every { android.util.Log.d(any(), any()) } returns 0
        every { android.util.Log.e(any(), any(), any()) } returns 0

        foodRepository = mockk(relaxed = true)
        shoplistRepository = mockk(relaxed = true)
        cookbookRepository = mockk(relaxed = true)
        recipeToShoplistService = RecipeToShoplistService(
            foodRepository,
            shoplistRepository,
            cookbookRepository
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkStatic(android.util.Log::class)
        clearAllMocks()
    }

    @Test
    fun `addRecipeToShoplist adds new ingredients to shoplist`() = runTest {
        // Given
        val recipe = Recipe(
            id = 1,
            title = "Pasta",
            subTitle = "Simple pasta",
            preparationTime = "10 min",
            totalCookingTime = "20 min",
            tags = Tags(tag = listOf("Italian")),
            ingredients = listOf(
                Ingredient("Pasta", 200.0, "g"),
                Ingredient("Tomatoes", 3.0, "pieces")
            ),
            steps = listOf("Cook pasta", "Add tomatoes")
        )
        val cookbook = Cookbook(id = 1, name = "RecipeShoplist", isInternal = true, isDeletable = false)
        val pastaFood = Food(id = 1, name = "Pasta", link = "https://example.com/pasta", category = "Grain", unit = listOf("g"), caloriesPerKg = 1500, caloriesPerUnit = 0)
        val tomatoFood = Food(id = 2, name = "Tomatoes", link = null, category = "Vegetable", unit = listOf("pieces"), caloriesPerKg = 180, caloriesPerUnit = 20)

        coEvery { cookbookRepository.getCookbookByName("RecipeShoplist") } returns cookbook
        coEvery { cookbookRepository.addRecipeToCookbook(1, 1) } just Runs
        coEvery { foodRepository.findByName("Pasta") } returns pastaFood
        coEvery { foodRepository.findByName("Tomatoes") } returns tomatoFood
        coEvery { shoplistRepository.findByFoodId(1) } returns null
        coEvery { shoplistRepository.findByFoodId(2) } returns null
        coEvery { shoplistRepository.insertShoplist(any()) } just Runs

        // When
        recipeToShoplistService.addRecipeToShoplist(recipe, 2)

        // Then
        coVerify { cookbookRepository.addRecipeToCookbook(1, 1) }
        coVerify { shoplistRepository.insertShoplist(match { it.foodId == 1 && it.quantity == 400 }) } // 200 * 2 people
        coVerify { shoplistRepository.insertShoplist(match { it.foodId == 2 && it.quantity == 6 }) } // 3 * 2 people
    }

    @Test
    fun `addRecipeToShoplist updates existing shoplist items`() = runTest {
        // Given
        val recipe = Recipe(
            id = 1,
            title = "Pasta",
            subTitle = "Simple pasta",
            preparationTime = "10 min",
            totalCookingTime = "20 min",
            tags = Tags(),
            ingredients = listOf(Ingredient("Pasta", 100.0, "g")),
            steps = listOf("Cook pasta")
        )
        val existingFood = Food(id = 1, name = "Pasta", link = "https://example.com/pasta", category = "Grain", unit = listOf("g"), caloriesPerKg = 1500, caloriesPerUnit = 0)
        val existingShoplistItem = Shoplist(id = 1, foodId = 1, quantity = 50, quantityType = "g")

        coEvery { cookbookRepository.getCookbookByName("RecipeShoplist") } returns null
        coEvery { foodRepository.findByName("Pasta") } returns existingFood
        coEvery { shoplistRepository.findByFoodId(1) } returns existingShoplistItem
        coEvery { shoplistRepository.updateShoplist(any()) } just Runs

        // When
        recipeToShoplistService.addRecipeToShoplist(recipe, 2)

        // Then
        coVerify { shoplistRepository.updateShoplist(match { it.quantity == 250 && it.quantityType == "g" }) } // 50 existing + (100 * 2 people)
        coVerify(exactly = 0) { shoplistRepository.insertShoplist(any()) }
    }

    @Test
    fun `addRecipeToShoplist creates new food when not found`() = runTest {
        // Given
        val recipe = Recipe(
            id = 1,
            title = "Exotic Recipe",
            subTitle = "With rare ingredient",
            preparationTime = "5 min",
            totalCookingTime = "10 min",
            tags = Tags(),
            ingredients = listOf(Ingredient("Dragon Fruit", 1.0, "piece")),
            steps = listOf("Eat dragon fruit")
        )
        val newFood = Food(id = 99, name = "Dragon Fruit", link = null, category = "Autre", unit = listOf("piece"), caloriesPerKg = 0, caloriesPerUnit = 0)

        coEvery { cookbookRepository.getCookbookByName("RecipeShoplist") } returns null
        coEvery { foodRepository.findByName("Dragon Fruit") } returns null
        coEvery { foodRepository.findByNameContaining("dragon fruit") } returns null
        coEvery { foodRepository.insert(any()) } returns 99L
        coEvery { foodRepository.findById(99) } returns newFood
        coEvery { shoplistRepository.findByFoodId(99) } returns null
        coEvery { shoplistRepository.insertShoplist(any()) } just Runs

        // When
        recipeToShoplistService.addRecipeToShoplist(recipe, 2)

        // Then
        coVerify { foodRepository.insert(match { it.name == "Dragon Fruit" && it.category == "Autre" }) }
        coVerify { shoplistRepository.insertShoplist(match { it.foodId == 99 && it.quantity == 2 }) } // 1 * 2 people
    }

    @Test
    fun `addRecipeToShoplist handles null quantity with default value`() = runTest {
        // Given
        val recipe = Recipe(
            id = 1,
            title = "Simple Recipe",
            subTitle = "",
            preparationTime = "5 min",
            totalCookingTime = "5 min",
            tags = Tags(),
            ingredients = listOf(Ingredient("Salt", null, "pinch")),
            steps = listOf("Add salt")
        )
        val saltFood = Food(id = 3, name = "Salt", category = "Seasoning", unit = listOf("pinch"), caloriesPerKg = 0, caloriesPerUnit = 0, link = "url")

        coEvery { cookbookRepository.getCookbookByName("RecipeShoplist") } returns null
        coEvery { foodRepository.findByName("Salt") } returns saltFood
        coEvery { shoplistRepository.findByFoodId(3) } returns null
        coEvery { shoplistRepository.insertShoplist(any()) } just Runs

        // When
        recipeToShoplistService.addRecipeToShoplist(recipe, 2)

        // Then
        coVerify { shoplistRepository.insertShoplist(match { it.quantity == 2 && it.quantityType == "pinch" }) } // (null becomes 1) * 2 people
    }

    @Test
    fun `addRecipeToShoplist handles empty unit with default`() = runTest {
        // Given
        val recipe = Recipe(
            id = 1,
            title = "Test Recipe",
            subTitle = "",
            preparationTime = "1 min",
            totalCookingTime = "1 min",
            tags = Tags(),
            ingredients = listOf(Ingredient("Mystery Item", 5.0, "")),
            steps = listOf("Use mystery item")
        )
        val mysteryFood = Food(id = 4, name = "Mystery Item", category = "Autre", unit = listOf(""), caloriesPerKg = 0, caloriesPerUnit = 0, link = "url")

        coEvery { cookbookRepository.getCookbookByName("RecipeShoplist") } returns null
        coEvery { foodRepository.findByName("Mystery Item") } returns mysteryFood
        coEvery { shoplistRepository.findByFoodId(4) } returns null
        coEvery { shoplistRepository.insertShoplist(any()) } just Runs

        // When
        recipeToShoplistService.addRecipeToShoplist(recipe, 2)

        // Then
        coVerify { shoplistRepository.insertShoplist(match { it.quantity == 10 && it.quantityType == "unité" }) } // 5 * 2 people
    }

    @Test
    fun `addRecipeToShoplist finds food by name containing when exact match fails`() = runTest {
        // Given
        val recipe = Recipe(
            id = 1,
            title = "Test Recipe",
            subTitle = "",
            preparationTime = "1 min",
            totalCookingTime = "1 min",
            tags = Tags(),
            ingredients = listOf(Ingredient("Red Apples", 2.0, "pieces")),
            steps = listOf("Use apples")
        )
        val appleFood = Food(id = 5, name = "Apple", link = "https://example.com/apple", category = "Fruit", unit = listOf("pieces"), caloriesPerKg = 520, caloriesPerUnit = 95)

        coEvery { cookbookRepository.getCookbookByName("RecipeShoplist") } returns null
        coEvery { foodRepository.findByName("Red Apples") } returns null
        coEvery { foodRepository.findByNameContaining("red apples") } returns appleFood
        coEvery { shoplistRepository.findByFoodId(5) } returns null
        coEvery { shoplistRepository.insertShoplist(any()) } just Runs

        // When
        recipeToShoplistService.addRecipeToShoplist(recipe, 2)

        // Then
        coVerify { foodRepository.findByName("Red Apples") }
        coVerify { foodRepository.findByNameContaining("red apples") }
        coVerify { shoplistRepository.insertShoplist(match { it.foodId == 5 && it.quantity == 4 }) } // 2 * 2 people
        coVerify(exactly = 0) { foodRepository.insert(any()) }
    }
}