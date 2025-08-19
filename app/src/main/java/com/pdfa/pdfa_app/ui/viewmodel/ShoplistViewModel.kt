package com.pdfa.pdfa_app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.pdfa.pdfa_app.data.repository.FoodRepository
import com.pdfa.pdfa_app.data.repository.ShoplistRepository
import com.pdfa.pdfa_app.api.Recipe
import com.pdfa.pdfa_app.data.repository.RecipeToShoplistService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ShoplistViewModel @Inject constructor(
    private val foodRepository: FoodRepository,
    private val shoplistRepository: ShoplistRepository,
    private val recipeToShoplistService: RecipeToShoplistService
) : ViewModel() {

    // États pour l'ajout à la liste de course
    private val _isAddingToShoplist = MutableStateFlow(false)
    val isAddingToShoplist: StateFlow<Boolean> = _isAddingToShoplist.asStateFlow()

    private val _addToShoplistResult = MutableStateFlow<String?>(null)
    val addToShoplistResult: StateFlow<String?> = _addToShoplistResult.asStateFlow()

    // Flow de la liste de course
    val shoplistWithFood = shoplistRepository.allFoodDetail

    fun addRecipeToShoplist(recipe: Recipe) {
        viewModelScope.launch {
            try {
                _isAddingToShoplist.value = true
                _addToShoplistResult.value = null

                recipeToShoplistService.addRecipeToShoplist(recipe)

                _addToShoplistResult.value = "✅ ${recipe.ingredients.size} ingrédients ajoutés à la liste de course"
            } catch (e: Exception) {
                _addToShoplistResult.value = "❌ Erreur lors de l'ajout : ${e.message}"
            } finally {
                _isAddingToShoplist.value = false
            }
        }
    }

    fun clearAddToShoplistResult() {
        _addToShoplistResult.value = null
    }

    // Autres méthodes utiles pour la shoplist
    fun removeFromShoplist(shoplistItem: com.pdfa.pdfa_app.data.model.Shoplist) {
        viewModelScope.launch {
            shoplistRepository.deleteShoplist(shoplistItem)
        }
    }

    fun updateQuantity(shoplistItem: com.pdfa.pdfa_app.data.model.Shoplist, newQuantity: Int) {
        viewModelScope.launch {
            val updatedItem = shoplistItem.copy(quantity = newQuantity)
            shoplistRepository.updateShoplist(updatedItem)
        }
    }
}
