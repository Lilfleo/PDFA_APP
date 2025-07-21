package com.pdfa.pdfa_app.data.model

import androidx.room.*

@Entity(
    tableName = "food_recipe",
    primaryKeys = ["recipe_id", "food_id"],
    foreignKeys = [
        ForeignKey(
            entity = Recipe::class,
            parentColumns = ["id"],
            childColumns = ["recipe_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Food::class,
            parentColumns = ["id"],
            childColumns = ["food_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["recipe_id"]),
        Index(value = ["food_id"])
    ]
)

data class FoodRecipeCrossRef(
    @ColumnInfo(name = "recipe_id")
    val recipeId: Int,
    @ColumnInfo(name = "food_id")
    val foodId: Int,
    @ColumnInfo(name = "food_quantity")
    val foodQuantity: Int // Quantity of this food in this recipe
)

// Data classes for relationships
data class RecipeWithFoods(
    @Embedded val recipe: Recipe,
    @Relation(
        parentColumn = "id",
        entityColumn = "recipe_id",
        associateBy = Junction(FoodRecipeCrossRef::class)
    )
    val foods: List<Food>
)

data class FoodWithRecipes(
    @Embedded val food: Food,
    @Relation(
        parentColumn = "id",
        entityColumn = "food_id",
        associateBy = Junction(FoodRecipeCrossRef::class)
    )
    val recipes: List<Recipe>
)

// Detailed recipe information with quantities
data class RecipeWithFoodQuantities(
    @Embedded val recipe: Recipe,
    @Relation(
        entity = FoodRecipeCrossRef::class,
        parentColumn = "id",
        entityColumn = "recipe_id"
    )
    val foodQuantities: List<FoodWithQuantity>
)

data class FoodWithQuantity(
    @Embedded val crossRef: FoodRecipeCrossRef,
    @Relation(
        parentColumn = "food_id",
        entityColumn = "id"
    )
    val food: Food
)