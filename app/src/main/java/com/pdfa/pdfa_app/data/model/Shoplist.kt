package com.pdfa.pdfa_app.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "shoplist",
    foreignKeys = [
        ForeignKey(
            entity = Food::class,
            parentColumns = ["id"],
            childColumns = ["food_id"],
            onDelete = ForeignKey.CASCADE
        )
    ])
data class Shoplist(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "food_id", index = true)
    val foodId: Int,
    @ColumnInfo(name = "quantity", index = true)
    val quantity: Int,
    @ColumnInfo(name = "quantity_type", index = true)
    val quantityType: String,
    val recipeId: List<Int>
)

