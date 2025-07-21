package com.pdfa.pdfa_app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdfa.pdfa_app.data.model.Food
import com.pdfa.pdfa_app.data.model.FoodDetail
import com.pdfa.pdfa_app.data.model.FoodDetailWithFood
import com.pdfa.pdfa_app.data.repository.FoodDetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class FoodDetailViewModel @Inject constructor(
    private val repository: FoodDetailRepository
) : ViewModel() {

    val foodDetail: StateFlow<List<FoodDetailWithFood>> =
        repository.allFoodDetail.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    fun addFoodDetail(foodDetail: FoodDetail) {
        viewModelScope.launch {
            repository.insert(foodDetail)
        }
    }

    fun updateFoodDetail(foodDetail: FoodDetail) {
        viewModelScope.launch {
            repository.update(foodDetail)
        }
    }

    fun upsertFoodDetail(foodDetail: FoodDetail) {
        viewModelScope.launch {
            repository.upsertFoodDetail(foodDetail)
        }
    }

    fun getFoodDetail(foodId: Int): Flow<FoodDetailWithFood> {
        return repository.getFoodDetail(foodId)
    }
}
