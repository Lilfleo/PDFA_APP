package com.pdfa.pdfa_app.user_interface.filter

import CustomCheckbox
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.pdfa.pdfa_app.ui.theme.AppColors
import com.pdfa.pdfa_app.ui.theme.AppShapes
import com.pdfa.pdfa_app.ui.theme.AppSpacing
import com.pdfa.pdfa_app.ui.theme.AppTypo
import com.pdfa.pdfa_app.user_interface.component.CustomDropdown

@Composable
fun FridgeFilterPanel(
    filterState: FridgeFilterState,
    uniqueCategories: List<String>,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = filterState.expanded,
        enter = expandVertically() + fadeIn(),
        exit = shrinkVertically() + fadeOut()
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .background(AppColors.Primary, AppShapes.CornerL)
                .padding(AppSpacing.L)
        ) {
            // 🏷️ Filtre par catégorie
            Text(
                text = "Catégorie :",
                style = AppTypo.Body,
                color = AppColors.Primary
            )
            Spacer(modifier = Modifier.height(AppSpacing.S))

            CustomDropdown(
                selectedValue = filterState.selectedCategoryFood ?: "",
                placeholder = "Toutes les catégories",
                onItemSelected = { selection ->
                    filterState.updateSelectedCategory(
                        if (selection == "Toutes les catégories") null else selection
                    )
                },
                elements = listOf("Toutes les catégories") + uniqueCategories
            )

            Spacer(modifier = Modifier.height(AppSpacing.M))

            // 📊 Options de tri
            Text(
                text = "Trier par :",
                style = AppTypo.BodyLight,
                color = AppColors.Primary
            )
            Spacer(modifier = Modifier.height(AppSpacing.S))

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(AppSpacing.M),
                verticalArrangement = Arrangement.spacedBy(AppSpacing.S)
            ) {
                // Tri par quantité croissante
                FilterCheckboxRow(
                    text = "Quantité ↗",
                    checked = filterState.sortByQuantityAsc,
                    onCheckedChange = filterState::enableQuantityAscSort  // ✅ Corrigé
                )

                // Tri par quantité décroissante
                FilterCheckboxRow(
                    text = "Quantité ↙",
                    checked = filterState.sortByQuantityDesc,
                    onCheckedChange = filterState::enableQuantityDescSort  // ✅ Corrigé
                )

                // Tri par date de péremption
                FilterCheckboxRow(
                    text = "Date péremption",
                    checked = filterState.sortByExpirationDate,
                    onCheckedChange = filterState::enableExpirationDateSort  // ✅ Corrigé
                )
            }

            // 🧹 Bouton pour effacer tous les filtres
            if (filterState.hasActiveFilters()) {
                Spacer(modifier = Modifier.height(AppSpacing.M))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = "Effacer les filtres",
                        style = AppTypo.BodyLight,
                        color = AppColors.Primary,
                        modifier = Modifier.clickable {
                            filterState.clearAllFilters()
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun FilterCheckboxRow(
    text: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.clickable { onCheckedChange(!checked) }
    ) {
        CustomCheckbox(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
        Spacer(modifier = Modifier.width(AppSpacing.S))
        Text(text = text, style = AppTypo.BodyLight)
    }
}
