package com.pdfa.pdfa_app.di

import android.content.Context
import androidx.room.Room

import com.pdfa.pdfa_app.data.dao.*
import com.pdfa.pdfa_app.data.database.AppDatabase
import com.pdfa.pdfa_app.data.repository.AllergyRepository
import com.pdfa.pdfa_app.data.repository.FoodDetailRepository
import com.pdfa.pdfa_app.data.repository.FoodRepository
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
    fun provideDatabase(@ApplicationContext app: Context, foodDaoProvider: Provider<FoodDao>, allergyDaoProvider: Provider<AllergyDao>): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            "app_db"
        )
            .addCallback(AppDatabase.createCallback({foodDaoProvider.get()},{allergyDaoProvider.get()}, app))
            .build()
    }

    @Provides
    fun provideFoodDao(db: AppDatabase): FoodDao = db.foodDao()

    @Provides
    @Singleton
    fun provideFoodRepository(dao: FoodDao): FoodRepository = FoodRepository(dao)

    @Provides
    fun provideAllergyDao(db: AppDatabase): AllergyDao = db.allergyDao()

    @Provides
    @Singleton
    fun provideAllergyRepository(dao: AllergyDao): AllergyRepository = AllergyRepository(dao)

    @Provides
    fun provideFoodDetailDao(db: AppDatabase): FoodDetailDao = db.foodDetailDao()

    @Provides
    @Singleton
    fun provideFoodDetailRepository(dao: FoodDetailDao): FoodDetailRepository = FoodDetailRepository(dao)
}
