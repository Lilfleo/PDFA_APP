package com.pdfa.pdfa_app.data.repository

import com.pdfa.pdfa_app.data.dao.DietPreferenceDao
import com.pdfa.pdfa_app.data.model.DietPreference
import com.pdfa.pdfa_app.data.model.DietPreferenceWithDiet
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DietPreferenceRepository @Inject constructor(
    private val dao: DietPreferenceDao
){
    val allDietPreferences : Flow<List<DietPreferenceWithDiet>> = dao.getAllDietPreference()

    suspend fun insetDietPreference(dietPreference: DietPreference) {
        dao.insertDietPreference(dietPreference)
    }

    suspend fun deleteDietPreference(dietPreference: DietPreference) {
        dao.deleteDietPreference(dietPreference)
    }
}