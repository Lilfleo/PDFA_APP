package com.pdfa.pdfa_app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pdfa.pdfa_app.api.Ingredient
import com.pdfa.pdfa_app.api.Tags
import java.util.Date

@Entity(tableName = "recipe")
data class Recipe(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val subTitle: String,
    val preparationTime: String,
    val totalCookingTime: String,
    val tags: Tags,
    val ingredients: List<Ingredient>,
    val steps: List<String>
)
