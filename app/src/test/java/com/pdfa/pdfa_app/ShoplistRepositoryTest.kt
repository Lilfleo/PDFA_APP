package com.pdfa.pdfa_app.data.repository

import com.pdfa.pdfa_app.data.dao.ShoplistDao
import com.pdfa.pdfa_app.data.model.Shoplist
import com.pdfa.pdfa_app.data.model.ShoplistWithFood
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

@ExperimentalCoroutinesApi
class ShoplistRepositoryTest {

    private lateinit var shoplistDao: ShoplistDao
    private lateinit var shoplistRepository: ShoplistRepository
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        shoplistDao = mockk(relaxed = true)
        shoplistRepository = ShoplistRepository(shoplistDao)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearMocks(shoplistDao)
    }

    @Test
    fun `allFoodDetail returns flow of shoplist with food from dao`() = runTest {
        // Given
        val food1 = Food(id = 1, name = "Apples", link = "https://example.com/apple", category = "Fruit", unit = listOf("pieces"), caloriesPerKg = 520, caloriesPerUnit = 95)
        val food2 = Food(id = 2, name = "Bread", link = null, category = "Bakery", unit = listOf("loaf"), caloriesPerKg = 2650, caloriesPerUnit = 250)
        val expectedShoplist = listOf(
            ShoplistWithFood(
                shoplist = Shoplist(id = 1, foodId = 1, quantity = 5, quantityType = "pieces", recipeId = listOf()),
                food = food1
            ),
            ShoplistWithFood(
                shoplist = Shoplist(id = 2, foodId = 2, quantity = 1, quantityType = "loaf", recipeId = listOf()),
                food = food2
            )
        )
        clearAllMocks()
        every { shoplistDao.getShoplist() } returns flowOf(expectedShoplist)
         shoplistRepository = ShoplistRepository(shoplistDao)
        // When
        val result = shoplistRepository.allFoodDetail.first()

        // Then
        assertEquals(expectedShoplist, result)
        assertEquals(2, result.size)
        verify { shoplistDao.getShoplist() }
    }

    @Test
    fun `allFoodDetail returns empty list when dao returns empty`() = runTest {
        // Given
        every { shoplistDao.getShoplist() } returns flowOf(emptyList())

        // When
        val result = shoplistRepository.allFoodDetail.firstOrNull()

        // Then
        assertTrue(result.isNullOrEmpty())
        verify { shoplistDao.getShoplist() }
    }

    @Test
    fun `insertShoplist calls dao insertSholist`() = runTest {
        // Given
        val shoplist = Shoplist(foodId = 1, quantity = 3, quantityType = "kg", recipeId = listOf())
        coEvery { shoplistDao.insertSholist(shoplist) } just Runs

        // When
        shoplistRepository.insertShoplist(shoplist)

        // Then
        coVerify { shoplistDao.insertSholist(shoplist) }
    }

    @Test
    fun `updateShoplist calls dao updateShoplist`() = runTest {
        // Given
        val shoplist = Shoplist(id = 1, foodId = 1, quantity = 5, quantityType = "pieces", recipeId = listOf())
        coEvery { shoplistDao.updateShoplist(shoplist) } just Runs

        // When
        shoplistRepository.updateShoplist(shoplist)

        // Then
        coVerify { shoplistDao.updateShoplist(shoplist) }
    }

    @Test
    fun `deleteShoplist calls dao deleteShoplist`() = runTest {
        // Given
        val shoplist = Shoplist(id = 1, foodId = 1, quantity = 2, quantityType = "bottles", recipeId = listOf())
        coEvery { shoplistDao.deleteShoplist(shoplist) } just Runs

        // When
        shoplistRepository.deleteShoplist(shoplist)

        // Then
        coVerify { shoplistDao.deleteShoplist(shoplist) }
    }

    @Test
    fun `findByFoodId returns shoplist when found`() = runTest {
        // Given
        val foodId = 1
        val expectedShoplist = Shoplist(id = 1, foodId = foodId, quantity = 2, quantityType = "kg", recipeId = listOf())
        coEvery { shoplistDao.findByFoodId(foodId) } returns expectedShoplist

        // When
        val result = shoplistRepository.findByFoodId(foodId)

        // Then
        assertEquals(expectedShoplist, result)
        coVerify { shoplistDao.findByFoodId(foodId) }
    }

    @Test
    fun `findByFoodId returns null when not found`() = runTest {
        // Given
        val foodId = 999
        coEvery { shoplistDao.findByFoodId(foodId) } returns null

        // When
        val result = shoplistRepository.findByFoodId(foodId)

        // Then
        assertNull(result)
        coVerify { shoplistDao.findByFoodId(foodId) }
    }

    @Test
    fun `multiple operations work correctly`() = runTest {
        // Given
        val shoplist = Shoplist(foodId = 1, quantity = 2, quantityType = "pieces", recipeId = listOf())
        val insertedShoplist = Shoplist(id = 1, foodId = 1, quantity = 2, quantityType = "pieces", recipeId = listOf())
        val updatedShoplist = insertedShoplist.copy(quantity = 5)

        coEvery { shoplistDao.insertSholist(shoplist) } just Runs
        coEvery { shoplistDao.findByFoodId(1) } returns insertedShoplist
        coEvery { shoplistDao.updateShoplist(updatedShoplist) } just Runs
        coEvery { shoplistDao.deleteShoplist(updatedShoplist) } just Runs

        // When & Then
        shoplistRepository.insertShoplist(shoplist)

        val found = shoplistRepository.findByFoodId(1)
        assertEquals(insertedShoplist, found)

        shoplistRepository.updateShoplist(updatedShoplist)
        shoplistRepository.deleteShoplist(updatedShoplist)

        // Verify all calls
        coVerify { shoplistDao.insertSholist(shoplist) }
        coVerify { shoplistDao.findByFoodId(1) }
        coVerify { shoplistDao.updateShoplist(updatedShoplist) }
        coVerify { shoplistDao.deleteShoplist(updatedShoplist) }
    }
}