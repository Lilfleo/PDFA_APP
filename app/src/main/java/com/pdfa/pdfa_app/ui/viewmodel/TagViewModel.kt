package com.pdfa.pdfa_app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pdfa.pdfa_app.data.model.Tag
import com.pdfa.pdfa_app.data.repository.TagRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TagViewModel @Inject constructor(
    private val repository: TagRepository
) : ViewModel() {

    private val _tags = MutableStateFlow<List<Tag>>(emptyList())
    val tags: StateFlow<List<Tag>> = _tags.asStateFlow()

    private val _selectedTag = MutableStateFlow<Tag?>(null)
    val selectedTag: StateFlow<Tag?> = _selectedTag.asStateFlow()

    init {
        // 🔍 Log pour débogage
        loadAllTags()
        viewModelScope.launch {
            _tags.collect { list ->
                println("⚠️ TAG_VIEWMODEL : reçu ${list.size} tags")
                list.forEach { println("🏷️ ${it.name}") }
            }
        }
    }

    fun getAllTags() {
        viewModelScope.launch {
            repository.getAllTags()
        }
    }

    fun getTagById(id: Int) {
        viewModelScope.launch {
            val tag = repository.getTagById(id)
            _selectedTag.value = tag
        }
    }

    private fun loadAllTags() {
        viewModelScope.launch {
            val allTags = repository.getAllTags()
            _tags.value = allTags
        }
    }
}
