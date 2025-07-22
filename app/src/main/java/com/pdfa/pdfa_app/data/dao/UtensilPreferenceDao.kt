package com.pdfa.pdfa_app.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.pdfa.pdfa_app.data.model.Utensil
import com.pdfa.pdfa_app.data.model.UtensilPreference
import com.pdfa.pdfa_app.data.model.UtensilPreferenceWithUtensil
import kotlinx.coroutines.flow.Flow

@Dao
interface UtensilPreferenceDao {
    @Transaction
    @Query("SELECT * FROM utensil_preference")
    fun getAllUtensilPreferences(): Flow<List<UtensilPreferenceWithUtensil>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUtensilPreference(utensilPreference: UtensilPreference): Long

    @Delete
    suspend fun deleteUtensilPreference(utensil: UtensilPreference)
}