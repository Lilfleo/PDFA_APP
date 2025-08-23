package com.pdfa.pdfa_app.data.repository

import com.pdfa.pdfa_app.data.dao.UtensilDao
import com.pdfa.pdfa_app.data.model.Utensil
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UtensilRepository @Inject constructor(
    private val utensilDao: UtensilDao
) {
    val allUtensils: Flow<List<Utensil>> = utensilDao.getAllUtensils()

    suspend fun insertUtensil(utensil: Utensil): Long {
        return utensilDao.insertUtensil(utensil)
    }
}
