package com.pdfa.pdfa_app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "tag")
data class Tag (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val color: String
)