package com.pdfa.pdfa_app.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.pdfa.pdfa_app.data.model.Diet
import kotlinx.coroutines.flow.Flow

@Dao
interface DietDao {
    @Query("SELECT * FROM diet")
    fun getAllDiet(): Flow<List<Diet>>

    @Insert
    suspend fun insert(diet: Diet)

    @Update
    suspend fun update(diet: Diet)

    @Delete
    suspend fun delete(diet: Diet)
}