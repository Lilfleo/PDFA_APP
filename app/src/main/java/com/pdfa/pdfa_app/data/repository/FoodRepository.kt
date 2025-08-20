package com.pdfa.pdfa_app.data.repository


import com.pdfa.pdfa_app.data.dao.FoodDao
import com.pdfa.pdfa_app.data.model.Food
import kotlinx.coroutines.flow.Flow

class FoodRepository(private val foodDao: FoodDao) {
    val allFood: Flow<List<Food>> = foodDao.getAllFood()

    suspend fun insert(food: Food): Long = foodDao.insertFood(food)

    suspend fun delete(food: Food) = foodDao.deleteFood(food)

    suspend fun findByName(name: String): Food? = foodDao.findByName(name)

    suspend fun findByNameContaining(name: String): Food? = foodDao.findByNameContaining(name)

    suspend fun findById(id: Int): Food? = foodDao.findById(id)

}
