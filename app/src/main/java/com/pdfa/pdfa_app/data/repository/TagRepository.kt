package com.pdfa.pdfa_app.data.repository

import com.pdfa.pdfa_app.data.dao.TagDao
import com.pdfa.pdfa_app.data.model.Tag
import com.pdfa.pdfa_app.data.model.TagWithRecipes
import javax.inject.Inject

class TagRepository @Inject constructor(
    private val dao: TagDao
) {

    suspend fun getAllTags(): List<Tag> = dao.getAllTags()

    suspend fun getTagById(id: Int): Tag? = dao.getTagById(id)

    suspend fun getTagByName(name: String): Tag? = dao.getTagByName(name)

    suspend fun insertTag(tag: Tag): Long = dao.insertTag(tag)

    suspend fun updateTag(tag: Tag) = dao.updateTag(tag)

    suspend fun deleteTag(tag: Tag) = dao.deleteTag(tag)

    suspend fun getTagsForRecipe(recipeId: Int): List<Tag> = dao.getTagsForRecipe(recipeId)

    suspend fun getTagWithRecipes(tagId: Int): TagWithRecipes? = dao.getTagWithRecipes(tagId)
}