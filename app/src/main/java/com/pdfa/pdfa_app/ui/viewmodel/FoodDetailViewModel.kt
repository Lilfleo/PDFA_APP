package com.pdfa.pdfa_app.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdfa.pdfa_app.api.Ingredient
import com.pdfa.pdfa_app.data.model.Food
import com.pdfa.pdfa_app.data.model.FoodDetail
import com.pdfa.pdfa_app.data.model.FoodDetailWithFood
import com.pdfa.pdfa_app.data.repository.FoodDetailRepository
import com.pdfa.pdfa_app.data.repository.FoodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class FoodDetailViewModel @Inject constructor(
    private val repository: FoodDetailRepository,
    private val foodRepository: FoodRepository
) : ViewModel() {

    val foodDetail: StateFlow<List<FoodDetailWithFood>> =
        repository.allFoodDetail.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    fun addFoodDetail(foodDetail: FoodDetail) {
        viewModelScope.launch {
            repository.insert(foodDetail)
        }
    }

    fun updateFoodDetail(foodDetail: FoodDetail) {
        viewModelScope.launch {
            repository.update(foodDetail)
        }
    }

    fun upsertFoodDetail(foodDetail: FoodDetail) {
        viewModelScope.launch {
            repository.upsertFoodDetail(foodDetail)
        }
    }

    fun getFoodDetail(foodId: Int): Flow<FoodDetailWithFood> {
        return repository.getFoodDetail(foodId)
    }

    fun deleteFoodDetail(detail: FoodDetail) {
        viewModelScope.launch {
            repository.delete(detail)
        }
    }
    suspend fun getByFoodId(foodId: Int): FoodDetail? {
        return repository.getByFoodId(foodId)
    }

    suspend fun canMakeRecipe(ingredients: List<Ingredient>): Boolean {
        return try {
            ingredients.all { ingredient ->
                val food = foodRepository.findByName(ingredient.name.replaceFirstChar { it.uppercase() })
                if (food != null) {
                    val foodDetailInFridge = repository.getByFoodId(food.id)
                    if (foodDetailInFridge != null) {
                        // Convertir la quantité nécessaire vers l'unité du frigo
                        val requiredQuantity = convertQuantity(
                            ingredient.quantity?.toInt() ?: 0,
                            ingredient.unit,
                            foodDetailInFridge.unit
                        )

                        val hasEnough = foodDetailInFridge.quantity >= requiredQuantity
                        Log.d("RecipeCheck", "🔍 ${ingredient.name}: besoin de $requiredQuantity ${foodDetailInFridge.unit}, disponible ${foodDetailInFridge.quantity} -> $hasEnough")

                        hasEnough
                    } else {
                        Log.d("RecipeCheck", "❌ ${ingredient.name}: pas dans le frigo")
                        false
                    }
                } else {
                    Log.d("RecipeCheck", "❌ ${ingredient.name}: food introuvable")
                    false
                }
            }
        } catch (e: Exception) {
            Log.e("RecipeCheck", "Erreur lors de la vérification des ingrédients", e)
            false
        }
    }

    fun prepareRecipe(ingredients: List<Ingredient>, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            try {
                // Vérifier d'abord si on peut faire la recette
                if (!canMakeRecipe(ingredients)) {
                    onResult(false, "❌ Ingrédients insuffisants dans le frigo")
                    return@launch
                }

                // Retirer les ingrédients
                val success = removeRecipeIngredients(ingredients)

                if (success) {
                    onResult(true, "✅ Recette préparée ! Ingrédients retirés du frigo")
                } else {
                    onResult(false, "❌ Erreur lors de la préparation")
                }
            } catch (e: Exception) {
                Log.e("RecipePrep", "Erreur lors de la préparation", e)
                onResult(false, "❌ Erreur: ${e.message}")
            }
        }
    }

    suspend fun removeRecipeIngredients(ingredients: List<Ingredient>): Boolean {
        return try {
            var allSuccess = true
            ingredients.forEach { ingredient ->
                // Normaliser le nom de l'ingrédient
                val normalizedName = ingredient.name.lowercase().trim()

                // Essayer d'abord avec findByName (optimisé)
                var food = foodRepository.findByName(ingredient.name.replaceFirstChar { it.uppercase() })

                // Si pas trouvé, utiliser la recherche par contenu
                if (food == null) {
                    food = foodRepository.findByNameContaining(normalizedName)
                    Log.d("RecipePrep", "✅ Trouvé par correspondance: ${food?.name} pour ingrédient: ${ingredient.name}")
                }

                if (food != null) {
                    val foodDetailInFridge = repository.getByFoodId(food.id)
                    if (foodDetailInFridge != null) {
                        // Convertir la quantité à retirer vers l'unité du frigo
                        val quantityToRemove = convertQuantity(
                            ingredient.quantity?.toInt() ?: 0,
                            ingredient.unit,
                            foodDetailInFridge.unit
                        )
                        val newQuantity = foodDetailInFridge.quantity - quantityToRemove
                        if (newQuantity <= 0) {
                            // Plus d'ingrédient, supprimer complètement
                            repository.delete(foodDetailInFridge)
                            Log.d("RecipePrep", "🗑️ ${ingredient.name}: supprimé du frigo (quantité épuisée)")
                        } else {
                            // Mettre à jour avec la nouvelle quantité
                            val updatedDetail = foodDetailInFridge.copy(quantity = newQuantity)
                            repository.update(updatedDetail)
                            Log.d("RecipePrep", "✅ ${ingredient.name}: ${foodDetailInFridge.quantity} -> $newQuantity ${foodDetailInFridge.unit}")
                        }
                    } else {
                        Log.w("RecipePrep", "⚠️ ${ingredient.name} introuvable dans le frigo")
                        allSuccess = false
                    }
                } else {
                    Log.w("RecipePrep", "⚠️ Food ${ingredient.name} introuvable")
                    allSuccess = false
                }
            }

            allSuccess
        } catch (e: Exception) {
            Log.e("RecipePrep", "Erreur lors du retrait des ingrédients", e)
            false
        }
    }

    private fun convertQuantity(quantity: Int, fromUnit: String, toUnit: String): Int {
        if (fromUnit == toUnit) return quantity

        // Ratio fixe : 1 pcs = 50g = 5cl
        val ratios = mapOf(
            "g" to 1,
            "pcs" to 50,
            "cl" to 10,
            "ml" to 1,
            "l" to 1000,
            "kg" to 1000,
            "unité" to 50
        )

        val fromRatio = ratios[fromUnit] ?: 1
        val toRatio = ratios[toUnit] ?: 1

        // Convertir vers grammes puis vers l'unité cible
        val inGrams = quantity * fromRatio
        val convertedQuantity = inGrams / toRatio

        return convertedQuantity.coerceAtLeast(1)
    }
}
