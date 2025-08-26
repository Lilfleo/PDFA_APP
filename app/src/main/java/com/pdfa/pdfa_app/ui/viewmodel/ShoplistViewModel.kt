package com.pdfa.pdfa_app.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.pdfa.pdfa_app.data.repository.FoodRepository
import com.pdfa.pdfa_app.data.repository.ShoplistRepository
import com.pdfa.pdfa_app.api.Recipe
import com.pdfa.pdfa_app.data.model.FoodDetail
import com.pdfa.pdfa_app.data.model.Shoplist
import com.pdfa.pdfa_app.data.repository.FoodDetailRepository
import com.pdfa.pdfa_app.data.repository.RecipeToShoplistService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class ShoplistViewModel @Inject constructor(
    private val foodRepository: FoodRepository,
    private val shoplistRepository: ShoplistRepository,
    private val foodDetailRepository: FoodDetailRepository,
    private val recipeToShoplistService: RecipeToShoplistService
) : ViewModel() {

    // États pour l'ajout à la liste de course
    private val _isAddingToShoplist = MutableStateFlow(false)
    val isAddingToShoplist: StateFlow<Boolean> = _isAddingToShoplist.asStateFlow()

    private val _addToShoplistResult = MutableStateFlow<String?>(null)
    val addToShoplistResult: StateFlow<String?> = _addToShoplistResult.asStateFlow()

    // Flow de la liste de course
    val shoplistWithFood = shoplistRepository.allFoodDetail

    fun addRecipeToShoplist(recipe: com.pdfa.pdfa_app.data.model.Recipe) {
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
    fun removeFromShoplist(shoplistItem: Shoplist) {
        viewModelScope.launch {
            shoplistRepository.deleteShoplist(shoplistItem)
        }
    }

    fun updateQuantity(shoplistItem: Shoplist, newQuantity: Int) {
        viewModelScope.launch {
            val updatedItem = shoplistItem.copy(quantity = newQuantity)
            shoplistRepository.updateShoplist(updatedItem)
        }
    }


    //Ajout au Fridge
    private val _selectedItems = MutableStateFlow<Set<Int>>(emptySet())
    val selectedItems: StateFlow<Set<Int>> = _selectedItems.asStateFlow()

    private val _isMovingToFridge = MutableStateFlow(false)
    val isMovingToFridge: StateFlow<Boolean> = _isMovingToFridge.asStateFlow()

    private val _moveToFridgeResult = MutableStateFlow<String?>(null)
    val moveToFridgeResult: StateFlow<String?> = _moveToFridgeResult.asStateFlow()

    fun toggleItemSelection(shoplistId: Int) {
        val currentSelection = _selectedItems.value.toMutableSet()
        if (currentSelection.contains(shoplistId)) {
            currentSelection.remove(shoplistId)
        } else {
            currentSelection.add(shoplistId)
        }
        _selectedItems.value = currentSelection
    }

    fun clearShoplist() {
        viewModelScope.launch {
            try {
                // Supprimer tous les éléments de la shoplist
                val currentItems = shoplistRepository.allFoodDetail.first()
                currentItems.forEach { item ->
                    shoplistRepository.deleteShoplist(item.shoplist)
                }
            } catch (e: Exception) {
                Log.e("ShoplistViewModel", "Erreur lors du vidage de la liste", e)
            }
        }
    }

    fun clearSelection() {
        _selectedItems.value = emptySet()
    }

    fun moveSelectedItemsToFridge() {
        viewModelScope.launch {
            try {
                _isMovingToFridge.value = true
                _moveToFridgeResult.value = null

                val currentShoplist = shoplistRepository.allFoodDetail.first()
                val selectedShoplistItems = currentShoplist.filter {
                    _selectedItems.value.contains(it.shoplist.id)
                }

                Log.d("ShoplistViewModel", "🔄 Déplacement de ${selectedShoplistItems.size} éléments vers le frigo")

                var addedCount = 0
                var updatedCount = 0

                selectedShoplistItems.forEach { shoplistItem ->
                    val shoplist = shoplistItem.shoplist
                    val food = shoplistItem.food

                    if (food != null) {
                        // Créer le FoodDetail
                        val newDetail = FoodDetail(
                            foodId = shoplist.foodId,
                            quantity = shoplist.quantity,
                            isWeight = true,
                            price = null,
                            buyingTime = Date(), // Date actuelle
                            expirationTime = null
                        )

                        // Vérifier si un FoodDetail existe déjà pour ce food
                        val existing = foodDetailRepository.getByFoodId(shoplist.foodId)

                        if (existing != null) {
                            // Fusionner les quantités
                            val mergedDetail = existing.copy(
                                quantity = existing.quantity + newDetail.quantity,
                                price = newDetail.price ?: existing.price,
                                isWeight = newDetail.isWeight,
                                buyingTime = newDetail.buyingTime,
                                expirationTime = newDetail.expirationTime ?: existing.expirationTime
                            )
                            foodDetailRepository.update(mergedDetail)
                            updatedCount++
                            Log.d("ShoplistViewModel", "✅ Quantité mise à jour pour ${food.name}: ${existing.quantity} + ${newDetail.quantity}")
                        } else {
                            // Créer un nouveau FoodDetail
                            foodDetailRepository.insert(newDetail)
                            addedCount++
                            Log.d("ShoplistViewModel", "✅ Nouvel élément ajouté au frigo: ${food.name}")
                        }

                        // Supprimer de la shoplist
                        shoplistRepository.deleteShoplist(shoplist)
                    } else {
                        Log.w("ShoplistViewModel", "⚠️ Food null pour shoplist ID: ${shoplist.id}")
                    }
                }

                // Message de résultat
                val resultMessage = when {
                    addedCount > 0 && updatedCount > 0 ->
                        "✅ $addedCount nouveaux aliments ajoutés et $updatedCount quantités mises à jour dans le frigo !"
                    addedCount > 0 ->
                        "✅ $addedCount aliments ajoutés au frigo !"
                    updatedCount > 0 ->
                        "✅ $updatedCount quantités mises à jour dans le frigo !"
                    else ->
                        "❌ Aucun élément n'a pu être déplacé"
                }

                _moveToFridgeResult.value = resultMessage
                _selectedItems.value = emptySet() // Clear selection

            } catch (e: Exception) {
                Log.e("ShoplistViewModel", "❌ Erreur lors du déplacement vers le frigo", e)
                _moveToFridgeResult.value = "❌ Erreur lors du déplacement : ${e.message}"
            } finally {
                _isMovingToFridge.value = false
            }
        }
    }

    fun clearMoveToFridgeResult() {
        _moveToFridgeResult.value = null
    }
}
