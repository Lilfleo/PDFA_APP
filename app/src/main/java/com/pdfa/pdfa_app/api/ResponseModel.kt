package com.pdfa.pdfa_app.api

import kotlinx.serialization.Serializable


//Tests Hello Wolrd
@Serializable
data class HelloResponse(
    val Hello: String
)

//Recette Reponse
@Serializable
data class RecipeMultipleResponse(
    val recipes: List<Recipe>,
)

@Serializable
data class RecipeResponse(
    val recipe : Recipe,
)

@Serializable
data class Recipe(
    val title: String,
    val subTitle: String,
    val preparationTime: String,
    val totalCookingTime: String,
    val tags: Tags,
    val ingredients: List<Ingredient>,
    val steps: List<String>
)

