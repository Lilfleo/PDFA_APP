package com.pdfa.pdfa_app.data.repository

import com.pdfa.pdfa_app.data.dao.RecipeDao
import com.pdfa.pdfa_app.data.dao.TagDao
import com.pdfa.pdfa_app.data.dao.RecipeTagCrossRefDao
import com.pdfa.pdfa_app.data.model.Recipe
import kotlinx.coroutines.flow.Flow

class RecipeRepository(
    private val recipeDao: RecipeDao,
) {

    fun getAllRecip(): Flow<List<Recipe>> = recipeDao.getAllRecipes()

    suspend fun insertRecipe(recipe: Recipe) = recipeDao.insertRecipe(recipe)

    suspend fun deleteRecipe(recipe: Recipe) = recipeDao.deleteRecipe(recipe)

}
