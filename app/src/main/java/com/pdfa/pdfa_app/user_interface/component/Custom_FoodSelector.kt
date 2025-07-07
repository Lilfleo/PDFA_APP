package com.pdfa.pdfa_app.user_interface.component


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomFoodSelector(
    foodList: List<String>,
    selectedFood: String,
    onFoodSelected: (String) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    var customFoodName by remember { mutableStateOf("") }

    val filteredList = remember(searchQuery, foodList) {
        foodList.filter {
            it.contains(searchQuery, ignoreCase = true)
        }
    }

    Column(modifier = Modifier.fillMaxWidth()) {

        Text(
            text = "Quel aliment souhaites-tu ajouter ?",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(start = 4.dp, bottom = 4.dp)
        )

        // Boîte dropdown
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = selectedFood.ifEmpty { "" },
                onValueChange = {},
                readOnly = true,
                placeholder = { Text("Sélectionner...") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )


            // Le menu déroulant
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {

                // Liste filtrée
                filteredList.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(item) },
                        onClick = {
                            onFoodSelected(item)
                            expanded = false
                            searchQuery = ""
                        }
                    )
                }

                // Option autre
                DropdownMenuItem(
                    text = { Text("Autre...") },
                    onClick = {
                        onFoodSelected("Autre")
                        expanded = false
                        searchQuery = ""
                    }
                )
            }
        }

        // Champ de saisie si autre
        AnimatedVisibility(visible = selectedFood == "Autre") {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = customFoodName,
                    onValueChange = { customFoodName = it },
                    label = { Text("Type d’aliment...") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
