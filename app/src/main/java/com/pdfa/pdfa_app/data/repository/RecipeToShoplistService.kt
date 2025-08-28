package com.pdfa.pdfa_app.data.repository

import android.util.Log
import com.pdfa.pdfa_app.data.model.Food
import com.pdfa.pdfa_app.data.model.Shoplist
import com.pdfa.pdfa_app.data.repository.FoodRepository
import com.pdfa.pdfa_app.data.repository.ShoplistRepository
import com.pdfa.pdfa_app.api.Recipe
import com.pdfa.pdfa_app.api.Ingredient
import javax.inject.Inject

class RecipeToShoplistService @Inject constructor(
    private val foodRepository: FoodRepository,
    private val shoplistRepository: ShoplistRepository,
    private val cookbookRepository: CookbookRepository
) {

    suspend fun addRecipeToShoplist(recipe: com.pdfa.pdfa_app.data.model.Recipe) {
        Log.d("RecipeToShoplist", "🔄 Ajout de ${recipe.ingredients.size} ingrédients à la liste")

        val cookbookShoplist = cookbookRepository.getCookbookByName("RecipeShoplist")
        Log.d("RecipeToShoplist", "ajout à la liste RecipeToShoplist: $cookbookShoplist")
        cookbookShoplist?.let { cb ->
            cookbookRepository.addRecipeToCookbook(cb.id, recipe.id)
        }

        recipe.ingredients.forEach { ingredient ->
            try {
                addIngredientToShoplist(ingredient)
                Log.d("RecipeToShoplist", "✅ Ingrédient ajouté: ${ingredient.name}")
            } catch (e: Exception) {
                Log.e("RecipeToShoplist", "❌ Erreur pour ${ingredient.name}: ${e.message}", e)
                throw e
            }
        }
    }

    private suspend fun addIngredientToShoplist(ingredient: Ingredient) {
        // 1. Chercher ou créer le Food
        val food = findOrCreateFood(ingredient.name, ingredient.unit)
        Log.d("RecipeToShoplist", "🍎 Food trouvé/créé: ${food.name} (ID: ${food.id})")

        // 2. Vérifier si l'item existe déjà dans la shoplist
        val existingShoplistItem = shoplistRepository.findByFoodId(food.id)

        val quantity = ingredient.quantity?.toInt() ?: 1
        val unit = if (ingredient.unit.isBlank()) "unité" else ingredient.unit

        if (existingShoplistItem != null) {
            Log.d("RecipeToShoplist", "♻️ Mise à jour quantité existante: ${existingShoplistItem.quantity} + $quantity")
            // Mettre à jour la quantité existante
            val updatedItem = existingShoplistItem.copy(
                quantity = existingShoplistItem.quantity + quantity,
                quantityType = unit
            )
            shoplistRepository.updateShoplist(updatedItem)
        } else {
            Log.d("RecipeToShoplist", "➕ Nouvel item shoplist: $quantity $unit de ${food.name}")
            // Créer un nouvel item dans la shoplist
            val shoplistItem = Shoplist(
                foodId = food.id,
                quantity = quantity,
                quantityType = unit
            )
            shoplistRepository.insertShoplist(shoplistItem)
        }
    }

    private suspend fun findOrCreateFood(ingredientName: String, ingredientUnit: String): Food {
        Log.d("RecipeToShoplist", "🔍 Recherche de l'aliment: $ingredientName")

        // Recherche par nom exact d'abord
        var food = foodRepository.findByName(ingredientName)
        if (food != null) {
            Log.d("RecipeToShoplist", "✅ Trouvé par nom exact: ${food.name} (ID: ${food.id})")
            return food
        }

        // Recherche avec une correspondance plus flexible
        val normalizedName = ingredientName.lowercase().trim()
        food = foodRepository.findByNameContaining(normalizedName)
        if (food != null) {
            Log.d("RecipeToShoplist", "✅ Trouvé par correspondance: ${food.name} (ID: ${food.id})")
            return food
        }

        // Créer un nouveau Food si aucun n'est trouvé
        val formattedName = ingredientName.trim().replaceFirstChar {
            if (it.isLowerCase()) it.titlecase() else it.toString()
        }
        Log.d("RecipeToShoplist", "➕ Création d'un nouvel aliment: $ingredientName")
        val newFood = Food(
            name = formattedName,
            link = null,
            category = "Autre",
            unit = listOf(ingredientUnit),
            caloriesPerKg = 0,
            caloriesPerUnit = 0
        )

        val foodId = foodRepository.insert(newFood)
        Log.d("RecipeToShoplist", "✅ Aliment créé avec l'ID: $foodId")

        // Récupérer le Food complet depuis la base pour s'assurer qu'il a bien été inséré
        val createdFood = foodRepository.findById(foodId.toInt())
        if (createdFood == null) {
            throw Exception("Échec de la création de l'aliment: $ingredientName")
        }

        Log.d("RecipeToShoplist", "✅ Aliment récupéré: ${createdFood.name} (ID: ${createdFood.id})")
        return createdFood
    }
}
