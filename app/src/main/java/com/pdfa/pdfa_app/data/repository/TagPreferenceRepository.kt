package com.pdfa.pdfa_app.data.repository

import com.pdfa.pdfa_app.data.dao.AllergyDao
import com.pdfa.pdfa_app.data.dao.TagPreferenceDao
import com.pdfa.pdfa_app.data.model.Allergy
import com.pdfa.pdfa_app.data.model.AllergyWithFood
import com.pdfa.pdfa_app.data.model.TagPreference
import com.pdfa.pdfa_app.data.model.TagPreferenceWithTag
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TagPreferenceRepository @Inject constructor(
    private val dao: TagPreferenceDao
) {
    val allTagPreferences : Flow<List<TagPreferenceWithTag>> = dao.getTagPreferences()

    suspend fun insertTagPreference(tagPreference: TagPreference) {
        dao.insertTagPreference(tagPreference)
    }

    suspend fun deleteTagPreference(tagPreference: TagPreference) {
        dao.deleteTagPreference(tagPreference)
    }
}