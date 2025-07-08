package com.pdfa.pdfa_app.user_interface.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.pdfa.pdfa_app.ui.theme.AppColors

@Composable
fun AddButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    FloatingActionButton(
        onClick = onClick,
        modifier = modifier,
        containerColor = AppColors.MainGreen,
        contentColor = AppColors.MainGrey
    ) {
        Icon(Icons.Filled.Add, contentDescription = "Add")
    }
}