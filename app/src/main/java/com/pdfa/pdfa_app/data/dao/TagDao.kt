package com.pdfa.pdfa_app.data.dao

import androidx.room.*
import com.pdfa.pdfa_app.data.model.RecipeWithTags
import com.pdfa.pdfa_app.data.model.Tag
import com.pdfa.pdfa_app.data.model.TagWithRecipes

@Dao
interface TagDao {
    @Query("SELECT * FROM tag")
    suspend fun getAllTags(): List<Tag>

    @Query("SELECT * FROM tag WHERE id = :id")
    suspend fun getTagById(id: Int): Tag?

    @Query("SELECT * FROM tag WHERE name = :name")
    suspend fun getTagByName(name: String): Tag?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTag(tag: Tag): Long

    @Update
    suspend fun updateTag(tag: Tag)

    @Delete
    suspend fun deleteTag(tag: Tag)

    @Transaction
    @Query("SELECT * FROM tag WHERE id IN (SELECT tag_id FROM recipe_tag WHERE recipe_id = :recipeId)")
    suspend fun getTagsForRecipe(recipeId: Int): List<Tag>

    @Transaction
    @Query("SELECT * FROM tag WHERE id = :tagId")
    suspend fun getTagWithRecipes(tagId: Int): TagWithRecipes?
}
