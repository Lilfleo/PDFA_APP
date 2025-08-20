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
import com.pdfa.pdfa_app.data.dao.DietDao
import com.pdfa.pdfa_app.data.dao.DietPreferenceDao
import com.pdfa.pdfa_app.data.dao.FoodDetailDao
import com.pdfa.pdfa_app.data.dao.FoodRecipeCrossRefDao
import com.pdfa.pdfa_app.data.dao.ProfilDao
import com.pdfa.pdfa_app.data.dao.RecipeDao
import com.pdfa.pdfa_app.data.dao.RecipeTagCrossRefDao
import com.pdfa.pdfa_app.data.dao.ShoplistDao
import com.pdfa.pdfa_app.data.dao.TagDao
import com.pdfa.pdfa_app.data.dao.TagPreferenceDao
import com.pdfa.pdfa_app.data.dao.UtensilDao
import com.pdfa.pdfa_app.data.dao.UtensilPreferenceDao
import com.pdfa.pdfa_app.data.model.Allergy
import com.pdfa.pdfa_app.data.model.FoodDetail
import com.pdfa.pdfa_app.data.model.FoodRecipeCrossRef
import com.pdfa.pdfa_app.data.model.Recipe
import com.pdfa.pdfa_app.data.model.RecipeTagCrossRef
import com.pdfa.pdfa_app.data.model.Tag
import com.pdfa.pdfa_app.data.model.TagPreference
import com.pdfa.pdfa_app.data.model.Utensil
import com.pdfa.pdfa_app.data.model.UtensilPreference
import dagger.hilt.android.qualifiers.ApplicationContext
import com.pdfa.pdfa_app.data.model.Diet
import com.pdfa.pdfa_app.data.model.DietPreference
import com.pdfa.pdfa_app.data.model.Profil
import com.pdfa.pdfa_app.data.model.Shoplist

@Database(entities = [
    Food::class,
    Allergy::class,
    FoodDetail::class,
    Tag::class,
    Recipe::class,
    RecipeTagCrossRef::class,
    FoodRecipeCrossRef::class,
    TagPreference::class,
    Utensil::class,
    UtensilPreference::class,
    Diet::class,
    DietPreference::class,
    Shoplist::class,
    Profil::class],
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
    abstract fun utensilDao(): UtensilDao
    abstract fun utensilPreferenceDao(): UtensilPreferenceDao
    abstract fun dietDao(): DietDao
    abstract fun dietPreferenceDao(): DietPreferenceDao
    abstract fun shoplistDao(): ShoplistDao
    abstract fun profilDao(): ProfilDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_db"
                )
                    .addCallback(createCallback(context.applicationContext))
                    .fallbackToDestructiveMigration(true)
                    .build().also { INSTANCE = it }
            }

        private fun createCallback(ctx: Context) = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                // Launch coroutine to insert data
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val database = INSTANCE ?: return@launch
                        val dbSeeder = DatabaseSeeder(database, ctx)
                        dbSeeder.seedDev()

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }
}
