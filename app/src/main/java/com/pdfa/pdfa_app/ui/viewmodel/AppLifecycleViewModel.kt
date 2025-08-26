package com.pdfa.pdfa_app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdfa.pdfa_app.data.repository.CookbookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import android.util.Log

@HiltViewModel
class AppLifecycleViewModel @Inject constructor(
    private val cookbookRepository: CookbookRepository
) : ViewModel() {

    private val TAG = "AppLifecycleViewModel"

    fun onAppGoingToBackground() {
        Log.d(TAG, "🔄 Application en cours de fermeture - Déplacement des recettes vers History")
        viewModelScope.launch {
            cookbookRepository.moveRecipesToHistory()
        }
    }
}
