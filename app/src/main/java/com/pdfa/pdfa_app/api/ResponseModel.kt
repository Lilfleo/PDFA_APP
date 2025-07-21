package com.pdfa.pdfa_app.api

import kotlinx.serialization.Serializable


//Tests Hello Wolrd
@Serializable
data class HelloResponse(
    val Hello: String
)

//Recette Reponse
@Serializable
data class RecipeResponse(
    val recipe : Recipe,
    val unusedIngredients: List<String>
)

@Serializable
data class Recipe(
    val title: String,
    val preparationTime: String,
    val totalCookingTime: String,
    val tags: List<String>,
    val ingredients: List<Ingredient>,
    val steps: List<String>
)

