package com.pdfa.pdfa_app.ui.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import com.pdfa.pdfa_app.data.repository.ApiRepository
import com.pdfa.pdfa_app.api.RecipeResponse
import com.pdfa.pdfa_app.api.HelloResponse
import com.pdfa.pdfa_app.api.Ingredient
import com.pdfa.pdfa_app.api.Recipe
import com.pdfa.pdfa_app.api.RecipeForShoplist
import com.pdfa.pdfa_app.api.RecipeMultipleResponse
import com.pdfa.pdfa_app.api.RecipeWithFood
import com.pdfa.pdfa_app.api.RecipeWithFoodPrompt
import com.pdfa.pdfa_app.api.RecipeWithoutFoodPrompt
import com.pdfa.pdfa_app.api.Tags
import com.pdfa.pdfa_app.data.repository.AllergyRepository
import com.pdfa.pdfa_app.data.repository.DietPreferenceRepository
import com.pdfa.pdfa_app.data.repository.FoodDetailRepository
import com.pdfa.pdfa_app.data.repository.TagPreferenceRepository
import com.pdfa.pdfa_app.data.repository.UtensilPreferenceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val tagRepository: TagPreferenceRepository,
    private val dietRepository: DietPreferenceRepository,
    private val allergyRepository: AllergyRepository,
    private val utensilRepository: UtensilPreferenceRepository,
    private val foodDetailRepository: FoodDetailRepository
) : ViewModel() {
    private val repository = ApiRepository()

    // États pour la connexion
    private val _connectionStatus = mutableStateOf<HelloResponse?>(null)
    val connectionStatus: State<HelloResponse?> = _connectionStatus

    // États pour les recettes with food
    private val _recipe = mutableStateOf<RecipeResponse?>(null)
    val recipe: State<RecipeResponse?> = _recipe

    // États pour les multiples recettes with food
    private val _multipleRecipeWithFood = mutableStateOf<RecipeMultipleResponse?>(null)
    val multipleRecipeWithFood: State<RecipeMultipleResponse?> = _multipleRecipeWithFood

    // États pour les multiples recettes without food
    private val _multipleRecipeWithoutFood = mutableStateOf<RecipeMultipleResponse?>(null)
    val multipleRecipeWithoutFood: State<RecipeMultipleResponse?> = _multipleRecipeWithoutFood

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _isLoadingWithFood = mutableStateOf(false)
    val isLoadingWithFood: State<Boolean> = _isLoadingWithFood

    private val _isLoadingWithoutFood = mutableStateOf(false)
    val isLoadingWithoutFood: State<Boolean> = _isLoadingWithoutFood

    private val _errorWithFood = mutableStateOf<String?>(null)
    val errorWithFood: State<String?> = _errorWithFood

    private val _errorWithoutFood = mutableStateOf<String?>(null)
    val errorWithoutFood: State<String?> = _errorWithoutFood

    //Recette selectionner
    private val _selectedRecipe = mutableStateOf<Recipe?>(null)
    val selectedRecipe: State<Recipe?> = _selectedRecipe

    //Collect pour envoi
    val tagPreferenceWithTags = tagRepository.allTagPreferences
    private val _tagNames = mutableStateOf<List<String>>(emptyList())
    val tagNames: State<List<String>> = _tagNames

    private fun getTagPreferenceNames() {
        viewModelScope.launch {
            tagPreferenceWithTags.collect { tagPreferences ->
                _tagNames.value = tagPreferences.map { it.tag.name }
            }
        }
    }

    val dietPreferenceWithDiet = dietRepository.allDietPreferences
    private val _dietNames = mutableStateOf<List<String>>(emptyList())
    val dietNames: State<List<String>> = _dietNames

    private fun getDietPreferenceNames() {
        viewModelScope.launch {
            dietPreferenceWithDiet.collect { dietPreference ->
                _dietNames.value = dietPreference.map { it.diet.name }
            }
        }
    }

    val allergyWithFood = allergyRepository.allAllergy
    private val _allergyNames = mutableStateOf<List<String>>(emptyList())
    val allergyNames: State<List<String>> = _allergyNames

    private fun getAllergiesWithFood() {
        viewModelScope.launch {
            allergyWithFood.collect { allergyWithFood ->
                _allergyNames.value = allergyWithFood.map { it.food.name }
            }
        }
    }

    val foodDetailWithFood = foodDetailRepository.allFoodDetail
    private val _ingredients = mutableStateOf<List<Ingredient>>(emptyList())
    val ingredients: State<List<Ingredient>> = _ingredients

    fun getFoodDetail() {
        viewModelScope.launch {
            foodDetailWithFood.collect { foodDetailWithFood ->
                val ingredientList = foodDetailWithFood.map { foodDetailItem ->
                    Ingredient(
                        name = foodDetailItem.food.name,
                        quantity = foodDetailItem.foodDetail.quantity.toDouble(),
                        unit =  "g"
                    )
                }
                _ingredients.value = ingredientList
            }
        }
    }

    val utensilWithUtensil = utensilRepository.allUtensilPreferences
    private val _utensilNames = mutableStateOf<List<String>>(emptyList())
    val utensilNames: State<List<String>> = _utensilNames
    private fun getUtensil() {
        viewModelScope.launch {
            utensilWithUtensil.collect { utensil ->
                _utensilNames.value = utensil.map { it.utensil.name }
            }
        }
    }


    //Excluded Title
    private val _excludedTitleList = mutableStateOf<List<String>>(emptyList())
    val excludedTitleList: State<List<String>> = _excludedTitleList

    fun addExcludedTitle(newString: String) {
        val currentList = _excludedTitleList.value.toMutableList()
        currentList.add(newString)
        _excludedTitleList.value = currentList
    }

    // État de chargement
    private val _isDataReady = mutableStateOf(false)
    val isDataReady: State<Boolean> = _isDataReady

    private var dataCollectionJobs = mutableListOf<Job>()

    // Initialiser les collectors une seule fois
    fun initializeDataCollectors() {
        if (dataCollectionJobs.isNotEmpty()) return // Déjà initialisé

        dataCollectionJobs.addAll(listOf(
            viewModelScope.launch { getTagPreferenceNames() },
            viewModelScope.launch { getDietPreferenceNames() },
            viewModelScope.launch { getAllergiesWithFood() },
            viewModelScope.launch { getFoodDetail() },
            viewModelScope.launch { getUtensil() }
        ))

        // Marquer comme prêt après un délai pour s'assurer que les données sont collectées
        viewModelScope.launch {
            delay(1000L) // Laisser le temps aux collectors de récupérer les premières données
            _isDataReady.value = true
            Log.d(TAG, "✅ Données prêtes pour l'utilisation")
        }
    }

    fun launchRecipeWithFoodCall() {
        viewModelScope.launch {
            if (!_isDataReady.value) {
                initializeDataCollectors()
                while (!_isDataReady.value) {
                    delay(100L)
                }
            }

            val recipeCall = RecipeWithFood(
                prompt = RecipeWithFoodPrompt(
                    title = "",
                    ingredients = _ingredients.value,
                    utensils = _utensilNames.value,
                    tags = Tags(
                        diet = _dietNames.value,
                        tag = _tagNames.value,
                        allergies = _allergyNames.value
                    )
                ),
                excludedTitles = _excludedTitleList.value
            )

            delay(2000L)
            generateMultipleRecipWithFood(recipeCall)
        }
    }

    fun launchRecipeWithoutFood() {
        viewModelScope.launch {
            if (!_isDataReady.value) {
                initializeDataCollectors()
                while (!_isDataReady.value) {
                    delay(100L)
                }
            }


            val recipeCall = RecipeForShoplist(
                prompt = RecipeWithoutFoodPrompt(
                    title = "",
                    utensils = _utensilNames.value,
                    tags = Tags(
                        diet = _dietNames.value,
                        tag = _tagNames.value,
                        allergies = _allergyNames.value
                    )
                ),
                excludedTitles = _excludedTitleList.value
            )


            delay(2000L)
            generateMultipleRecipWithoutFood(recipeCall)
        }
    }

    fun selectRecipe(recipe: Recipe) {
        _selectedRecipe.value = recipe
    }

    fun generateRecipe(requestData: RecipeWithFood) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorWithFood.value = null

            Log.d(TAG, "🔄 Données envoyées: $requestData")

            try {
                val recipeResponse = repository.generateRecipe(requestData)
                _recipe.value = recipeResponse
                Log.i(TAG, "✅ Recette générée: ${recipeResponse.recipe.title}")
            } catch (e: Exception) {
                Log.e(TAG, "❌ Erreur: ${e.message}", e)
                _errorWithFood.value = "Erreur: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun generateMultipleRecipWithFood(requestData: RecipeWithFood) {
        viewModelScope.launch {
            _isLoadingWithFood.value = true
            _errorWithFood.value = null

            Log.d(TAG, "🔄 Données envoyées: $requestData")

            try {
                val recipeResponse = repository.generateMultipleRecipWithFood(requestData)
                _multipleRecipeWithFood.value = recipeResponse
                recipeResponse.recipes.forEach { recipe ->
                    addExcludedTitle(recipe.title)
                }
                Log.i(TAG, "✅ Recette générée: ${recipeResponse.recipes.size}")
            } catch (e: Exception) {
                Log.e(TAG, "❌ Erreur: ${e.message}", e)
                _errorWithFood.value = "Erreur: ${e.message}"
            } finally {
                _isLoadingWithFood.value = false
            }
        }
    }

    fun generateMultipleRecipWithoutFood(requestData: RecipeForShoplist) {
        viewModelScope.launch {
            _isLoadingWithoutFood.value = true
            _errorWithoutFood.value = null

            Log.d(TAG, "🔄 Données envoyées: $requestData")

            try {
                val recipeResponse = repository.generateMultipleRecipWithoutFood(requestData)
                _multipleRecipeWithoutFood.value = recipeResponse
                recipeResponse.recipes.forEach { recipe ->
                    addExcludedTitle(recipe.title)
                }
                Log.i(TAG, "✅ Recette générée: ${recipeResponse.recipes.size}")
            } catch (e: Exception) {
                Log.e(TAG, "❌ Erreur: ${e.message}", e)
                _errorWithoutFood.value = "Erreur: ${e.message}"
            } finally {
                _isLoadingWithoutFood.value = false
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        repository.cleanup()
    }
}
