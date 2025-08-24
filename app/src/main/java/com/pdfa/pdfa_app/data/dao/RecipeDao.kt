package com.pdfa.pdfa_app.data.dao

import androidx.room.*
import com.pdfa.pdfa_app.data.model.Recipe
import com.pdfa.pdfa_app.data.model.RecipeWithFoodQuantities
import com.pdfa.pdfa_app.data.model.RecipeWithTags
import com.pdfa.pdfa_app.data.model.TagWithRecipes

@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipe")
    suspend fun getAllRecipes(): List<Recipe>

    @Query("SELECT * FROM recipe WHERE id = :id")
    suspend fun getRecipeById(id: Int): Recipe?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: Recipe): Long

    @Update
    suspend fun updateRecipe(recipe: Recipe)

    @Delete
    suspend fun deleteRecipe(recipe: Recipe)

    // Get recipe with its foods
    @Transaction
    @Query("SELECT * FROM recipe WHERE id = :recipeId")
    suspend fun getRecipeWithFoods(recipeId: Int): RecipeWithFoodQuantities?

    // Get recipe with food details (including quantities)
    @Transaction
    @Query("SELECT * FROM recipe WHERE id = :recipeId")
    suspend fun getRecipeWithFoodDetails(recipeId: Int): RecipeWithFoodQuantities?

    // Get recipe with tags
    @Transaction
    @Query("SELECT * FROM recipe WHERE id = :recipeId")
    suspend fun getRecipeWithTags(recipeId: Int): RecipeWithTags?

    // Get recipes for a specific food
    @Transaction
    @Query("SELECT * FROM recipe WHERE id IN (SELECT recipe_id FROM food_recipe WHERE food_id = :foodId)")
    suspend fun getRecipesForFood(foodId: Int): List<Recipe>
}

