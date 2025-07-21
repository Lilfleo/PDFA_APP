package com.pdfa.pdfa_app.ui.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import kotlinx.coroutines.launch
import com.pdfa.pdfa_app.data.repository.ApiRepository
import com.pdfa.pdfa_app.api.RecipeResponse
import com.pdfa.pdfa_app.api.HelloResponse
import com.pdfa.pdfa_app.api.RecipeWithFood

class RecipeViewModel : ViewModel() {
    private val repository = ApiRepository()

    // États pour la connexion
    private val _connectionStatus = mutableStateOf<HelloResponse?>(null)
    val connectionStatus: State<HelloResponse?> = _connectionStatus

    // États pour les recettes
    private val _recipe = mutableStateOf<RecipeResponse?>(null)
    val recipe: State<RecipeResponse?> = _recipe

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _error = mutableStateOf<String?>(null)
    val error: State<String?> = _error

    fun testConnection() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            Log.d(TAG, "🔄 Tentative de connexion à l'API...")

            try {
                val result = repository.testConnection()
                _connectionStatus.value = result

                // Log de succès
                Log.i(TAG, "✅ Connexion réussie!")

            } catch (e: Exception) {
                _error.value = "Erreur de connexion: ${e.message}"

                // Log d'erreur
                Log.e(TAG, "❌ Erreur de connexion", e)
                Log.e(TAG, "💥 Message d'erreur: ${e.message}")

            } finally {
                _isLoading.value = false
                Log.d(TAG, "🏁 Test de connexion terminé")
            }
        }
    }

    fun generateRecipe(requestData: RecipeWithFood) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            Log.d(TAG, "🔄 Données envoyées: $requestData")

            try {
                val recipeResponse = repository.generateRecipe(requestData)
                _recipe.value = recipeResponse
                Log.i(TAG, "✅ Recette générée: ${recipeResponse.recipe.title}")
            } catch (e: Exception) {
                Log.e(TAG, "❌ Erreur: ${e.message}", e)
                _error.value = "Erreur: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        repository.cleanup()
    }
}
