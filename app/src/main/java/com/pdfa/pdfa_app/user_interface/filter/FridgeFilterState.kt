package com.pdfa.pdfa_app.user_interface.filter

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.pdfa.pdfa_app.data.model.FoodDetailWithFood
import java.util.Date

@Stable
class FridgeFilterState {
    // 🔍 États des filtres
    var searchQuery by mutableStateOf("")
        private set

    var selectedCategoryFood by mutableStateOf<String?>(null)
        private set

    var sortByQuantityAsc by mutableStateOf(false)
    var sortByQuantityDesc by mutableStateOf(false)
    var sortByExpirationDate by mutableStateOf(false)

    var expanded by mutableStateOf(false)
        private set

    // 📝 Actions pour modifier les états (noms différents pour éviter conflits)
    fun updateSearchQuery(query: String) {
        searchQuery = query
    }

    fun updateSelectedCategory(category: String?) {
        selectedCategoryFood = category
    }

    fun toggleFilterExpansion() {
        expanded = !expanded
    }

    fun enableQuantityAscSort(enabled: Boolean) {  // ✅ Nom changé
        sortByQuantityAsc = enabled
        if (enabled) {
            sortByQuantityDesc = false
            sortByExpirationDate = false
        }
    }

    fun enableQuantityDescSort(enabled: Boolean) {  // ✅ Nom changé
        sortByQuantityDesc = enabled
        if (enabled) {
            sortByQuantityAsc = false
            sortByExpirationDate = false
        }
    }

    fun enableExpirationDateSort(enabled: Boolean) {  // ✅ Nom changé
        sortByExpirationDate = enabled
        if (enabled) {
            sortByQuantityAsc = false
            sortByQuantityDesc = false
        }
    }

    fun clearAllFilters() {
        searchQuery = ""
        selectedCategoryFood = null
        sortByQuantityAsc = false
        sortByQuantityDesc = false
        sortByExpirationDate = false
    }

    // 🎯 Logique de filtrage principale
    fun applyFilters(foodDetail: List<FoodDetailWithFood>): List<FoodDetailWithFood> {
        var result = foodDetail

        // 1️⃣ Recherche par nom d'aliment
        if (searchQuery.isNotBlank()) {
            result = result.filter {
                it.food.name.contains(searchQuery, ignoreCase = true)
            }
        }

        // 2️⃣ Filtre par catégorie
        if (selectedCategoryFood != null) {
            result = result.filter {
                it.food.category == selectedCategoryFood
            }
        }

        // 3️⃣ Tri par quantité croissante
        if (sortByQuantityAsc) {
            result = result.sortedBy { it.foodDetail.quantity }
        }

        // 4️⃣ Tri par quantité décroissante
        if (sortByQuantityDesc) {
            result = result.sortedByDescending { it.foodDetail.quantity }
        }

        // 5️⃣ Tri par date de péremption
        if (sortByExpirationDate) {
            result = result.sortedBy {
                it.foodDetail.expirationTime ?: Date(Long.MAX_VALUE)
            }
        }

        return result
    }

    // 📊 Récupère les catégories uniques
    fun getUniqueCategories(foodDetail: List<FoodDetailWithFood>): List<String> {
        return foodDetail.map { it.food.category }.distinct().sorted()
    }

    // ✅ Vérifie si des filtres sont actifs
    fun hasActiveFilters(): Boolean {
        return searchQuery.isNotBlank() ||
                selectedCategoryFood != null ||
                sortByQuantityAsc ||
                sortByQuantityDesc ||
                sortByExpirationDate
    }
}
