package com.pdfa.pdfa_app.data.model

import kotlinx.serialization.Serializable

@Serializable
data class AllergySeed(
    val foodId: Int
) {
    fun toEntity(): Allergy {
        return Allergy(foodId = foodId)
    }
}