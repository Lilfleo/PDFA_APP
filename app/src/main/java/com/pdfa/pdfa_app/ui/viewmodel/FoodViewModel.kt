package com.pdfa.pdfa_app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdfa.pdfa_app.data.model.Food
import com.pdfa.pdfa_app.data.repository.FoodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodViewModel @Inject constructor(
    private val repository: FoodRepository
) : ViewModel() {

    val foodList: StateFlow<List<Food>> =
        repository.allFood.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )
    init {
        // 🔍 Log pour débogage
        viewModelScope.launch {
            repository.allFood.collect { list ->
                println("⚠️ FOOD_VIEWMODEL : reçu ${list.size} aliments")
                list.forEach { println("🍎 ${it.name}") }
            }
        }
    }

    fun addFood(food: Food) {
        viewModelScope.launch {
            repository.insert(food)
        }
    }

    fun removeFood(food: Food) {
        viewModelScope.launch {
            repository.delete(food)
        }
    }
}
