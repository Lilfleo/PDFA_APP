package com.pdfa.pdfa_app.data.dao

import androidx.room.*
import com.pdfa.pdfa_app.data.model.Tag
import com.pdfa.pdfa_app.data.model.TagWithRecipes

@Dao
interface TagDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTag(tag: Tag): Long

    @Transaction
    @Query("SELECT * FROM tag WHERE id = :tagId")
    suspend fun getTagWithRecipes(tagId: Int): TagWithRecipes
}
