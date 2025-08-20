package com.pdfa.pdfa_app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "profil")
data class Profil (
    @PrimaryKey ( autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val age: Int
)