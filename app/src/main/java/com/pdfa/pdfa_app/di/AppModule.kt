package com.pdfa.pdfa_app.di

import android.content.Context
import androidx.room.Room

import com.pdfa.pdfa_app.data.dao.*
import com.pdfa.pdfa_app.data.database.AppDatabase
import com.pdfa.pdfa_app.data.model.FoodRecipeCrossRef
import com.pdfa.pdfa_app.data.repository.AllergyRepository
import com.pdfa.pdfa_app.data.repository.CookbookRepository
import com.pdfa.pdfa_app.data.repository.DietRepository
import com.pdfa.pdfa_app.data.repository.FoodDetailRepository
import com.pdfa.pdfa_app.data.repository.FoodRepository
import com.pdfa.pdfa_app.data.repository.ProfilRepository
import com.pdfa.pdfa_app.data.repository.RecipeRepository
import com.pdfa.pdfa_app.data.repository.RecipeToShoplistService
import com.pdfa.pdfa_app.data.repository.ShoplistRepository
import com.pdfa.pdfa_app.data.repository.TagPreferenceRepository
import com.pdfa.pdfa_app.data.repository.UtensilPreferenceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext app: Context): AppDatabase {
        return AppDatabase.getInstance(app)
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
    fun provideFoodDetailRepository(dao: FoodDetailDao): FoodDetailRepository =
        FoodDetailRepository(dao)

    @Provides
    fun provideTagDao(db: AppDatabase): TagDao = db.tagDao()

    @Provides
    fun provideRecipeTagCrossRefDao(db: AppDatabase): RecipeTagCrossRefDao =
        db.recipeTagCrossRefDao()

    @Provides
    fun provideRecipeDao(db: AppDatabase): RecipeDao = db.recipeDao()

    @Provides
    @Singleton
    fun provideRecipeRepository(
        recipeDao: RecipeDao,
    ): RecipeRepository = RecipeRepository(recipeDao)

    @Provides
    fun provideFoodRecipeCrossRefDao(db: AppDatabase): FoodRecipeCrossRefDao =
        db.foodRecipeCrossRefDao()

    @Provides
    fun provideTagPreferenceDao(db: AppDatabase): TagPreferenceDao = db.tagPreferenceDao()

    @Provides
    @Singleton
    fun proviceTagPreferenceRepository(tagPreferenceDao: TagPreferenceDao): TagPreferenceRepository =
        TagPreferenceRepository(tagPreferenceDao)

    @Provides
    fun provideUtensilDao(db: AppDatabase): UtensilDao = db.utensilDao()

    @Provides
    fun provideUtensilPreferenceDao(db: AppDatabase): UtensilPreferenceDao =
        db.utensilPreferenceDao()

    @Provides
    @Singleton
    fun provideUtensilPreferenceRepository(utensilPreferenceDao: UtensilPreferenceDao): UtensilPreferenceRepository =
        UtensilPreferenceRepository(utensilPreferenceDao)

    @Provides
    fun provideDietDao(db: AppDatabase): DietDao = db.dietDao()

    @Provides
    @Singleton
    fun provideDietRepository(dietDao: DietDao): DietRepository = DietRepository(dietDao)

    @Provides
    fun provideDietPreferenceDao(db: AppDatabase): DietPreferenceDao = db.dietPreferenceDao()

    @Provides
    fun provideShoplistDao(db: AppDatabase): ShoplistDao = db.shoplistDao()

    @Provides
    @Singleton
    fun provideShoplistRepository(shoplistDao: ShoplistDao) = ShoplistRepository(shoplistDao)

    @Provides
    @Singleton
    fun provideRecipeToShoplistService(
        foodRepository: FoodRepository,
        shoplistRepository: ShoplistRepository
    ): RecipeToShoplistService {
        return RecipeToShoplistService(foodRepository, shoplistRepository)
    }

    @Provides
    fun provideProfilDao(db: AppDatabase): ProfilDao = db.profilDao()

    @Provides
    @Singleton
    fun provideProfilRepository(profilDao: ProfilDao) = ProfilRepository(profilDao)

    @Provides
    fun provideCookbookDao(db: AppDatabase): CookbookDao = db.cookbookDao()


    @Provides
    @Singleton
    fun provideCookbookRepository(cookbookDao: CookbookDao, recipeDao: RecipeDao) = CookbookRepository(cookbookDao, recipeDao)
}