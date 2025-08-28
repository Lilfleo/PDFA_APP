package com.pdfa.pdfa_app.data.repository

import android.util.Log
import com.pdfa.pdfa_app.data.dao.CookbookDao
import com.pdfa.pdfa_app.data.dao.RecipeDao
import com.pdfa.pdfa_app.data.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CookbookRepository @Inject constructor(
    private val cookbookDao: CookbookDao,
    private val recipeDao: RecipeDao
) {

    // CRUD Cookbook
    suspend fun createCookbook(name: String, isInternal: Boolean = false): Long {
        val cookbook = Cookbook(name = name, isInternal = isInternal, isDeletable = true)
        return cookbookDao.insertCookbook(cookbook)
    }

    suspend fun updateCookbook(cookbook: Cookbook) {
        cookbookDao.updateCookbook(cookbook)
    }

    suspend fun deleteCookbook(cookbook: Cookbook) {
        cookbookDao.deleteCookbook(cookbook)
    }

    suspend fun getCookbookById(id: Int): Cookbook? {
        return cookbookDao.getCookbookById(id)
    }

    fun getAllUserCookbooks(): Flow<List<Cookbook>> {
        return cookbookDao.getAllUserCookbooks()
    }

    suspend fun getRecipeCountInCookbook(cookbookId: Long): Int {
        return cookbookDao.getRecipeCountInCookbook(cookbookId)
    }


    suspend fun getCookbookByName(name: String): Cookbook? {
        return cookbookDao.getCookbookByName(name)
    }

    // Récupérer cookbooks par type
    suspend fun getInternalCookbooks(): List<Cookbook> {
        return cookbookDao.getCookbooksByType(true)
    }

    suspend fun getUserCookbooks(): List<Cookbook> {
        return cookbookDao.getCookbooksByType(false)
    }

    fun getUserCookbooksFlow(): Flow<List<Cookbook>> {
        return cookbookDao.getCookbooksByTypeFlow(false)
    }

    // Récupérer cookbooks avec recettes
    suspend fun getCookbookWithRecipes(cookbookId: Int): CookbookWithRecipes? {
        return cookbookDao.getCookbookWithRecipes(cookbookId)
    }

    suspend fun getCookbookWithRecipesByName(name: String): CookbookWithRecipes? {
        return cookbookDao.getCookbookWithRecipesByName(name)
    }

    suspend fun getUserCookbooksWithRecipes(): List<CookbookWithRecipes> {
        return cookbookDao.getCookbooksWithRecipesByType(false)
    }

    suspend fun getRecipesFromInternalCookbook(cookbookName: String): Flow<List<Recipe>> {
        return flow {
            val cookbook = cookbookDao.getCookbookByName(cookbookName)
            cookbook?.let { cb ->
                recipeDao.getRecipesFromCookbookByName(cb.name).collect { recipes ->
                    emit(recipes)
                }
            } ?: emit(emptyList())
        }
    }

    fun getUserCookbooksWithRecipesFlow(): Flow<List<CookbookWithRecipes>> {
        return cookbookDao.getCookbooksWithRecipesByTypeFlow(false)
    }

    // Gestion des recettes dans les cookbooks
    suspend fun addRecipeToCookbook(cookbookId: Int, recipeId: Int) {
        val crossRef = CookbookRecipeCrossRef(cookbookId, recipeId)
        cookbookDao.insertCookbookRecipeCrossRef(crossRef)
    }

    suspend fun addRecipeToCookbookByName(cookbookName: String, recipeId: Int) {
        val cookbook = cookbookDao.getCookbookByName(cookbookName)
        cookbook?.let {
            addRecipeToCookbook(it.id, recipeId)
        }
    }

    suspend fun removeRecipeFromCookbook(cookbookId: Int, recipeId: Int) {
        cookbookDao.removeRecipeFromCookbook(cookbookId, recipeId)
    }

    suspend fun isRecipeInCookbook(cookbookId: Int, recipeId: Int): Boolean {
        return cookbookDao.isRecipeInCookbook(cookbookId, recipeId) > 0
    }

    // Sauvegarder une recette générée par l'IA dans le bon cookbook
    suspend fun saveRecipeToInternalCookbook(recipe: com.pdfa.pdfa_app.api.Recipe, cookbookName: String): Long {
        // Convertir la recette API en recette Room
        val roomRecipe = Recipe(
            title = recipe.title,
            subTitle = recipe.subTitle,
            preparationTime = recipe.preparationTime,
            totalCookingTime = recipe.totalCookingTime,
            tags = recipe.tags,
            ingredients = recipe.ingredients,
            steps = recipe.steps
        )

        // Sauvegarder la recette
        val recipeId = recipeDao.insertRecipe(roomRecipe)

        // L'ajouter au cookbook approprié
        addRecipeToCookbookByName(cookbookName, recipeId.toInt())

        return recipeId
    }

    suspend fun saveMultipleRecipesToInternalCookbook(
        recipes: List<com.pdfa.pdfa_app.api.Recipe>,
        cookbookName: String
    ): List<Long> {
        return recipes.map { recipe ->
            saveRecipeToInternalCookbook(recipe, cookbookName)
        }
    }

    suspend fun moveRecipesToHistory() {
        try {
            // Récupérer les cookbooks source et destination
            val recipeWithFood = getCookbookByName("RecipeWithFood")
            val recipeWithoutFood = getCookbookByName("RecipeWithoutFood")
            val history = getCookbookByName("History")

            if (history == null) {
                Log.e("CookbookRepository", "Cookbook 'History' introuvable")
                return
            }

            // Récupérer toutes les recettes des cookbooks sources
            val recipesFromWithFood = recipeWithFood?.let {
                getCookbookWithRecipes(it.id)?.recipes ?: emptyList()
            } ?: emptyList()

            val recipesFromWithoutFood = recipeWithoutFood?.let {
                getCookbookWithRecipes(it.id)?.recipes ?: emptyList()
            } ?: emptyList()

            // Combiner toutes les recettes
            val allRecipes = recipesFromWithFood + recipesFromWithoutFood

            if (allRecipes.isNotEmpty()) {
                Log.d("CookbookRepository", "Déplacement de ${allRecipes.size} recettes vers History")

                // Ajouter toutes les recettes à History
                allRecipes.forEach { recipe ->
                    // Vérifier si la recette n'est pas déjà dans History
                    if (!isRecipeInCookbook(history.id, recipe.id)) {
                        addRecipeToCookbook(history.id, recipe.id)
                    }
                }

                // Supprimer les recettes des cookbooks sources
                recipeWithFood?.let { cookbook ->
                    recipesFromWithFood.forEach { recipe ->
                        removeRecipeFromCookbook(cookbook.id, recipe.id)
                    }
                }

                recipeWithoutFood?.let { cookbook ->
                    recipesFromWithoutFood.forEach { recipe ->
                        removeRecipeFromCookbook(cookbook.id, recipe.id)
                    }
                }

                Log.d("CookbookRepository", "✅ Recettes déplacées avec succès vers History")
            }
        } catch (e: Exception) {
            Log.e("CookbookRepository", "❌ Erreur lors du déplacement vers History: ${e.message}", e)
        }
    }

}
