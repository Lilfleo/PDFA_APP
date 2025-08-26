package com.pdfa.pdfa_app.data.database

import android.content.Context
import android.util.Log
import com.pdfa.pdfa_app.data.model.Allergy
import com.pdfa.pdfa_app.data.model.Cookbook
import com.pdfa.pdfa_app.data.model.Diet
import com.pdfa.pdfa_app.data.model.Food
import com.pdfa.pdfa_app.data.model.Profil
import com.pdfa.pdfa_app.data.model.Recipe
import com.pdfa.pdfa_app.data.model.Tag
import com.pdfa.pdfa_app.data.model.Utensil
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
        private const val FOOD_FILE_NAME = "data/food_100.json"
        private const val UTENSILS_FILE_NAME = "data/utensils.json"
        private const val TAGS_FILE_NAME = "data/tags.json"
        private const val DIET_FILE_NAME = "data/diet.json"
        private const val PROFIL_FILE_NAME = "data/profil.json"
        private const val COOKBOOK_FILE_NAME = "data/cookbook.json"
    }

    suspend fun seedDev() = withContext(Dispatchers.IO) {
        Log.d(TAG, "Starting development seed...")

        try {
            // seed prod first
            seed()

            // seed some extra data
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DAY_OF_MONTH, 30)
            val expirationDate = calendar.time

//            // Insert foods and get their IDs
//            val carrotId = db.foodDao().insertFood(
//                Food(
//                    name = "Carrot",
//                    link = "https://example.com/carrot",
//                    caloriesPerKg = 410,
//                    caloriesPerUnit = 41,
//                    expirationTime = expirationDate
//                )
//            )
//
//            val tomatoId = db.foodDao().insertFood(
//                Food(
//                    name = "Tomato",
//                    link = "https://example.com/tomato",
//                    caloriesPerKg = 180,
//                    caloriesPerUnit = 18,
//                    expirationTime = expirationDate
//                )
//            )
//
//            db.foodDao().insertFood(
//                Food(
//                    name = "Pepper",
//                    link = "https://example.com/pepper",
//                    caloriesPerKg = 200,
//                    caloriesPerUnit = 20,
//                    expirationTime = expirationDate
//                )
//            )

//            // Insert allergies using the food IDs
//            val allergies = listOf(
//                Allergy(foodId = carrotId.toInt()),
//                Allergy(foodId = tomatoId.toInt())
//            )
//            allergies.forEach { allergy ->
//                db.allergyDao().insertAllergy(allergy)
//            }

//            // Insert recipe
//            db.recipeDao().insertRecipe(
//                Recipe(
//                    name = "Carrottes au thon",
//                    description = "Plein de chose a faire",
//                    totalCalories = 1000,
//                    createdAt = Date()
//                )
//            )

            Log.d(TAG, "Development seed completed ✅")

        } catch (e: Exception) {
            Log.e(TAG, "Development seed failed: ${e.message}", e)
            throw e
        }
    }

    suspend fun seed() {
        Log.d(TAG, "Seeding begin...")

        try {
            val foodString = loadFoodFromAssets()
            val utensilString = loadUtensilsFromAssets()
            val tagString = loadTagsFromAssets()
            val dietSting = loadDietsFromAssets()
            val profilString = loadProfilFromAssets()
            val cookbookString = loadCookbookAssets()

            val foodList: List<Food> = parseJsonData(foodString)
            val utensilsList: List<Utensil> = parseJsonData(utensilString)
            val tagList : List<Tag> = parseJsonData(tagString)
            val dietList: List<Diet> = parseJsonData(dietSting)
            val profilList: List<Profil> = parseJsonData(profilString)
            val cookbookList: List<Cookbook> = parseJsonData(cookbookString)

            insertSeedData(foodList, utensilsList, tagList, dietList, profilList, cookbookList
            )
            Log.d(TAG, "Seeding completed ! ✅")
        } catch (e: Exception) {
            Log.e(TAG, "Seeding failed: ${e.message}", e)
            throw e
        }
    }

    private suspend fun loadFoodFromAssets(): String = withContext(Dispatchers.IO) {
        try {
            context.assets.open(FOOD_FILE_NAME).bufferedReader().use { it.readText() }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to load food JSON from assets: ${e.message}")
            throw e
        }
    }

    private suspend fun loadUtensilsFromAssets(): String = withContext(Dispatchers.IO) {
        try {
            context.assets.open(UTENSILS_FILE_NAME).bufferedReader().use { it.readText() }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to load utensils JSON from assets: ${e.message}")
            throw e
        }
    }

    private suspend fun loadTagsFromAssets(): String = withContext(Dispatchers.IO) {
        try {
            context.assets.open(TAGS_FILE_NAME).bufferedReader().use { it.readText() }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to load tags JSON from assets: ${e.message}")
            throw e
        }
    }

    private suspend fun loadDietsFromAssets(): String = withContext(Dispatchers.IO) {
        try {
            context.assets.open(DIET_FILE_NAME).bufferedReader().use { it.readText() }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to load diet JSON from assets: ${e.message}")
            throw e
        }
    }

    private suspend fun loadProfilFromAssets(): String = withContext(Dispatchers.IO) {
        try {
            context.assets.open(PROFIL_FILE_NAME).bufferedReader().use { it.readText() }
        } catch (e: Exception) {
            Log.e(TAG,"Failed to load profil JSON from assets: ${e.message}" )
            throw e
        }
    }

    private suspend fun loadCookbookAssets(): String = withContext(Dispatchers.IO) {
        try {
            context.assets.open(COOKBOOK_FILE_NAME).bufferedReader().use { it.readText() }
        } catch (e: Exception) {
            Log.e(TAG,"Failed to load cookbook JSON from assets: ${e.message}" )
            throw e
        }
    }

    private inline fun <reified T : Any> parseJsonData(jsonString: String): List<T> {
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
            Log.e(TAG, "Failed to parse JSON data for ${T::class.simpleName}: ${e.message}")
            throw e
        }
    }

    private suspend fun insertSeedData(foods: List<Food>, utensils: List<Utensil>, tags: List<Tag>, diets: List<Diet>, profils: List<Profil>, cookbooks: List<Cookbook>) = withContext(Dispatchers.IO) {
        Log.d(TAG, "Inserting ${foods.size} foods, ${utensils.size} utensils, ${tags.size} tags and ${profils.size}")

        try {
            // Use batch insertion if available, otherwise insert individually
            if (foods.isNotEmpty()) {
                foods.forEach { food ->
                    try {
                            db.foodDao().insertFood(food)
                    } catch (insertException: Exception) {
                        Log.w(TAG, "Failed to insert food: ${food.name}, error: ${insertException.message}")
                    }
                }
            }

            if (utensils.isNotEmpty()) {
                utensils.forEach { utensil ->
                    try {
                        db.utensilDao().insertUtensil(utensil)
                    } catch (insertException: Exception) {
                        Log.w(TAG, "Failed to insert utensil: ${utensil.name}, error: ${insertException.message}")
                    }
                }
            }

            if (tags.isNotEmpty()) {
                tags.forEach { tag ->
                    try {
                        db.tagDao().insertTag(tag)
                    } catch (insertException: Exception) {
                        Log.w(TAG, "Failed to insert utensil: ${tag.name}, error: ${insertException.message}")
                    }
                }
            }

            if (diets.isNotEmpty()) {
                diets.forEach { diet ->
                    try {
                        db.dietDao().insert(diet)
                    } catch (insertException: Exception) {
                        Log.w(TAG, "Failed to insert diet: ${diet.name}, error: ${insertException.message}")
                    }
                }
            }

            if (profils.isNotEmpty()) {
                profils.forEach { profil ->
                    try {
                        db.profilDao().insertProfil(profil)
                    } catch (insertException: Exception) {
                        Log.w(TAG, "Failed to insert profil: ${profil.name}, error: ${insertException.message}")
                    }
                }
            }

            if (cookbooks.isNotEmpty()) {
                cookbooks.forEach { cookbook ->
                    try {
                        db.cookbookDao().insertCookbook(cookbook)
                    } catch (insertException: Exception) {
                        Log.w(TAG, "Failed to insert cookbook: ${cookbook.name}, error: ${insertException.message}")

                    }
                }
            }

            Log.d(TAG, "Seed data insertion completed!")
        } catch (e: Exception) {
            Log.e(TAG, "Failed to insert seed data: ${e.message}", e)
            throw e
        }
    }
}