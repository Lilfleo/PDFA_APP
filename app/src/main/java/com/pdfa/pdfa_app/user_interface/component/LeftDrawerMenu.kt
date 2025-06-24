package com.pdfa.pdfa_app.user_interface.component

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DrawerMenu(
    onDestinationClicked: (String) -> Unit
) {
    ModalDrawerSheet {
        DrawerContent(onDestinationClicked = onDestinationClicked)
    }
}

@Composable
fun DrawerContent(
    onDestinationClicked: (String) -> Unit
) {
    val navigationItems = listOf("Home", "Fridge", "Recipe", "Cookbook")

    navigationItems.forEach { item ->
        Text(
            text = item,
            modifier = Modifier
                .clickable {
                    Log.d("DrawerClick", "Clicked $item")
                    onDestinationClicked(item)
                }
                .padding(16.dp)
        )
    }
}
