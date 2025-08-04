package com.pdfa.pdfa_app.data.database

import android.content.Context
import android.util.Log
import com.pdfa.pdfa_app.data.model.Allergy
import com.pdfa.pdfa_app.data.model.Food
import com.pdfa.pdfa_app.data.model.Recipe
import com.pdfa.pdfa_app.data.model.Tag
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import java.util.Calendar
import java.util.Date

class DatabaseSeeder(
    private val db: AppDatabase,
    private val context: Context
) {
    companion object {
        private const val TAG = "DatabaseSeeder"
        private const val JSON_FILE_NAME = "food_100.json"
    }

    suspend fun seedDev() = withContext(Dispatchers.IO) {
        Log.d(TAG, "Starting development seed...")

        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_MONTH, 30)
        val expirationDate = calendar.time

        val carrotId: Long
        val tomatoId: Long

        db.foodDao().apply {
            carrotId = insertFood(
                Food(
                    name = "Carrot",
                    link = "https://example.com/carrot",
                    caloriesPerKg = 410,
                    caloriesPerUnit = 41,
                    expirationTime = expirationDate
                )
            )
            tomatoId = insertFood(
                Food(
                    name = "Tomato",
                    link = "https://example.com/tomato",
                    caloriesPerKg = 180,
                    caloriesPerUnit = 18,
                    expirationTime = expirationDate
                )
            )
            insertFood(
                Food(
                    name = "Pepper",
                    link = "https://example.com/pepper",
                    caloriesPerKg = 200,
                    caloriesPerUnit = 20,
                    expirationTime = expirationDate
                )
            )
        }

        // Use Int for food IDs as per your Allergy model
        db.allergyDao().apply {
            insertAllergy(Allergy(foodId = carrotId.toInt()))
            insertAllergy(Allergy(foodId = tomatoId.toInt()))
        }

        db.recipeDao().apply {
            insertRecipe(
                Recipe(
                    name = "Carrottes au thon",
                    description = "Plein de chose a faire",
                    totalCalories = 1000,
                    createdAt = Date()
                )
            )
        }

        db.tagDao().apply {
            insertTag(
                Tag(
                    name = "mexicain",
                    color = "blue"
                )
            )
        }
        seed()
        Log.d(TAG, "Development seed completed ✅")

    }

    suspend fun seed() {
        Log.d(TAG, "Seeding begin...")

        try {
            val jsonString = loadJsonFromAssets()
            val foodList = parseJsonData(jsonString)
            insertSeedData(foodList)
            Log.d(TAG, "Seeding completed ! ✅")
        } catch (e: Exception) {
            Log.e(TAG, "Seeding failed: ${e.message}", e)
            throw e
        }
    }

    private suspend fun loadJsonFromAssets(): String = withContext(Dispatchers.IO) {
        return@withContext try {
            context.assets.open(JSON_FILE_NAME).bufferedReader().use { it.readText() }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to load JSON from assets: ${e.message}")
            throw e
        }
    }

    private fun parseJsonData(jsonString: String): List<Food> {
        val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
            serializersModule = SerializersModule {
                contextual(DateSerializer)
            }
        }
        return try {
            json.decodeFromString(jsonString)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to parse JSON data: ${e.message}")
            throw e
        }
    }

    private suspend fun insertSeedData(foods: List<Food>) = withContext(Dispatchers.IO) {
        Log.d(TAG, "Inserting ${foods.size} food...")

        try {
            foods.forEach { food ->
                db.foodDao().apply {
                    insertFood(food)
                }
            }
            Log.d(TAG, "Insertion success!")
        } catch (e: Exception) {
            Log.e(TAG, "Failed to insert seed data: ${e.message}")
            throw e
        }
    }
}