package com.pdfa.pdfa_app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdfa.pdfa_app.data.model.Diet
import com.pdfa.pdfa_app.data.repository.DietRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DietViewModel @Inject constructor(
    private val dietRepository: DietRepository
) : ViewModel() {

    // StateFlow pour la liste des régimes
    private val _diets = MutableStateFlow<List<Diet>>(emptyList())
    val diets: StateFlow<List<Diet>> = _diets.asStateFlow()

    private val _dietNames = MutableStateFlow<List<String>>(emptyList())
    val dietNames: StateFlow<List<String>> = _dietNames.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        loadDiets()
        loadDietNames() // ← Charge aussi les noms
    }

    private fun loadDiets() {
        viewModelScope.launch {
            _error.value = null
            try {
                dietRepository.getAllDiets().collect { dietList ->
                    _diets.value = dietList
                }
            } catch (e: Exception) {
                _error.value = "Erreur lors du chargement des régimes: ${e.message}"
            } finally {
            }
        }
    }

    private fun loadDietNames() {
        viewModelScope.launch {
            try {
                dietRepository.getAllDietNames().collect { nameList ->
                    _dietNames.value = nameList
                }
            } catch (e: Exception) {
                _error.value = "Erreur lors du chargement des noms de régimes: ${e.message}"
            }
        }
    }

    fun getDietNames(): List<String> {
        return _dietNames.value
    }
}
