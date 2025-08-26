package com.pdfa.pdfa_app.data.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.Junction
import androidx.room.Relation

@Entity(
    tableName = "cookbook_recipe_cross_ref",
    primaryKeys = ["cookbookId", "recipeId"],
    foreignKeys = [
        ForeignKey(
            entity = Cookbook::class,
            parentColumns = ["id"],
            childColumns = ["cookbookId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Recipe::class,
            parentColumns = ["id"],
            childColumns = ["recipeId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["cookbookId"]),
        Index(value = ["recipeId"])
    ]
)
data class CookbookRecipeCrossRef(
    val cookbookId: Int,
    val recipeId: Int
)

data class CookbookWithRecipes(
    @Embedded
    val cookbook: Cookbook,

    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(CookbookRecipeCrossRef::class,
            parentColumn = "cookbookId",
            entityColumn = "recipeId"
        )
    )
    val recipes: List<Recipe>
)

// Une Recipe avec tous ses cookbooks
data class RecipeWithCookbooks(
    @Embedded
    val recipe: Recipe,

    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            CookbookRecipeCrossRef::class,
            parentColumn = "recipeId",
            entityColumn = "cookbookId"
        )
    )
    val cookbooks: List<Cookbook>
)