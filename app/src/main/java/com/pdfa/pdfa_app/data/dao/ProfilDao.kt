package com.pdfa.pdfa_app.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.pdfa.pdfa_app.data.model.Profil
import kotlinx.coroutines.flow.Flow

@Dao
interface ProfilDao {
    @Query("SELECT * FROM profil")
    fun getProfil(): Flow<List<Profil>>

    @Query("SELECT * FROM profil LIMIT 1")
    fun getFirstProfil(): Flow<Profil?>

    @Insert
    suspend fun insertProfil(profil: Profil)

    @Update
    suspend fun updateProfil(profil: Profil)

    @Delete
    suspend fun deleteProfil(profil: Profil)
}