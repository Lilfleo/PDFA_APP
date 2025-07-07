package com.pdfa.pdfa_app.data.repository

import com.pdfa.pdfa_app.data.dao.FoodDetailDao
import com.pdfa.pdfa_app.data.model.FoodDetail
import com.pdfa.pdfa_app.data.model.FoodDetailWithFood
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FoodDetailRepository @Inject constructor(
    private val dao: FoodDetailDao
) {
    val allFoodDetail : Flow<List<FoodDetailWithFood>> = dao.getFoodDetails()

    fun getFoodDetail(foodId: Int): Flow<FoodDetailWithFood> {
        return dao.getFoodDetail(foodId)
    }

    suspend fun insert(allergy: FoodDetail) {
        dao.insertFoodDetail(allergy)
    }
}
