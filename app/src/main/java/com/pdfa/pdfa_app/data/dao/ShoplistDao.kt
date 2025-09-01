package com.pdfa.pdfa_app.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import androidx.room.Upsert
import com.pdfa.pdfa_app.data.model.Shoplist
import com.pdfa.pdfa_app.data.model.ShoplistWithFood
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoplistDao {
    @Transaction
    @Query("SELECT * FROM shoplist")
    fun getShoplist(): Flow<List<ShoplistWithFood>>

    @Query("SELECT * FROM shoplist WHERE food_id = :foodId LIMIT 1")
    suspend fun findByFoodId(foodId: Int): Shoplist?

    @Insert
    suspend fun insertSholist(shoplist: Shoplist)

    @Update
    suspend fun updateShoplist(shoplist: Shoplist)

    @Upsert
    suspend fun upsertShoplist(shoplist: Shoplist)

    @Delete
    suspend fun deleteShoplist(shoplist: Shoplist)

    @Query("SELECT * FROM shoplist WHERE recipeId = :recipeId")
    suspend fun findByRecipeId(recipeId: Int): Shoplist?
}