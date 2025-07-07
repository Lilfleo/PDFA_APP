package com.pdfa.pdfa_app.user_interface.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

data class FoodCategory(
    val label: String
)

@Composable
fun CustomSearchWithSuggestions(
    categories: List<FoodCategory>,
    modifier: Modifier = Modifier,
    onItemSelected: (String) -> Unit
) {
    var query by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    Column(modifier) {
        OutlinedTextField(
            value = query,
            onValueChange = {
                query = it
                expanded = it.isNotBlank()
            },
            label = { Text("Type d'aliment") },
            modifier = Modifier.fillMaxWidth()
        )

        if (expanded) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp)
            ) {
                Column {
                    categories
                        .filter { it.label.contains(query, ignoreCase = true) }
                        .forEach { category ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        query = category.label
                                        expanded = false
                                        onItemSelected(category.label)
                                    }
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(category.label)
                            }
                        }

                    if (categories.none { it.label.contains(query, ignoreCase = true) }) {
                        Text(
                            text = "Aucun résultat",
                            modifier = Modifier.padding(12.dp),
                            color = Color.Gray
                        )
                    }
                }
            }
        }
    }
}
