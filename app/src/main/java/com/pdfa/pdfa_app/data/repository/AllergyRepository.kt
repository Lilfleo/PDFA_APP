package com.pdfa.pdfa_app.data.repository

import com.pdfa.pdfa_app.data.dao.AllergyDao
import com.pdfa.pdfa_app.data.model.Allergy
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AllergyRepository @Inject constructor(
    private val dao: AllergyDao
) {
    fun getAllAllergy(): Flow<List<Allergy>> = dao.getAllAllergy()

    suspend fun insert(allergy: Allergy) {
        dao.insert(allergy)
    }
}
