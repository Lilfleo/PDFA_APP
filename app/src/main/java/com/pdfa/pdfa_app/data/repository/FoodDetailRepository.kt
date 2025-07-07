package com.pdfa.pdfa_app.data.repository

import com.pdfa.pdfa_app.data.dao.FoodDetailDao
import com.pdfa.pdfa_app.data.model.Food
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

    suspend fun update(foodDetail: FoodDetail) {
        dao.updateFoodDetail(foodDetail)
    }

    // Upsert inserts the FoodDetail if its ID is 0 (i.e. new), or updates the existing row if the ID matches an existing entry.
    // Returns the inserted or updated row ID.
    suspend fun upsert(foodDetail: FoodDetail): Long = dao.upsertFoodDetail(foodDetail)
}
