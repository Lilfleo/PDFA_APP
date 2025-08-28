//package com.pdfa.pdfa_app
//
//import com.pdfa.pdfa_app.data.dao.FoodDao
//import com.pdfa.pdfa_app.data.model.Food
//import com.pdfa.pdfa_app.data.repository.FoodRepository
//import io.mockk.*
//import kotlinx.coroutines.ExperimentalCoroutinesApi
//import kotlinx.coroutines.flow.first
//import kotlinx.coroutines.flow.flowOf
//import kotlinx.coroutines.test.*
//import org.junit.After
//import org.junit.Before
//import org.junit.Test
//import java.util.*
//
//@OptIn(ExperimentalCoroutinesApi::class)
//class FoodRepositoryTest {
//
//    private lateinit var foodDao: FoodDao
//    private lateinit var repository: FoodRepository
//
//    private val testDispatcher = StandardTestDispatcher()
//
//    @Before
//    fun setup() {
//        MockKAnnotations.init(this)
//        foodDao = mockk(relaxed = true)
//    }
//
//    @After
//    fun teardown() {
//        clearAllMocks()
//    }
//
//    @Test
//    fun `insert calls insertFood on dao`() = runTest(testDispatcher) {
//        val food = sampleFood()
//        repository = FoodRepository(foodDao)
//
//        coEvery { foodDao.insertFood(food) } returns 1L
//
//        repository.insert(food)
//
//        coVerify { foodDao.insertFood(food) }
//    }
//
//    @Test
//    fun `delete calls deleteFood on dao`() = runTest(testDispatcher) {
//        val food = sampleFood()
//        repository = FoodRepository(foodDao)
//
//        coEvery { foodDao.deleteFood(food) } just Runs
//
//        repository.delete(food)
//
//        coVerify { foodDao.deleteFood(food) }
//    }
//
//    @Test
//    fun `allFood emits data from dao`() = runTest(testDispatcher) {
//        val fakeList = listOf(sampleFood())
//        every { foodDao.getAllFood() } returns flowOf(fakeList)
//
//        repository = FoodRepository(foodDao)
//
//        val result = repository.allFood.first()
//
//        assert(result == fakeList)
//
//        verify { foodDao.getAllFood() }
//    }
//
//    private fun sampleFood(): Food = Food(
//        id = 1,
//        name = "Apple",
//        link = "https://example.com/apple",
//        caloriesPerKg = 520,
//        caloriesPerUnit = 80,
//        expirationTime = Date()
//    )
//}
