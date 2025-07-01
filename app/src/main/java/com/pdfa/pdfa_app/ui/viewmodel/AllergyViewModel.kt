package com.pdfa.pdfa_app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdfa.pdfa_app.data.model.Allergy
import com.pdfa.pdfa_app.data.model.AllergyWithFood
import com.pdfa.pdfa_app.data.repository.AllergyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllergyViewModel @Inject constructor(
    private val repository: AllergyRepository
) : ViewModel() {

    val allAllergy: StateFlow<List<AllergyWithFood>> =
        repository.allAllergy
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun insertAllergy(allergy: Allergy) {
        viewModelScope.launch {
            repository.insert(allergy)
        }
    }
}
