package com.pdfa.pdfa_app.data.dao

import androidx.room.*
import com.pdfa.pdfa_app.data.model.Recipe
import com.pdfa.pdfa_app.data.model.RecipeWithTags

@Dao
interface RecipeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: Recipe): Long

    @Transaction
    @Query("SELECT * FROM recipe WHERE id = :recipeId")
    suspend fun getRecipeWithTags(recipeId: Int): RecipeWithTags
}

