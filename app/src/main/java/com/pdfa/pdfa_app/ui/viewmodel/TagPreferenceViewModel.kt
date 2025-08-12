package com.pdfa.pdfa_app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdfa.pdfa_app.data.model.TagPreference
import com.pdfa.pdfa_app.data.model.TagPreferenceWithTag
import com.pdfa.pdfa_app.data.repository.TagPreferenceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class TagPreferenceViewModel @Inject constructor(
    private val repository: TagPreferenceRepository
): ViewModel() {
    val tagPreferenceList: StateFlow<List<TagPreferenceWithTag>> =
        repository.allTagPreferences.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    init {
        // 🔍 Log pour débogage
        viewModelScope.launch {
            repository.allTagPreferences.collect { list ->
                println("⚠️ TAG_PREFERENCE_VIEWMODEL : reçu ${list.size} préférences de tags")
                list.forEach { println("🏷️ ${it.tag.name ?: "Tag inconnu"}") }
            }
        }
    }

    fun addTagPreference(tagId: Int) {
        viewModelScope.launch {
            val tagPreference = TagPreference(tagId = tagId)
            repository.insertTagPreference(tagPreference)
        }
    }

//    fun removeTagPreference(tagPreference: TagPreference) {
//        viewModelScope.launch {
//            repository.deleteTagPreference(tagPreference)
//        }
//    }

    fun removeTagPreferenceByTagId(tagId: Int) {
        viewModelScope.launch {
            val tagPreferenceToRemove = tagPreferenceList.value
                .find { it.tagPreference.tagId == tagId }?.tagPreference

            tagPreferenceToRemove?.let {
                repository.deleteTagPreference(it)
            }
        }
    }
}