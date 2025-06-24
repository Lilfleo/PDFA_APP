package com.example.pdfa_app.di

import android.content.Context
import androidx.room.Room
import com.example.pdfa_app.data.dao.FoodDao
import com.example.pdfa_app.data.database.AppDatabase
import com.example.pdfa_app.data.repository.FoodRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext app: Context, foodDaoProvider: Provider<FoodDao>): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            "app_db"
        )
            .addCallback(AppDatabase.createCallback { foodDaoProvider.get() })
            .build()
    }

    @Provides
    fun provideFoodDao(db: AppDatabase): FoodDao = db.foodDao()

    @Provides
    @Singleton
    fun provideFoodRepository(dao: FoodDao): FoodRepository = FoodRepository(dao)
}
