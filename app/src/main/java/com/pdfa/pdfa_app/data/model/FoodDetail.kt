package com.pdfa.pdfa_app.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
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
    ],
    indices = [Index(value = ["food_id"], unique = true)] // ✅ garantit un seul détail par aliment
)


data class FoodDetail(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "food_id")
    val foodId: Int,
    val quantity: Float,
    @ColumnInfo(name = "is_weight")
    val isWeight: Boolean,
    val price: Float?,
    @ColumnInfo(name = "buying_time")
    val buyingTime: Date,
    @ColumnInfo(name = "expiration_time")
    val expirationTime: Date
)
