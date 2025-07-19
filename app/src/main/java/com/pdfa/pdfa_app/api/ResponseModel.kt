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
    val unusedIngredients: Array<String>
)

@Serializable
data class Recipe(
    val titre: String,
    val preparationTime: String,
    val totalCookingTime: String,
    val tags: Array<String>,
    val ingredients: Array<Ingredient>,
    val steps: Array<String>
)

