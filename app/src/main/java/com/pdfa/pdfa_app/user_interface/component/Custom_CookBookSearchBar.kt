package com.pdfa.pdfa_app.user_interface.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.pdfa.pdfa_app.ui.theme.AppColors
import com.pdfa.pdfa_app.ui.theme.AppShapes
import com.pdfa.pdfa_app.ui.theme.AppSpacing

@Composable
fun CustomCookbookSearchBar(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "Search...",
    onEditClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = AppSpacing.L, vertical = AppSpacing.S),
        horizontalArrangement = Arrangement.spacedBy(AppSpacing.M),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CustomTextFiel(
            value = value,
            onValueChange = onValueChange,
            placeholder = placeholder,
            modifier = Modifier.weight(1f)
        )

//        // Bouton de filtres (tu peux remplacer par ton bouton existant)
//        IconButton(
//            onClick = onFilterClick,
//            modifier = Modifier
//                .size(AppSpacing.XXXXL)
//                .background(
//                    color = AppColors.LightGrey,
//                    shape = AppShapes.CornerL
//                )
//        ) {
//            Icon(Icons.Default.Tune, contentDescription = "Filter")
//        }

        IconButton(
            onClick = onEditClick,
            modifier = Modifier
                .size(AppSpacing.XXXXL)
                .background(
                    color = AppColors.MainGreen,
                    shape = AppShapes.CornerL
                )
        ) {
            Icon(Icons.Default.Edit, contentDescription = "Filter")
        }
    }
}