package com.pdfa.pdfa_app.api

import kotlinx.serialization.Serializable

// Recipe with food
@Serializable
data class RecipeWithFood(
    val prompt: RecipeWithFoodPrompt,
    val excludedTitles: List<String>
)

@Serializable
data class RecipeWithFoodPrompt(
    val title: String? = null,
    val ingredients: List<Ingredient>,
    val utensils: List<String>,
    val tags: Tags,
)

// Recipe for shoplist
@Serializable
data class RecipeForShoplist(
    val prompt: RecipeWithoutFoodPrompt,
    val excludedTitles: List<String>
)

@Serializable
data class RecipeWithoutFoodPrompt(
    val title: String? = null,
    val utensils: List<String>,
    val tags: Tags,
)

//General
@Serializable
data class Ingredient(
    val name: String,
    val quantity: Double?,
    val unit: String
)

@Serializable
data class Tags(
    val diet: List<String>? = null,
    val tag: List<String>? = null,
    val allergies: List<String>? = null
)