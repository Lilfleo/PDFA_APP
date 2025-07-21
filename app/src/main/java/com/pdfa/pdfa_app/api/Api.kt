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

class ApiClient {
//    private val baseUrl = "http://10.0.2.2:8000"
    private val baseUrl = "http://yep-ia.onrender.com"


    private val httpClient = HttpClient(Android) {
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

        install(HttpTimeout) {
            requestTimeoutMillis = 30000
            connectTimeoutMillis = 15000
            socketTimeoutMillis = 15000
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


    fun close() {
        httpClient.close()
    }
}