package com.pdfa.pdfa_app.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.pdfa.pdfa_app.data.model.RecipeTagCrossRef

@Dao
interface RecipeTagCrossRefDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRecipeTagCrossRef(crossRef: RecipeTagCrossRef)
}
