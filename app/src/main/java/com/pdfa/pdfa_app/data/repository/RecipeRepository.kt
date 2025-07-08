package com.pdfa.pdfa_app.data.repository

import com.pdfa.pdfa_app.data.dao.RecipeDao
import com.pdfa.pdfa_app.data.dao.TagDao
import com.pdfa.pdfa_app.data.dao.RecipeTagCrossRefDao
import com.pdfa.pdfa_app.data.model.Recipe
import com.pdfa.pdfa_app.data.model.RecipeTagCrossRef
import com.pdfa.pdfa_app.data.model.RecipeWithTags
import com.pdfa.pdfa_app.data.model.Tag
import com.pdfa.pdfa_app.data.model.TagWithRecipes

class RecipeRepository(
    private val recipeDao: RecipeDao,
    private val tagDao: TagDao,
    private val crossRefDao: RecipeTagCrossRefDao
) {

    suspend fun insertRecipe(recipe: Recipe): Long {
        return recipeDao.insertRecipe(recipe)
    }

    suspend fun insertTag(tag: Tag): Long {
        return tagDao.insertTag(tag)
    }

    suspend fun linkRecipeWithTag(recipeId: Int, tagId: Int) {
        crossRefDao.insertRecipeTagCrossRef(
            RecipeTagCrossRef(recipeId = recipeId, tagId = tagId)
        )
    }

    suspend fun insertRecipeWithTags(recipe: Recipe, tagIDs: List<Int>): Long {
        val recipeId = insertRecipe(recipe).toInt()

        for (tagID in tagIDs) {
            linkRecipeWithTag(recipeId, tagID)
        }

        return recipeId.toLong()
    }

    suspend fun getRecipeWithTags(recipeId: Int): RecipeWithTags {
        return recipeDao.getRecipeWithTags(recipeId)
    }

    suspend fun getTagWithRecipes(tagId: Int): TagWithRecipes {
        return tagDao.getTagWithRecipes(tagId)
    }
}
