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

@Database(entities = [Food::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun foodDao(): FoodDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_db"
                ).build().also { INSTANCE = it }
            }

        fun createCallback(foodDaoProvider: () -> FoodDao) = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)

                // Launch coroutine to insert data
                CoroutineScope(Dispatchers.IO).launch {
                    foodDaoProvider().apply {
                        insertFood(Food(name = "Carrot", link = "https://example.com/carrot", caloriesPerKg = 410, caloriesPerUnit = 41, expirationTime = Date()))
                        insertFood(Food(name = "Tomato", link = "https://example.com/tomato", caloriesPerKg = 180, caloriesPerUnit = 18, expirationTime = Date()))
                        insertFood(Food(name = "Pepper", link = "https://example.com/pepper", caloriesPerKg = 200, caloriesPerUnit = 20, expirationTime = Date()))
                    }
                }
            }
        }
    }
}
