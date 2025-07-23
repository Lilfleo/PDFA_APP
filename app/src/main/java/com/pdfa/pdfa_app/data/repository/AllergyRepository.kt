package com.pdfa.pdfa_app.data.repository


import com.pdfa.pdfa_app.data.dao.AllergyDao
import com.pdfa.pdfa_app.data.model.Allergy
import com.pdfa.pdfa_app.data.model.AllergyWithFood
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AllergyRepository @Inject constructor(
    private val dao: AllergyDao
) {
    val allAllergy : Flow<List<AllergyWithFood>> = dao.getAllergiesWithFood()

    suspend fun insert(allergy: Allergy) {
        dao.insertAllergy(allergy)
    }
    suspend fun deleteAllergy(allergy: Allergy) {
        dao.deleteAllergy(allergy)
    }
}
