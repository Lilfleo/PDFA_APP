package com.pdfa.pdfa_app.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import androidx.room.Upsert
import com.pdfa.pdfa_app.data.model.FoodDetail
import com.pdfa.pdfa_app.data.model.FoodDetailWithFood
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDetailDao {
    @Insert
    suspend fun insertFoodDetail(foodDetail: FoodDetail)

    @Transaction
    @Query("SELECT * FROM food_detail")
    fun getFoodDetails(): Flow<List<FoodDetailWithFood>>

    @Transaction
    @Query("SELECT * FROM food_detail WHERE food_id = :foodId")
    fun getFoodDetail(foodId: Int): Flow<FoodDetailWithFood>

    @Update
    suspend fun updateFoodDetail(foodDetail: FoodDetail)

    @Upsert
    suspend fun upsertFoodDetail(foodDetail: FoodDetail): Long

    @Delete
    suspend fun deleteFoodDetail(foodDetail: FoodDetail)
}

