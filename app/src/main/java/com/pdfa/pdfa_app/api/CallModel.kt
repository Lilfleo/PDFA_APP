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
    val title: String,
    val ingredients: List<Ingredient>,
    val utensils: List<String>,
    val tags: CallTags,
)

// Recipe for shoplist
@Serializable
data class RecipeForShoplist(
    val prompt: RecipeWithoutFoodPrompt,
    val excludedTitles: List<String>
)

@Serializable
data class RecipeWithoutFoodPrompt(
    val title: String,
    val utensils: List<String>,
    val tags: CallTags,
)

//General
@Serializable
data class Ingredient(
    val name: String,
    val quantity: Double?,
    val unit: String
)

@Serializable
data class CallTags(
    val diet: List<String>,
    val tag: List<String>,
    val allergies: List<String>
)