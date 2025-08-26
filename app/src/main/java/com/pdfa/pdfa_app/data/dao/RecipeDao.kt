package com.pdfa.pdfa_app.data.dao

import androidx.room.*
import com.pdfa.pdfa_app.data.model.Recipe
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipe")
    fun getAllRecipes(): Flow<List<Recipe>>

    @Query("SELECT * FROM recipe WHERE id = :id")
    suspend fun getRecipeById(id: Int): Recipe?

    @Insert
    suspend fun insertRecipe(recipe: Recipe): Long

    @Update
    suspend fun updateRecipe(recipe: Recipe)

    @Delete
    suspend fun deleteRecipe(recipe: Recipe)

    @Query("""
    SELECT r.* FROM recipe r 
    INNER JOIN cookbook_recipe_cross_ref cr ON r.id = cr.recipeId 
    INNER JOIN cookbook c ON cr.cookbookId = c.id
    WHERE c.name = :cookbookName 
""")
    fun getRecipesFromCookbookByName(cookbookName: String): Flow<List<Recipe>>


}

