package com.pdfa.pdfa_app.user_interface.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.pdfa.pdfa_app.ui.theme.AppColors
import com.pdfa.pdfa_app.ui.theme.AppShapes
import com.pdfa.pdfa_app.ui.theme.AppSpacing
import com.pdfa.pdfa_app.ui.theme.AppTypo

@Composable
fun EditSection(
    name: String,
//    isChecked: Boolean,
//    onCheckedChange: (Boolean) -> Unit
){
    var isEditMode by remember { mutableStateOf(false) }
    var sectionName by remember { mutableStateOf(name) }
    var openDeleteDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (!isEditMode) {
                Text(
                    text = sectionName,
                    style = AppTypo.CookbookSection,
                    color = AppColors.MediumGrey
                )
            } else {
                CustomTextFiel(
                    value = sectionName,
                    onValueChange = { sectionName = it },
                    placeholder = sectionName,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.padding(AppSpacing.XXS))

            Row(
                horizontalArrangement = Arrangement.spacedBy(AppSpacing.S),
                verticalAlignment = Alignment.CenterVertically
            ){
                if (!isEditMode) {
                    IconButton(
                        onClick = { isEditMode = true },
                        modifier = Modifier
                            .size(AppSpacing.XXXXL)
                            .background(
                                color = AppColors.LightGrey,
                                shape = AppShapes.CornerL
                            )
                    ) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit")
                    }
                } else {
                    IconButton(
                        onClick = { isEditMode = false },
                        modifier = Modifier
                            .size(AppSpacing.XXXXL)
                            .background(
                                color = AppColors.Easy,
                                shape = AppShapes.CornerL
                            )
                    ) {
                        Icon(Icons.Default.Check, contentDescription = "Validate")
                    }
                }

                if (!isEditMode) {
                    IconButton(
                        onClick = { openDeleteDialog = true },
                        modifier = Modifier
                            .size(AppSpacing.XXXXL)
                            .background(
                                color = AppColors.Hard,
                                shape = AppShapes.CornerL
                            )
                    ) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete")
                    }
                }
            }
        }

        if (openDeleteDialog) {
            DeleteConfirmationGeneral(
                itemName = sectionName,
                onConfirm = {
                    openDeleteDialog = false
                },
                onDismiss = {
                    openDeleteDialog = false
                },
                onDelete = {
                    openDeleteDialog = false
                }
            )
        }
    }
}