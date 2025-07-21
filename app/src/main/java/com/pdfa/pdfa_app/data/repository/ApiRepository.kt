package com.pdfa.pdfa_app.data.repository

import com.pdfa.pdfa_app.api.ApiClient
import com.pdfa.pdfa_app.api.HelloResponse
import com.pdfa.pdfa_app.api.RecipeResponse
import com.pdfa.pdfa_app.api.RecipeWithFood
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ApiRepository {
    private val apiClient = ApiClient()

    suspend fun testConnection(): HelloResponse {
        return apiClient.testConnection().getOrThrow()
    }

    suspend fun generateRecipe(requestData: RecipeWithFood): RecipeResponse {
        return apiClient.getRecipeWithFood(requestData = requestData).getOrThrow()
    }

    fun cleanup() {
        apiClient.close()
    }
}