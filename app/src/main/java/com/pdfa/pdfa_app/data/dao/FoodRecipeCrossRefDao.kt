package com.pdfa.pdfa_app.data.dao

import androidx.room.*
import com.pdfa.pdfa_app.data.model.FoodRecipeCrossRef

@Dao
interface FoodRecipeCrossRefDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFoodRecipe(foodRecipe: FoodRecipeCrossRef)

    @Delete
    suspend fun deleteFoodRecipe(foodRecipe: FoodRecipeCrossRef)

    @Query("DELETE FROM food_recipe WHERE recipe_id = :recipeId")
    suspend fun deleteFoodRecipesForRecipe(recipeId: Int)

    @Query("DELETE FROM food_recipe WHERE food_id = :foodId")
    suspend fun deleteFoodRecipesForFood(foodId: Int)

    @Query("SELECT * FROM food_recipe WHERE recipe_id = :recipeId")
    suspend fun getFoodRecipesForRecipe(recipeId: Int): List<FoodRecipeCrossRef>

    @Query("SELECT * FROM food_recipe WHERE food_id = :foodId")
    suspend fun getFoodRecipesForFood(foodId: Int): List<FoodRecipeCrossRef>

    @Query("UPDATE food_recipe SET food_quantity = :quantity WHERE recipe_id = :recipeId AND food_id = :foodId")
    suspend fun updateFoodQuantityInRecipe(recipeId: Int, foodId: Int, quantity: Int)
}