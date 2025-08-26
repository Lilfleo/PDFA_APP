package com.pdfa.pdfa_app.data.dao

import androidx.room.*
import com.pdfa.pdfa_app.data.model.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CookbookDao {

    @Insert
    suspend fun insertCookbook(cookbook: Cookbook): Long

    @Update
    suspend fun updateCookbook(cookbook: Cookbook)

    @Delete
    suspend fun deleteCookbook(cookbook: Cookbook)

    @Query("SELECT * FROM cookbook WHERE isInternal = 0 ORDER BY name ASC")
    fun getAllUserCookbooks(): Flow<List<Cookbook>>

    @Query("SELECT COUNT(*) FROM cookbook_recipe_cross_ref WHERE cookbookId = :cookbookId")
    suspend fun getRecipeCountInCookbook(cookbookId: Long): Int

    @Query("SELECT * FROM cookbook WHERE id = :id")
    suspend fun getCookbookById(id: Int): Cookbook?

    @Query("SELECT * FROM cookbook WHERE name = :name LIMIT 1")
    suspend fun getCookbookByName(name: String): Cookbook?

    // Récupérer les cookbooks par type
    @Query("SELECT * FROM cookbook WHERE isInternal = :isInternal ORDER BY name ASC")
    suspend fun getCookbooksByType(isInternal: Boolean): List<Cookbook>

    @Query("SELECT * FROM cookbook WHERE isInternal = :isInternal ORDER BY name ASC")
    fun getCookbooksByTypeFlow(isInternal: Boolean): Flow<List<Cookbook>>

    // Relations avec recettes
    @Transaction
    @Query("SELECT * FROM cookbook WHERE id = :cookbookId")
    suspend fun getCookbookWithRecipes(cookbookId: Int): CookbookWithRecipes?

    @Transaction
    @Query("SELECT * FROM cookbook WHERE name = :name LIMIT 1")
    suspend fun getCookbookWithRecipesByName(name: String): CookbookWithRecipes?

    @Transaction
    @Query("SELECT * FROM cookbook WHERE isInternal = :isInternal ORDER BY name ASC")
    suspend fun getCookbooksWithRecipesByType(isInternal: Boolean): List<CookbookWithRecipes>

    @Transaction
    @Query("SELECT * FROM cookbook WHERE isInternal = :isInternal ORDER BY name ASC")
    fun getCookbooksWithRecipesByTypeFlow(isInternal: Boolean): Flow<List<CookbookWithRecipes>>

    // Gestion des relations Many-to-Many
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCookbookRecipeCrossRef(crossRef: CookbookRecipeCrossRef)

    @Delete
    suspend fun deleteCookbookRecipeCrossRef(crossRef: CookbookRecipeCrossRef)

    @Query("DELETE FROM cookbook_recipe_cross_ref WHERE cookbookId = :cookbookId AND recipeId = :recipeId")
    suspend fun removeRecipeFromCookbook(cookbookId: Int, recipeId: Int)

    @Query("SELECT COUNT(*) FROM cookbook_recipe_cross_ref WHERE cookbookId = :cookbookId AND recipeId = :recipeId")
    suspend fun isRecipeInCookbook(cookbookId: Int, recipeId: Int): Int
}
