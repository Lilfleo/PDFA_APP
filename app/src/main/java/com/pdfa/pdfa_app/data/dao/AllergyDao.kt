package com.pdfa.pdfa_app.data.dao

import androidx.room.*
import com.pdfa.pdfa_app.data.model.Allergy
import com.pdfa.pdfa_app.data.model.AllergyWithFood
import com.pdfa.pdfa_app.data.model.Food
import kotlinx.coroutines.flow.Flow

@Dao
interface AllergyDao {
    @Insert
    suspend fun insertAllergy(allergy: Allergy)
 
    @Transaction
    @Query("SELECT * FROM allergy")
    fun getAllergiesWithFood(): Flow<List<AllergyWithFood>>
}
