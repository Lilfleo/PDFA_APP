package com.pdfa.pdfa_app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "utensil")
data class Utensil (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
)