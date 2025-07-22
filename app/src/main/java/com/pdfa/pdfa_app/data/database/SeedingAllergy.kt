package com.pdfa.pdfa_app.data.SeedingAllergy

import android.content.Context
import android.util.Log
import com.pdfa.pdfa_app.data.dao.AllergyDao
import com.pdfa.pdfa_app.data.model.AllergySeed
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class SeedingAllergy(
    private val context: Context,
    private val allergyDao: AllergyDao
) {
    companion object {
        private const val TAG = "SeedingAllergy"
        private const val ALLERGY_JSON_FILE = "allergies.json"
    }

    fun seed() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                Log.d(TAG, "🌱 Seeding begin...")
                
                // Seeding des allergies
                seedAllergies()
                
                Log.d(TAG, "✅ Seeding win!")
            } catch (e: Exception) {
                Log.e(TAG, "❌ Error during seeding: ${e.message}", e)
            }
        }
    }

    private suspend fun seedAllergies() {
        try {
            Log.d(TAG, "🚨 Seeding allergies...")
            val jsonString = loadJsonFromAssets(ALLERGY_JSON_FILE)
            val allergyList = parseJsonData<List<AllergySeed>>(jsonString)
            
            withContext(Dispatchers.IO) {
                Log.d(TAG, "Inserting ${allergyList.size} allergies...")
                allergyList.map { it.toEntity() }.forEach { allergy ->
                    allergyDao.insertAllergy(allergy)
                }
                Log.d(TAG, "✅ Allergies inserted successfully!")
            }
        } catch (e: Exception) {
            Log.e(TAG, "❌ Error seeding allergies: ${e.message}", e)
            throw e
        }
    }

    private fun loadJsonFromAssets(fileName: String): String {
        return context.assets.open(fileName).bufferedReader().use { it.readText() }
    }

    private inline fun <reified T> parseJsonData(jsonString: String): T {
        val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
        }
        return json.decodeFromString(jsonString)
    }
}