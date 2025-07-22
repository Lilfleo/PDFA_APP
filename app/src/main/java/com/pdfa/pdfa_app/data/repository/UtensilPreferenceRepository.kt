package com.pdfa.pdfa_app.data.repository

import com.pdfa.pdfa_app.data.dao.UtensilPreferenceDao
import com.pdfa.pdfa_app.data.model.UtensilPreference
import com.pdfa.pdfa_app.data.model.UtensilPreferenceWithUtensil
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UtensilPreferenceRepository @Inject constructor(
    private val dao: UtensilPreferenceDao
) {
    val allUtensilPreferences : Flow<List<UtensilPreferenceWithUtensil>> = dao.getAllUtensilPreferences()

    suspend fun insertUtensilPreference(utensilPreference: UtensilPreference) {
        dao.insertUtensilPreference(utensilPreference)
    }

    suspend fun deleteUtensilPreference(utensilPreference: UtensilPreference) {
        dao.deleteUtensilPreference(utensilPreference)
    }
}