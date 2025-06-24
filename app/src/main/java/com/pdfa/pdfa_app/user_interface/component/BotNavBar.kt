package com.pdfa.pdfa_app.user_interface.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun BottomNavBar(
    selectedIndex: Int,
    onItemSelected: (Int, String) -> Unit,
    routes: List<String>
) {
    NavigationBar(containerColor = Color.White) {
        routes.forEachIndexed { index, route ->
            NavigationBarItem(
                selected = selectedIndex == index,
                onClick = { onItemSelected(index, route) },
                icon = { Icon(Icons.Filled.Menu, contentDescription = null) }, // À remplacer plus tard par `AppIcon`
                label = { Text(route) }
            )
        }
    }
}
