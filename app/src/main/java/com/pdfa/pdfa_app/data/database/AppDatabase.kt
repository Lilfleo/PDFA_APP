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
import com.pdfa.pdfa_app.data.model.Allergy
import com.pdfa.pdfa_app.data.model.FoodDetail


@Database(entities = [Food::class, Allergy::class, FoodDetail::class], version = 2)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun foodDao(): FoodDao
    abstract fun allergyDao(): AllergyDao
    abstract fun foodDetailDao(): FoodDetailDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_db"
                )
                .addCallback(
                    createCallback(
                        {getInstance(context).foodDao()},
                        {getInstance(context).allergyDao()},
                    )
                )
                .fallbackToDestructiveMigration()
                .build().also { INSTANCE = it }
            }

        fun createCallback(foodDaoProvider: () -> FoodDao, allergyDaoProvider: () -> AllergyDao) = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)

                // Launch coroutine to insert data
                CoroutineScope(Dispatchers.IO).launch {
                    foodDaoProvider().apply {
                        insertFood(Food(name = "Carrot", link = "https://example.com/carrot", caloriesPerKg = 410, caloriesPerUnit = 41, expirationTime = Date()))
                        insertFood(Food(name = "Tomato", link = "https://example.com/tomato", caloriesPerKg = 180, caloriesPerUnit = 18, expirationTime = Date()))
                        insertFood(Food(name = "Pepper", link = "https://example.com/pepper", caloriesPerKg = 200, caloriesPerUnit = 20, expirationTime = Date()))
                    }
                    allergyDaoProvider().apply {
                        insertAllergy(Allergy(foodId = 1))
                        insertAllergy(Allergy(foodId = 2))
                    }
                }
            }
        }
    }
}
