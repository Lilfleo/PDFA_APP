package com.pdfa.pdfa_app.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.pdfa.pdfa_app.data.model.Allergy
import com.pdfa.pdfa_app.data.model.AllergyWithFood
import com.pdfa.pdfa_app.data.model.TagPreference
import com.pdfa.pdfa_app.data.model.TagPreferenceWithTag
import kotlinx.coroutines.flow.Flow

@Dao
interface TagPreferenceDao {
    @Insert
    suspend fun insertTagPreference(tagPreference: TagPreference)

    @Transaction
    @Query("SELECT * FROM tag_preference")
    fun getTagPreferences(): Flow<List<TagPreferenceWithTag>>

    @Delete
    suspend fun deleteTagPreference(tagPreference: TagPreference)
}