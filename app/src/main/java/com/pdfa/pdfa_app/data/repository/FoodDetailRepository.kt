package com.pdfa.pdfa_app.data.repository

import android.util.Log
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

    suspend fun insert(foodDetail: FoodDetail) {
        dao.insertFoodDetail(foodDetail)
    }

    suspend fun update(foodDetail: FoodDetail) {
        dao.updateFoodDetail(foodDetail)
    }

    suspend fun upsertFoodDetail(detail: FoodDetail) {
        val existing = dao.getByFoodId(detail.foodId)
        if (existing != null) {
            Log.d("FoodDetailRepo", "🔁 UPDATE existant : id=${existing.id}")
            dao.updateFoodDetail(detail.copy(id = existing.id))
        } else {
            Log.d("FoodDetailRepo", "🆕 INSERT nouveau detail")
            dao.insertFoodDetail(detail)
        }
    }

    suspend fun delete(foodDetail: FoodDetail) {
        dao.deleteFoodDetail(foodDetail)
    }

    suspend fun getByFoodId(foodId: Int): FoodDetail? {
        return dao.getByFoodId(foodId)
    }



}





    // Upsert inserts the FoodDetail if its ID is 0 (i.e. new), or updates the existing row if the ID matches an existing entry.
    // Returns the inserted or updated row ID.

