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

    @Query("SELECT * FROM food_detail WHERE id = :id")
    suspend fun getById(id: Int): FoodDetail?

    @Query("SELECT * FROM food_detail WHERE food_id = :foodId")
    suspend fun getByFoodId(foodId: Int): FoodDetail?

    @Transaction
    @Query("SELECT * FROM food_detail WHERE food_id = :foodId")
    fun getFoodDetail(foodId: Int): Flow<FoodDetailWithFood>

    @Query("""
        SELECT * FROM food_detail fd 
        JOIN food f ON fd.food_id = f.id 
        WHERE LOWER(f.name) = LOWER(:foodName)
        """)
    suspend fun getFoodByName(foodName: String): FoodDetailWithFood?

    @Query("DELETE FROM food_detail WHERE id = :id")
    suspend fun deleteFoodDetail(id: Int)

    @Update
    suspend fun updateFoodDetail(foodDetail: FoodDetail)

    @Upsert
    suspend fun upsertFoodDetail(foodDetail: FoodDetail)

    @Delete
    suspend fun deleteFoodDetail(foodDetail: FoodDetail)




}

