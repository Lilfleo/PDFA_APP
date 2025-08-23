package com.pdfa.pdfa_app.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class ShoplistWithFood(
    @Embedded
    val shoplist: Shoplist,

    @Relation(
        parentColumn = "food_id",
        entityColumn = "id"
    )val food: Food
)