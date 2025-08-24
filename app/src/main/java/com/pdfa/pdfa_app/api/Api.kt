package com.pdfa.pdfa_app.api

import android.content.ContentValues.TAG
import android.util.Log
import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.HttpRedirect
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class ApiClient(
    private val baseUrl: String = "http://yep-ia.onrender.com",
    private val httpClient: HttpClient = createDefaultHttpClient()
) {

    companion object {
        private const val TAG = "ApiClient"

        fun createDefaultHttpClient(): HttpClient {
            return HttpClient(Android) {
                install(ContentNegotiation) {
                    json(Json {
                        ignoreUnknownKeys = true
                        isLenient = true
                    })
                }
                install(HttpRedirect) {
                    checkHttpMethod = false
                    allowHttpsDowngrade = false
                }
            }
        }
    }

    suspend fun testConnection(): Result<HelloResponse> {
        return try {
            Log.d(TAG, "🌐 Appel API: GET $baseUrl/")

            val response: HttpResponse = httpClient.get("$baseUrl/")

            Log.d(TAG, "📡 Code de réponse: ${response.status.value}")

            if (response.status.isSuccess()) {
                val helloResponse = response.body<HelloResponse>()
                Log.d(TAG, "✅ Réponse reçue: $helloResponse")
                Result.success(helloResponse)
            } else {
                Log.w(TAG, "⚠️ Réponse non-success: ${response.status}")
                Result.failure(Exception("Erreur de connexion: ${response.status}"))
            }
        } catch (e: Exception) {
            Log.e(TAG, "💥 Exception lors de l'appel API", e)
            Result.failure(e)
        }
    }

    suspend fun getRecipeWithFood(requestData: RecipeWithFood): Result<RecipeResponse> {
        return try {
            val response: HttpResponse = httpClient.post("$baseUrl/recipes/generate") {
                contentType(ContentType.Application.Json)
                 setBody(requestData)
            }

            if (response.status.isSuccess()) {
                val recipeResponse = response.body<RecipeResponse>()
                Result.success(recipeResponse)
            } else {
                Result.failure(Exception("Erreur lors de la génération de recette: ${response.status}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getMultipleRecipeWithFood(requestData: RecipeWithFood): Result<RecipeMultipleResponse> {
        return try {
            val response: HttpResponse = httpClient.post("$baseUrl/recipes-batch/generate-multiple") {
                contentType(ContentType.Application.Json)
                setBody(requestData)
            }

            if (response.status.isSuccess()) {
                val recipeResponse = response.body<RecipeMultipleResponse>()
                Result.success(recipeResponse)
            } else {
                Result.failure(Exception("Erreur lors de la génération de recette: ${response.status}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getMultipleRecipeWithoutFood(requestData: RecipeForShoplist): Result<RecipeMultipleResponse> {
        return try {
            val response: HttpResponse = httpClient.post("${baseUrl}/recipes-batch/generate-multiple-without-ingredients") {
                contentType(ContentType.Application.Json)
                setBody(requestData)
            }

            if (response.status.isSuccess()) {
                val recipeResponse = response.body<RecipeMultipleResponse>()
                Result.success(recipeResponse)
            } else {
                Result.failure(Exception("Erreur lors de la génération de recette: ${response.status}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    fun close() {
        httpClient.close()
    }
}