package com.pdfa.pdfa_app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "utensil")
data class Utensil (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
)

// Ajouter cette classe ici
@Serializable
data class UtensilFromJson(
    val name: String
)
