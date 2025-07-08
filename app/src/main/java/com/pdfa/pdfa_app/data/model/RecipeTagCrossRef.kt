package com.pdfa.pdfa_app.data.model

import androidx.room.Entity
import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

@Entity(primaryKeys = ["recipeId", "tagId"])
data class RecipeTagCrossRef(
    val recipeId: Int,
    val tagId: Int
)

data class RecipeWithTags(
    @Embedded val recipe: Recipe,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = RecipeTagCrossRef::class,
            parentColumn = "recipeId",
            entityColumn = "tagId"
        )
    )
    val tags: List<Tag>
)

data class TagWithRecipes(
    @Embedded val tag: Tag,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = RecipeTagCrossRef::class,
            parentColumn = "tagId",
            entityColumn = "recipeId"
        )
    )
    val recipes: List<Recipe>
)
