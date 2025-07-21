package com.pdfa.pdfa_app.data.database

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.pdfa.pdfa_app.data.model.Food
import com.pdfa.pdfa_app.data.dao.FoodDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date
import com.pdfa.pdfa_app.data.dao.AllergyDao
import com.pdfa.pdfa_app.data.dao.FoodDetailDao
import com.pdfa.pdfa_app.data.dao.FoodRecipeCrossRefDao
import com.pdfa.pdfa_app.data.dao.RecipeDao
import com.pdfa.pdfa_app.data.dao.RecipeTagCrossRefDao
import com.pdfa.pdfa_app.data.dao.TagDao
import com.pdfa.pdfa_app.data.dao.TagPreferenceDao
import com.pdfa.pdfa_app.data.model.Allergy
import com.pdfa.pdfa_app.data.model.FoodDetail
import com.pdfa.pdfa_app.data.model.FoodRecipeCrossRef
import com.pdfa.pdfa_app.data.model.Recipe
import com.pdfa.pdfa_app.data.model.RecipeTagCrossRef
import com.pdfa.pdfa_app.data.model.Tag
import com.pdfa.pdfa_app.data.model.TagPreference


@Database(entities = [
    Food::class,
    Allergy::class,
    FoodDetail::class,
    Tag::class,
    Recipe::class,
    RecipeTagCrossRef::class,
    FoodRecipeCrossRef::class,
    TagPreference::class],
    version = 2)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun foodDao(): FoodDao
    abstract fun allergyDao(): AllergyDao
    abstract fun foodDetailDao(): FoodDetailDao
    abstract fun tagDao(): TagDao
    abstract fun recipeDao(): RecipeDao
    abstract fun recipeTagCrossRefDao(): RecipeTagCrossRefDao
    abstract fun foodRecipeCrossRefDao(): FoodRecipeCrossRefDao
    abstract fun tagPreferenceDao(): TagPreferenceDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_db"
                )
                    .addCallback(createCallback())
                    .fallbackToDestructiveMigration(true)
                    .build().also { INSTANCE = it }
            }

        private fun createCallback() = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)

                // Launch coroutine to insert data
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val database = INSTANCE ?: return@launch

                        val carrotId: Long
                        val tomatoId: Long

                        database.foodDao().apply {
                            carrotId = insertFood(
                                Food(
                                    name = "Carrot",
                                    link = "https://example.com/carrot",
                                    caloriesPerKg = 410,
                                    caloriesPerUnit = 41,
                                    expirationTime = Date()
                                )
                            )
                            tomatoId = insertFood(
                                Food(
                                    name = "Tomato",
                                    link = "https://example.com/tomato",
                                    caloriesPerKg = 180,
                                    caloriesPerUnit = 18,
                                    expirationTime = Date()
                                )
                            )
                            insertFood(
                                Food(
                                    name = "Pepper",
                                    link = "https://example.com/pepper",
                                    caloriesPerKg = 200,
                                    caloriesPerUnit = 20,
                                    expirationTime = Date()
                                )
                            )
                        }

                        database.allergyDao().apply {
                            insertAllergy(Allergy(foodId = carrotId.toInt()))
                            insertAllergy(Allergy(foodId = tomatoId.toInt()))
                        }

                        database.recipeDao().apply {
                            insertRecipe(
                                Recipe(
                                    name = "Carrottes au thon",
                                    description = "Plein de chose a faire",
                                    totalCalories = 1000,
                                    createdAt = Date()
                                )
                            )
                        }

                        database.tagDao().apply {
                            insertTag(
                                Tag(
                                    name = "mexicain",
                                    color = "blue"
                                )
                            )
                        }

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }
}