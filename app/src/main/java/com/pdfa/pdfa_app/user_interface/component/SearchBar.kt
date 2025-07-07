package com.pdfa.pdfa_app.user_interface.component

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.pdfa.pdfa_app.ui.theme.AppShapes

@Composable
fun SearchBar(query: String, onQueryChange: (String) -> Unit, modifier: Modifier = Modifier) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        placeholder = { Text("Search") },
        label = { Text("Search") },
        singleLine = true,
        shape = AppShapes.CornerS,
        modifier = modifier
    )

}