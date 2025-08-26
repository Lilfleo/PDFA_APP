package com.pdfa.pdfa_app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdfa.pdfa_app.data.model.*
import com.pdfa.pdfa_app.data.repository.CookbookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CookbookViewModel @Inject constructor(
    private val cookbookRepository: CookbookRepository
) : ViewModel() {

    // État des cookbooks utilisateur (pas isInternal)
    private val _userCookbooks = MutableStateFlow<List<CookbookWithRecipes>>(emptyList())
    val userCookbooks: StateFlow<List<CookbookWithRecipes>> = _userCookbooks.asStateFlow()

    // État de chargement
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // État d'erreur
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    // Cookbook sélectionné pour les détails
    private val _selectedCookbook = MutableStateFlow<CookbookWithRecipes?>(null)
    val selectedCookbook: StateFlow<CookbookWithRecipes?> = _selectedCookbook.asStateFlow()

    init {
        loadUserCookbooks()
    }

    // Charger tous les cookbooks utilisateur
    fun loadUserCookbooks() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                cookbookRepository.getUserCookbooksWithRecipesFlow()
                    .collect { cookbooks ->
                        _userCookbooks.value = cookbooks
                        _isLoading.value = false
                    }
            } catch (e: Exception) {
                _error.value = "Erreur lors du chargement des cookbooks: ${e.message}"
                _isLoading.value = false
            }
        }
    }

    // Créer un nouveau cookbook
    fun createCookbook(name: String) {
        viewModelScope.launch {
            try {
                val existingCookbook = cookbookRepository.getCookbookByName(name)
                if (existingCookbook != null) {
                    _error.value = "Un cookbook avec ce nom existe déjà"
                    return@launch
                }

                cookbookRepository.createCookbook(name, isInternal = false)
                // Les cookbooks se rechargeront automatiquement via le Flow
            } catch (e: Exception) {
                _error.value = "Erreur lors de la création du cookbook: ${e.message}"
            }
        }
    }

    // Modifier un cookbook
    fun updateCookbook(cookbook: Cookbook, newName: String) {
        viewModelScope.launch {
            try {
                val updatedCookbook = cookbook.copy(name = newName)
                cookbookRepository.updateCookbook(updatedCookbook)
            } catch (e: Exception) {
                _error.value = "Erreur lors de la modification du cookbook: ${e.message}"
            }
        }
    }

    // Supprimer un cookbook
    fun deleteCookbook(cookbook: Cookbook) {
        viewModelScope.launch {
            try {
                cookbookRepository.deleteCookbook(cookbook)
            } catch (e: Exception) {
                _error.value = "Erreur lors de la suppression du cookbook: ${e.message}"
            }
        }
    }

    //RECIPE

    private val _cookbookStatesForRecipe = MutableStateFlow<List<CookbookSelectionState>>(emptyList())
    val cookbookStatesForRecipe: StateFlow<List<CookbookSelectionState>> = _cookbookStatesForRecipe.asStateFlow()

    private val _isLoadingRecipeStates = MutableStateFlow(false)
    val isLoadingRecipeStates: StateFlow<Boolean> = _isLoadingRecipeStates.asStateFlow()

    private val _isSavingRecipeStates = MutableStateFlow(false)
    val isSavingRecipeStates: StateFlow<Boolean> = _isSavingRecipeStates.asStateFlow()

    fun initializeForRecipe(recipe: Recipe) {
        viewModelScope.launch {
            _isLoadingRecipeStates.value = true
            _error.value = null

            try {
                val states = mutableListOf<CookbookSelectionState>()

                // Utiliser les cookbooks déjà chargés
                _userCookbooks.value.forEach { cookbookWithRecipes ->
                    val isPresent = cookbookRepository.isRecipeInCookbook(cookbookWithRecipes.cookbook.id, recipe.id)
                    states.add(
                        CookbookSelectionState(
                            cookbook = cookbookWithRecipes.cookbook,
                            isSelected = isPresent,
                            wasAlreadyPresent = isPresent
                        )
                    )
                }

                _cookbookStatesForRecipe.value = states
            } catch (e: Exception) {
                _error.value = "Erreur lors du chargement des états: ${e.message}"
            } finally {
                _isLoadingRecipeStates.value = false
            }
        }
    }

    fun toggleCookbookForRecipe(cookbookId: Int) {
        val currentStates = _cookbookStatesForRecipe.value.toMutableList()
        val index = currentStates.indexOfFirst { it.cookbook.id == cookbookId }

        if (index != -1) {
            currentStates[index] = currentStates[index].copy(
                isSelected = !currentStates[index].isSelected
            )
            _cookbookStatesForRecipe.value = currentStates
        }
    }

    fun saveRecipeChanges(recipeId: Int, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            _isSavingRecipeStates.value = true
            _error.value = null

            try {
                _cookbookStatesForRecipe.value.forEach { state ->
                    when {
                        !state.wasAlreadyPresent && state.isSelected -> {
                            cookbookRepository.addRecipeToCookbook(state.cookbook.id, recipeId)
                        }
                        state.wasAlreadyPresent && !state.isSelected -> {
                            cookbookRepository.removeRecipeFromCookbook( state.cookbook.id, recipeId)
                        }
                    }
                }
                onSuccess()
            } catch (e: Exception) {
                val errorMsg = "Erreur lors de la sauvegarde: ${e.message}"
                _error.value = errorMsg
                onError(errorMsg)
            } finally {
                _isSavingRecipeStates.value = false
            }
        }
    }

    fun resetRecipeStates() {
        _cookbookStatesForRecipe.value = emptyList()
        _isLoadingRecipeStates.value = false
        _isSavingRecipeStates.value = false
    }

    // Ajouter une recette à un cookbook
    fun addRecipeToCookbook(cookbookId: Int, recipeId: Int) {
        viewModelScope.launch {
            try {
                cookbookRepository.addRecipeToCookbook(cookbookId, recipeId)
            } catch (e: Exception) {
                _error.value = "Erreur lors de l'ajout de la recette: ${e.message}"
            }
        }
    }

    // Retirer une recette d'un cookbook
    fun removeRecipeFromCookbook(cookbookId: Int, recipeId: Int) {
        viewModelScope.launch {
            try {
                cookbookRepository.removeRecipeFromCookbook(cookbookId, recipeId)
            } catch (e: Exception) {
                _error.value = "Erreur lors de la suppression de la recette: ${e.message}"
            }
        }
    }

    // Sélectionner un cookbook pour voir les détails
    fun selectCookbook(cookbookId: Int) {
        viewModelScope.launch {
            try {
                val cookbook = cookbookRepository.getCookbookWithRecipes(cookbookId)
                _selectedCookbook.value = cookbook
            } catch (e: Exception) {
                _error.value = "Erreur lors du chargement du cookbook: ${e.message}"
            }
        }
    }

//    fun isRecipeInCookbook(cookbookId: Int, recipeId: Int): Boolean {
//        viewModelScope.launch {
//            try {
//                val isRecipe = cookbookRepository.isRecipeInCookbook(cookbookId, recipeId)
//            } catch (e: Exception) {
//                _error.value = "Erreur lors du chargement du cookbook: ${e.message}"
//            }
//        }
//    }

    // Effacer les erreurs
    fun clearError() {
        _error.value = null
    }
}
