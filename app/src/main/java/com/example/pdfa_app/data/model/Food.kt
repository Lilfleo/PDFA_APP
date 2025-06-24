package com.example.pdfa_app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "food")
data class Food(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val link: String,
    val caloriesPerKg: Int,
    val caloriesPerUnit: Int,
    val expirationTime: Date
)