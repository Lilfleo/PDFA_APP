package com.pdfa.pdfa_app.api

import kotlinx.serialization.Serializable

// Recipe with food
@Serializable
data class RecipeWithFood(
    val prompt: RecipeWithFoodPrompt,
    val excluded_titles: Array<String>
)

@Serializable
data class RecipeWithFoodPrompt(
    val titre: String,
    val ingredient: Array<Ingredient>,
    val utensils: Array<String>,
    val tags: CallTags,
)

// Recipe for shoplist
@Serializable
data class RecipeForShoplist(
    val prompt: RecipeForShoplistPrompt,
    val excluded_titles: Array<String>
)

@Serializable
data class RecipeForShoplistPrompt(
    val titre: String,
    val utensils: Array<String>,
    val tags: CallTags,
)

//General
@Serializable
data class Ingredient(
    val name: String,
    val quantity: Double,
    val unit: String
)

@Serializable
data class CallTags(
    val diet: Array<String>,
    val tag: Array<String>,
    val allergies: Array<String>
)