package com.pdfa.pdfa_app.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.pdfa.pdfa_app.data.model.DietPreference
import com.pdfa.pdfa_app.data.model.DietPreferenceWithDiet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@Dao
interface DietPreferenceDao {
    @Transaction
    @Query("SELECT * FROM diet_preference")
    fun getAllDietPreference(): Flow<List<DietPreferenceWithDiet>>

    @Insert
    suspend fun insertDietPreference(diet: DietPreference)

    @Delete
    suspend fun deleteDietPreference(diet: DietPreference)
}