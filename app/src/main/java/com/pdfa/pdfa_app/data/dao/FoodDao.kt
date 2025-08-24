package com.pdfa.pdfa_app.data.dao

import androidx.room.*
import com.pdfa.pdfa_app.data.model.Food
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFood(food: Food): Long

    @Query("SELECT * FROM food ORDER BY expirationTime ASC")
    fun getAllFood(): Flow<List<Food>>

    @Delete
    suspend fun deleteFood(food: Food)

    @Query("SELECT * FROM food WHERE name = :name LIMIT 1")
    suspend fun findByName(name: String): Food?

    @Query("SELECT * FROM food WHERE name LIKE '%' || :name || '%' LIMIT 1")
    suspend fun findByNameContaining(name: String): Food?

    @Query("SELECT * FROM food WHERE id = :id LIMIT 1")
    suspend fun findById(id: Int): Food?
}