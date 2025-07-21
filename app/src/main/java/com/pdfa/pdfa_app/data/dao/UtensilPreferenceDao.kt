package com.pdfa.pdfa_app.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.pdfa.pdfa_app.data.model.Utensil
import com.pdfa.pdfa_app.data.model.UtensilPreferenceWithUtensil
import kotlinx.coroutines.flow.Flow

@Dao
interface UtensilPreferenceDao {
    @Transaction
    @Query("SELECT * FROM utensil_preference")
    fun getAllUtensils(): Flow<List<UtensilPreferenceWithUtensil>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUtensil(utensil: Utensil): Long

    @Delete
    suspend fun deleteUtensil(utensil: Utensil)
}