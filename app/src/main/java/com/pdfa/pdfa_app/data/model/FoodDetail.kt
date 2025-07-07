package com.pdfa.pdfa_app.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "food_detail",
    foreignKeys = [
        ForeignKey(
            entity = Food::class,
            parentColumns = ["id"],
            childColumns = ["food_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)

data class FoodDetail(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "food_id", index = true)
    val foodId: Int,
    val quantity: Int,
    @ColumnInfo(name = "is_weight")
    val isWeight: Boolean,
    val price: Int,
    @ColumnInfo(name = "buying_time")
    val buyingTime: Date,
    @ColumnInfo(name = "expiration_time")
    val expirationTime: Date
)
