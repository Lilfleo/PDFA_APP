package com.pdfa.pdfa_app.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pdfa.pdfa_app.data.model.RecipeTagCrossRef

@Dao
interface RecipeTagCrossRefDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipeTag(recipeTag: RecipeTagCrossRef)

    @Delete
    suspend fun deleteRecipeTag(recipeTag: RecipeTagCrossRef)

    @Query("DELETE FROM recipe_tag WHERE recipe_id = :recipeId")
    suspend fun deleteRecipeTagsForRecipe(recipeId: Int)

    @Query("SELECT * FROM recipe_tag WHERE recipe_id = :recipeId")
    suspend fun getRecipeTagsForRecipe(recipeId: Int): List<RecipeTagCrossRef>
}
