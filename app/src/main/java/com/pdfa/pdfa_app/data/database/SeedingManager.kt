package com.pdfa.pdfa_app.data.SeedingManager

import android.content.Context
import android.util.Log
import com.pdfa.pdfa_app.data.dao.FoodDao
import com.pdfa.pdfa_app.data.model.FoodSeed
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class SeedingManager(
    private val context: Context,
    private val foodDao: FoodDao
) {

    companion object {
        private const val TAG = "SeedingManager"
        private const val JSON_FILE_NAME = "food_100.json"
    }

   fun seed() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                Log.d(TAG, "Seeding begin...")

                val jsonString = loadJsonFromAssets(JSON_FILE_NAME)
                val foodList = parseJsonData(jsonString)

                insertSeedData(foodList)

                Log.d(TAG, "Seeding win ! ✅")
            } catch (e: Exception) {
                Log.e(TAG, "Error : ${e.message}", e)
            }
        }
    }

    private fun loadJsonFromAssets(fileName: String): String {
        return context.assets.open(fileName).bufferedReader().use { it.readText() }
    }

    private fun parseJsonData(jsonString: String): List<FoodSeed> {
        val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
        }
        return json.decodeFromString(jsonString)
    }

    private suspend fun insertSeedData(foods: List<FoodSeed>) {
        withContext(Dispatchers.IO) {
            Log.d(TAG, "Push ${foods.size} aliments...")
            foods.map { it.toEntity() }.forEach { food ->
                foodDao.insertFood(food)
            }
            Log.d(TAG, "Push win!")
        }
    }
}
