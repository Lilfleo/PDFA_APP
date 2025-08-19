package com.pdfa.pdfa_app.data.repository

import com.pdfa.pdfa_app.data.dao.DietDao
import com.pdfa.pdfa_app.data.model.Diet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DietRepository(private val dietDao: DietDao) {
    fun getAllDiets(): Flow<List<Diet>> = dietDao.getAllDiet()

    fun getAllDietNames(): Flow<List<String>> =
        dietDao.getAllDiet().map { diets ->
            diets.map { it.name }
        }

    suspend fun insert(diet: Diet) = dietDao.insert(diet)

    suspend fun update(diet: Diet) = dietDao.update(diet)

    suspend fun delete(diet: Diet) = dietDao.delete(diet)
}
