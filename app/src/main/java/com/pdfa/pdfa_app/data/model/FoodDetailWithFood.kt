package com.pdfa.pdfa_app.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class FoodDetailWithFood(
    @Embedded
    val foodDetail: FoodDetail,

    @Relation(
        parentColumn = "food_id",
        entityColumn = "id"
    )
    val food: Food
)