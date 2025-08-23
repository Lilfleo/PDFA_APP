package com.pdfa.pdfa_app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdfa.pdfa_app.data.model.DietPreference
import com.pdfa.pdfa_app.data.model.DietPreferenceWithDiet
import com.pdfa.pdfa_app.data.repository.DietPreferenceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DietPreferenceViewModel @Inject constructor(
    private val repository: DietPreferenceRepository
): ViewModel() {

    val dietPreferenceList: StateFlow<List<DietPreferenceWithDiet>> =
        repository.allDietPreferences.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    init {
        viewModelScope.launch {
            repository.allDietPreferences.collect { list ->
                println("⚠️ TAG_PREFERENCE_VIEWMODEL : reçu ${list.size} préférences de diet")

            }
        }
    }

    fun addDietPreference(dietId: Int) {
        viewModelScope.launch {
            val dietPreference = DietPreference(dietId = dietId)
            repository.insetDietPreference(dietPreference)
        }
    }

    fun removePreferenceByDietId(dietId: Int) {
        viewModelScope.launch {
            val dietPreferenceToRemove = dietPreferenceList.value
                .find { it.dietPreference.dietId == dietId }?.dietPreference

            dietPreferenceToRemove?.let {
                repository.deleteDietPreference(it)
            }
        }
    }
}