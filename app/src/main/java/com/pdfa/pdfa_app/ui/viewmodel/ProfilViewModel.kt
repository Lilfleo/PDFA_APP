package com.pdfa.pdfa_app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdfa.pdfa_app.data.model.Profil
import com.pdfa.pdfa_app.data.repository.ProfilRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfilViewModel @Inject constructor(
    private val repository: ProfilRepository
): ViewModel() {

    private val _profil = MutableStateFlow<Profil?>(null)
    val profil: StateFlow<Profil?> = _profil.asStateFlow()

    init {
        loadProfil()
    }

    fun insertProfil(profil: Profil) {
        viewModelScope.launch {
            repository.insertProfil(profil)
        }
    }

    fun updateProfil(profil: Profil) {
        viewModelScope.launch {
            repository.updateProfil(profil)
        }
    }

    fun deleteProfil(profil: Profil) {
        viewModelScope.launch {
            repository.deleteProfil(profil)
        }
    }

    private fun loadProfil() {
        viewModelScope.launch {
            repository.getFirstProfil().collect { firstProfil ->
                _profil.value = firstProfil
            }
        }
    }
}