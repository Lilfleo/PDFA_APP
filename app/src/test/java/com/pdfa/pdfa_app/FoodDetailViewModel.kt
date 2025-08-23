package com.pdfa.pdfa_app

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.pdfa.pdfa_app.data.model.FoodDetail
import com.pdfa.pdfa_app.data.model.FoodDetailWithFood
import com.pdfa.pdfa_app.data.repository.FoodDetailRepository
import com.pdfa.pdfa_app.ui.viewmodel.FoodDetailViewModel
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.*
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class FoodDetailViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var repository: FoodDetailRepository
    private lateinit var viewModel: FoodDetailViewModel
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        repository = mockk(relaxed = true)

        // Mock du flow par défaut
        every { repository.allFoodDetail } returns flowOf(emptyList())
    }

    @After
    fun teardown() {
        clearAllMocks()
    }

    @Test
    fun `when viewModel created, then foodDetail starts empty`() = runTest(testDispatcher) {
        // Given
        every { repository.allFoodDetail } returns flowOf(emptyList())

        // When
        viewModel = FoodDetailViewModel(repository)

        // Then
        assertEquals(emptyList(), viewModel.foodDetail.value)
    }

    @Test
    fun `when addFoodDetail called, then repository insert called`() = runTest(testDispatcher) {
        // Given
        viewModel = FoodDetailViewModel(repository)
        val foodDetail = sampleFoodDetail()
        coEvery { repository.insert(foodDetail) } just Runs

        // When
        viewModel.addFoodDetail(foodDetail)

        // Then
        coVerify { repository.insert(foodDetail) }
    }

    @Test
    fun `when updateFoodDetail called, then repository update called`() = runTest(testDispatcher) {
        // Given
        viewModel = FoodDetailViewModel(repository)
        val foodDetail = sampleFoodDetail()
        coEvery { repository.update(foodDetail) } just Runs

        // When
        viewModel.updateFoodDetail(foodDetail)

        // Then
        coVerify { repository.update(foodDetail) }
    }

    @Test
    fun `when upsertFoodDetail called, then repository upsert called`() = runTest(testDispatcher) {
        // Given
        viewModel = FoodDetailViewModel(repository)
        val foodDetail = sampleFoodDetail()
        coEvery { repository.upsertFoodDetail(foodDetail) } just Runs

        // When
        viewModel.upsertFoodDetail(foodDetail)

        // Then
        coVerify { repository.upsertFoodDetail(foodDetail) }
    }

    @Test
    fun `when deleteFoodDetail called, then repository delete called`() = runTest(testDispatcher) {
        // Given
        viewModel = FoodDetailViewModel(repository)
        val foodDetail = sampleFoodDetail()
        coEvery { repository.delete(foodDetail) } just Runs

        // When
        viewModel.deleteFoodDetail(foodDetail)

        // Then
        coVerify { repository.delete(foodDetail) }
    }

    @Test
    fun `when getFoodDetail called, then returns repository flow`() = runTest(testDispatcher) {
        // Given
        viewModel = FoodDetailViewModel(repository)
        val foodId = 1
        val expectedFlow = flowOf(mockk<FoodDetailWithFood>(relaxed = true))
        every { repository.getFoodDetail(foodId) } returns expectedFlow

        // When
        val result = viewModel.getFoodDetail(foodId).first()

        // Then
        verify { repository.getFoodDetail(foodId) }
    }

    @Test
    fun `when getByFoodId called, then repository returns correct detail`() = runTest(testDispatcher) {
        // Given
        viewModel = FoodDetailViewModel(repository)
        val foodId = 1
        val expectedDetail = sampleFoodDetail()
        coEvery { repository.getByFoodId(foodId) } returns expectedDetail

        // When
        val result = viewModel.getByFoodId(foodId)

        // Then
        coVerify { repository.getByFoodId(foodId) }
        assertEquals(expectedDetail, result)
    }

    // ✅ CORRIGÉ avec tes vrais paramètres !
    private fun sampleFoodDetail(): FoodDetail = FoodDetail(
        id = 1,
        foodId = 1,
        quantity = 2,
        isWeight = false,
        price = 3.50f,
        buyingTime = Date(),
        expirationTime = Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000) // +7 jours
    )
}
