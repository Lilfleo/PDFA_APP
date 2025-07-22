package com.pdfa.pdfa_app.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pdfa.pdfa_app.data.model.Food
import com.pdfa.pdfa_app.data.model.Recipe
import com.pdfa.pdfa_app.data.model.Utensil
import kotlinx.coroutines.flow.Flow

@Dao
interface UtensilDao {

    @Query("SELECT * FROM utensil")
    fun getAllUtensils(): Flow<List<Utensil>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUtensil(utensil: Utensil): Long
}