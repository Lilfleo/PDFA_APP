package com.pdfa.pdfa_app.data.repository

import com.pdfa.pdfa_app.data.dao.DietDao
import com.pdfa.pdfa_app.data.model.Diet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DietRepository(private val dao: DietDao) {
    fun getAllDiets(): Flow<List<Diet>> = dao.getAllDiet()

    fun getAllDietNames(): Flow<List<String>> =
        dao.getAllDiet().map { diets ->
            diets.map { it.name }
        }

    suspend fun insert(diet: Diet) = dao.insert(diet)

    suspend fun update(diet: Diet) = dao.update(diet)

    suspend fun delete(diet: Diet) = dao.delete(diet)
}
