package com.pdfa.pdfa_app.user_interface.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp



@Composable
fun FridgeScreen(onAddClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.align(Alignment.Center).fillMaxSize()) {
            SearchBar(
                query = "",
                onQueryChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }
        Text(
            text = "Fridge Screen",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.align(Alignment.Center)
        )

        AddButton(
            onClick = onAddClick,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        )
    }
}

@Composable
fun AddButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    FloatingActionButton(
        onClick = onClick,
        modifier = modifier,
        containerColor = Color(0xFF91AB75),
        contentColor = Color(0xFF656565)
    ) {
        Icon(Icons.Filled.Add, contentDescription = "Add")
    }
}

@Composable
fun SearchBar(query: String, onQueryChange: (String) -> Unit, modifier: Modifier = Modifier) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        placeholder = { Text("Search") },
        label = { Text("Search") },
        singleLine = true,
        shape = RoundedCornerShape(8.dp),
        modifier = modifier,
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color(0xFF91AB75),
            unfocusedIndicatorColor = Color.LightGray,
            focusedLabelColor = Color(0xFF91AB75),
            unfocusedLabelColor = Color.Gray
        )
    )

}