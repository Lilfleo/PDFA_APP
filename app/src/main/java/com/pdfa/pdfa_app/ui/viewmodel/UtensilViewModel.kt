package com.pdfa.pdfa_app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdfa.pdfa_app.data.dao.UtensilPreferenceDao
import com.pdfa.pdfa_app.data.model.Utensil
import com.pdfa.pdfa_app.data.model.UtensilPreference
import com.pdfa.pdfa_app.data.model.UtensilPreferenceWithUtensil
import com.pdfa.pdfa_app.data.repository.UtensilRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UtensilViewModel @Inject constructor(
    private val utensilRepository: UtensilRepository,
    private val utensilPreferenceDao: UtensilPreferenceDao
) : ViewModel() {

    val utensils: StateFlow<List<Utensil>> = utensilRepository.allUtensils  // ✅ Sans ()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val utensilPreferences: Flow<List<UtensilPreferenceWithUtensil>> =
        utensilPreferenceDao.getAllUtensilPreferences()

    fun toggleUtensilSelection(utensilId: Int) {
        viewModelScope.launch {
            val existingPreference = utensilPreferenceDao.getAllUtensilPreferences()
                .first()
                .find { it.utensil.id == utensilId }

            if (existingPreference != null) {
                utensilPreferenceDao.deleteUtensilPreference(existingPreference.utensilPreference)
            } else {
                utensilPreferenceDao.insertUtensilPreference(
                    UtensilPreference(utensilId = utensilId)
                )
            }
        }
    }
}

