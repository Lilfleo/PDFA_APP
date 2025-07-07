package com.pdfa.pdfa_app.data.model

import kotlinx.serialization.Serializable

@Serializable
data class FoodSeed(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val link: String,
    val caloriesPerKg: Int,
    val caloriesPerUnit: Int,
    val expirationTime: Date
){
    fun toEntity(): Food {
        return Food(
            id = id,
            name = name,
            pictureFileName = link,
            caloriesPerKg = caloriesPerKg,
            caloriesPerUnit = caloriesPerUnit,
            expirationTime = expiration_time
        )
    }
}