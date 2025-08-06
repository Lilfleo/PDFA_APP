package com.pdfa.pdfa_app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.Calendar
import java.util.Date

@Serializable
@Entity(tableName = "food")
data class Food(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val link: String,
    val caloriesPerKg: Int,
    val caloriesPerUnit: Int,
    @Contextual val expirationTime: Date = run {
        Calendar.getInstance().apply {
            add(Calendar.DAY_OF_MONTH, 7)
        }.time
    }
)