package com.pdfa.pdfa_app.data.repository

import com.pdfa.pdfa_app.data.dao.FoodDao
import com.pdfa.pdfa_app.data.model.Food
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
import java.util.*

@ExperimentalCoroutinesApi
class FoodRepositoryTest {

    private lateinit var foodDao: FoodDao
    private lateinit var foodRepository: FoodRepository
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        foodDao = mockk(name = "foodDao", relaxed = true)
        foodRepository = FoodRepository(foodDao)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearMocks(foodDao)
    }

    @Test
    fun `getAllFood returns flow of food list from dao`() = runTest {
        // Given
        val expectedFoodList = flowOf(listOf(
            Food(
                id = 1,
                name = "Apple",
                link = "https://example.com/apple",
                category = "Fruit",
                unit = listOf("piece", "kg"),
                caloriesPerKg = 520,
                caloriesPerUnit = 95
            ),
            Food(
                id = 2,
                name = "Banana",
                link = null,
                category = "Fruit",
                unit = listOf("piece"),
                caloriesPerKg = 890,
                caloriesPerUnit = 105
            )
        ))
        clearAllMocks()
        foodDao = mockk()

        every { foodDao.getAllFood() } returns expectedFoodList
        foodRepository = FoodRepository(foodDao)
        // When
        val result = foodRepository.allFood

        // Then
        assertEquals(expectedFoodList.first(), result.firstOrNull())
        verify { foodDao.getAllFood() }
    }

    @Test
    fun `getAllFood returns empty flow when dao returns empty list`() = runTest {
        // Given
        val emptyList = emptyList<Food>()
        clearMocks(foodDao)
        every { foodDao.getAllFood() } returns flowOf(emptyList)
        foodRepository = FoodRepository(foodDao)
        // When
        val result = foodRepository.allFood.firstOrNull()

        // Then
        assertTrue(result.isNullOrEmpty())
        verify { foodDao.getAllFood() }
    }

    @Test
    fun `insert food returns id from dao`() = runTest {
        // Given
        val food = Food(
            name = "Orange",
            link = "https://example.com/orange",
            category = "Fruit",
            unit = listOf("piece", "kg"),
            caloriesPerKg = 470,
            caloriesPerUnit = 60
        )
        val expectedId = 123L
        coEvery { foodDao.insertFood(food) } returns expectedId

        // When
        val result = foodRepository.insert(food)

        // Then
        assertEquals(expectedId, result)
        coVerify { foodDao.insertFood(food) }
    }

    @Test
    fun `insert food with zero calories`() = runTest {
        // Given
        val food = Food(
            name = "Water",
            link = null,
            category = "Beverage",
            unit = listOf("ml", "liter"),
            caloriesPerKg = 0,
            caloriesPerUnit = 0
        )
        val expectedId = 456L
        coEvery { foodDao.insertFood(food) } returns expectedId

        // When
        val result = foodRepository.insert(food)

        // Then
        assertEquals(expectedId, result)
        coVerify { foodDao.insertFood(food) }
    }

    @Test
    fun `insert food with null calories`() = runTest {
        // Given
        val food = Food(
            name = "Unknown Food",
            link = null,
            category = "Unknown",
            unit = listOf("piece"),
            caloriesPerKg = null,
            caloriesPerUnit = null
        )
        val expectedId = 789L
        coEvery { foodDao.insertFood(food) } returns expectedId

        // When
        val result = foodRepository.insert(food)

        // Then
        assertEquals(expectedId, result)
        coVerify { foodDao.insertFood(food) }
    }

    @Test
    fun `delete food calls dao delete`() = runTest {
        // Given
        val food = Food(
            id = 1,
            name = "Apple",
            link = "https://example.com/apple",
            category = "Fruit",
            unit = listOf("piece"),
            caloriesPerKg = 520,
            caloriesPerUnit = 95
        )
        coEvery { foodDao.deleteFood(food) } just Runs

        // When
        foodRepository.delete(food)

        // Then
        coVerify { foodDao.deleteFood(food) }
    }

    @Test
    fun `findByName returns food when found`() = runTest {
        // Given
        val foodName = "Apple"
        val expectedFood = Food(
            id = 1,
            name = foodName,
            link = "https://example.com/apple",
            category = "Fruit",
            unit = listOf("piece", "kg"),
            caloriesPerKg = 520,
            caloriesPerUnit = 95
        )
        coEvery { foodDao.findByName(foodName) } returns expectedFood

        // When
        val result = foodRepository.findByName(foodName)

        // Then
        assertEquals(expectedFood, result)
        coVerify { foodDao.findByName(foodName) }
    }

    @Test
    fun `findByName returns null when not found`() = runTest {
        // Given
        val foodName = "NonExistentFood"
        coEvery { foodDao.findByName(foodName) } returns null

        // When
        val result = foodRepository.findByName(foodName)

        // Then
        assertNull(result)
        coVerify { foodDao.findByName(foodName) }
    }

    @Test
    fun `findByName with empty string`() = runTest {
        // Given
        val emptyName = ""
        coEvery { foodDao.findByName(emptyName) } returns null

        // When
        val result = foodRepository.findByName(emptyName)

        // Then
        assertNull(result)
        coVerify { foodDao.findByName(emptyName) }
    }

    @Test
    fun `findByNameContaining returns food when partial match found`() = runTest {
        // Given
        val partialName = "App"
        val expectedFood = Food(
            id = 1,
            name = "Apple",
            link = "https://example.com/apple",
            category = "Fruit",
            unit = listOf("piece"),
            caloriesPerKg = 520,
            caloriesPerUnit = 95
        )
        coEvery { foodDao.findByNameContaining(partialName) } returns expectedFood

        // When
        val result = foodRepository.findByNameContaining(partialName)

        // Then
        assertEquals(expectedFood, result)
        coVerify { foodDao.findByNameContaining(partialName) }
    }

    @Test
    fun `findByNameContaining returns null when no partial match found`() = runTest {
        // Given
        val partialName = "xyz"
        coEvery { foodDao.findByNameContaining(partialName) } returns null

        // When
        val result = foodRepository.findByNameContaining(partialName)

        // Then
        assertNull(result)
        coVerify { foodDao.findByNameContaining(partialName) }
    }

    @Test
    fun `findByNameContaining with empty string`() = runTest {
        // Given
        val emptyName = ""
        coEvery { foodDao.findByNameContaining(emptyName) } returns null

        // When
        val result = foodRepository.findByNameContaining(emptyName)

        // Then
        assertNull(result)
        coVerify { foodDao.findByNameContaining(emptyName) }
    }

    @Test
    fun `findById returns food when found`() = runTest {
        // Given
        val foodId = 1
        val expectedFood = Food(
            id = foodId,
            name = "Apple",
            link = "https://example.com/apple",
            category = "Fruit",
            unit = listOf("piece", "kg"),
            caloriesPerKg = 520,
            caloriesPerUnit = 95
        )
        coEvery { foodDao.findById(foodId) } returns expectedFood

        // When
        val result = foodRepository.findById(foodId)

        // Then
        assertEquals(expectedFood, result)
        coVerify { foodDao.findById(foodId) }
    }

    @Test
    fun `findById returns null when not found`() = runTest {
        // Given
        val foodId = 999
        coEvery { foodDao.findById(foodId) } returns null

        // When
        val result = foodRepository.findById(foodId)

        // Then
        assertNull(result)
        coVerify { foodDao.findById(foodId) }
    }

    @Test
    fun `findById with negative id`() = runTest {
        // Given
        val negativeId = -1
        coEvery { foodDao.findById(negativeId) } returns null

        // When
        val result = foodRepository.findById(negativeId)

        // Then
        assertNull(result)
        coVerify { foodDao.findById(negativeId) }
    }

    @Test
    fun `findById with zero id`() = runTest {
        // Given
        val zeroId = 0
        coEvery { foodDao.findById(zeroId) } returns null

        // When
        val result = foodRepository.findById(zeroId)

        // Then
        assertNull(result)
        coVerify { foodDao.findById(zeroId) }
    }

    @Test
    fun `multiple operations work correctly`() = runTest {
        // Given
        val food1 = Food(
            name = "Apple",
            link = "https://example.com/apple",
            category = "Fruit",
            unit = listOf("piece"),
            caloriesPerKg = 520,
            caloriesPerUnit = 95
        )
        val food2 = Food(
            id = 1,
            name = "Apple",
            link = "https://example.com/apple",
            category = "Fruit",
            unit = listOf("piece"),
            caloriesPerKg = 520,
            caloriesPerUnit = 95
        )
        val insertId = 1L

        coEvery { foodDao.insertFood(food1) } returns insertId
        coEvery { foodDao.findById(1) } returns food2
        coEvery { foodDao.deleteFood(food2) } just Runs

        // When & Then
        val id = foodRepository.insert(food1)
        assertEquals(insertId, id)

        val foundFood = foodRepository.findById(1)
        assertEquals(food2, foundFood)

        foodRepository.delete(food2)

        // Verify all calls
        coVerify { foodDao.insertFood(food1) }
        coVerify { foodDao.findById(1) }
        coVerify { foodDao.deleteFood(food2) }
    }

    @Test
    fun `allFood property delegates to dao getAllFood`() = runTest {
        // Given
        val foodList = listOf(
            Food(
                id = 1,
                name = "Apple",
                link = "https://example.com/apple",
                category = "Fruit",
                unit = listOf("piece", "kg"),
                caloriesPerKg = 520,
                caloriesPerUnit = 95
            ),
            Food(
                id = 2,
                name = "Banana",
                link = null,
                category = "Fruit",
                unit = listOf("piece"),
                caloriesPerKg = 890,
                caloriesPerUnit = 105
            ),
            Food(
                id = 3,
                name = "Chicken Breast",
                link = "https://example.com/chicken",
                category = "Protein",
                unit = listOf("gram", "kg"),
                caloriesPerKg = 1650,
                caloriesPerUnit = null
            )
        )
        // Clear any existing mock behavior and set new one
        clearMocks(foodDao)
        every { foodDao.getAllFood() } returns flowOf(foodList)
        foodRepository = FoodRepository(foodDao)
        // When
        val flow = foodRepository.allFood
        val result = flow.first()

        // Then
        assertEquals(foodList, result)
        assertEquals(3, result.size)
        verify { foodDao.getAllFood() }
    }

    @Test
    fun `insert food with expiration date`() = runTest {
        // Given
        val expirationDate = Date()
        val food = Food(
            name = "Milk",
            link = null,
            category = "Dairy",
            unit = listOf("ml", "liter"),
            caloriesPerKg = 420,
            caloriesPerUnit = null,
            expirationTime = expirationDate
        )
        val expectedId = 999L
        coEvery { foodDao.insertFood(food) } returns expectedId

        // When
        val result = foodRepository.insert(food)

        // Then
        assertEquals(expectedId, result)
        coVerify { foodDao.insertFood(food) }
    }

    @Test
    fun `findByName with special characters in name`() = runTest {
        // Given
        val foodName = "Café au Lait"
        val expectedFood = Food(
            id = 5,
            name = foodName,
            link = null,
            category = "Beverage",
            unit = listOf("cup", "ml"),
            caloriesPerKg = null,
            caloriesPerUnit = 180
        )
        coEvery { foodDao.findByName(foodName) } returns expectedFood

        // When
        val result = foodRepository.findByName(foodName)

        // Then
        assertEquals(expectedFood, result)
        coVerify { foodDao.findByName(foodName) }
    }
}