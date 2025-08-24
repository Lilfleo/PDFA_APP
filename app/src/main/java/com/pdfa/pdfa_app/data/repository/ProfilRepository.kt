package com.pdfa.pdfa_app.data.repository

import com.pdfa.pdfa_app.data.dao.ProfilDao
import com.pdfa.pdfa_app.data.model.Profil
import kotlinx.coroutines.flow.Flow

class ProfilRepository(
    private val dao: ProfilDao
) {
    fun getProfil(): Flow<List<Profil>> = dao.getProfil()

    fun getFirstProfil(): Flow<Profil?> = dao.getFirstProfil()

    suspend fun insertProfil(profil: Profil) = dao.insertProfil(profil)

    suspend fun updateProfil(profil: Profil) = dao.updateProfil(profil)

    suspend fun deleteProfil(profil: Profil) = dao.deleteProfil(profil)
}