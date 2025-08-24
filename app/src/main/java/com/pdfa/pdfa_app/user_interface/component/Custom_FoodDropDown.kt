package com.pdfa.pdfa_app.user_interface.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pdfa.pdfa_app.ui.theme.AppColors
import com.pdfa.pdfa_app.ui.theme.AppShapes
import com.pdfa.pdfa_app.ui.theme.AppTypo
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.pdfa.pdfa_app.data.model.Food
import com.pdfa.pdfa_app.ui.theme.AppSpacing

@Composable
fun CustomFoodDropdown(
    selectedValue: String = "",
    placeholder: String = "Type d'aliment",
    onItemSelected: (Food) -> Unit,
    foods: List<Food> = emptyList() // Ajoute ta liste de foods ici
) {
    var expanded by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }

    // Box pour le champ principal uniquement
    Box {
        // Champ principal (taille fixe)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 2.dp,
                    color = AppColors.LightGrey,
                    shape = AppShapes.CornerM
                )
                .clickable { expanded = true }
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = if ( selectedValue.isEmpty() ) placeholder else selectedValue,
                style = AppTypo.Body,
                color = if (selectedValue.isEmpty()) AppColors.LightGrey else Color.Black
            )

            Icon(
                if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = "Toggle dropdown",
                tint = AppColors.MediumGrey,
                modifier = Modifier.size(20.dp)
            )
        }

        // Popup flottant - ne prend aucune place dans le layout
        if (expanded) {
            Popup(
                onDismissRequest = {
                    expanded = false
                },
                properties = PopupProperties(
                    focusable = true, // Important : permet les interactions à l'intérieur
                    dismissOnBackPress = true,
                    dismissOnClickOutside = true // On gère manuellement
                )
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth(.825f)
                        .heightIn(max = 500.dp)
                        .background(
                            color = AppColors.Primary,
                            shape = AppShapes.CornerM
                        ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column {
                        // Barre de recherche en haut
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(AppSpacing.XS)
                                .background(
                                    color = Color.Gray.copy(alpha = 0.1f),
                                    shape = AppShapes.CornerS
                                )
                                .padding(horizontal = 12.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.Search,
                                contentDescription = "Search",
                                tint = Color.Gray,
                                modifier = Modifier.size(16.dp)
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            BasicTextField(
                                value = searchQuery,
                                onValueChange = { searchQuery = it },
                                modifier = Modifier.weight(1f),
                                textStyle = TextStyle(
                                    color = Color.Black,
                                    fontSize = 14.sp
                                ),
                                singleLine = true,
                                decorationBox = { innerTextField ->
                                    Box {
                                        if (searchQuery.isEmpty()) {
                                            Text(
                                                text = "Rechercher...",
                                                color = Color.Gray,
                                                fontSize = 14.sp
                                            )
                                        }
                                        innerTextField()
                                    }
                                }
                            )

                            if (searchQuery.isNotEmpty()) {
                                IconButton(
                                    onClick = { searchQuery = "" },
                                    modifier = Modifier.size(24.dp)
                                ) {
                                    Icon(
                                        Icons.Default.Clear,
                                        contentDescription = "Clear",
                                        tint = Color.Gray,
                                        modifier = Modifier.size(14.dp)
                                    )
                                }
                            }

                            // Bouton fermer
                            IconButton(
                                onClick = {
                                    expanded = false
                                    searchQuery = ""
                                },
                                modifier = Modifier.size(24.dp)
                            ) {
                                Icon(
                                    Icons.Default.KeyboardArrowUp,
                                    contentDescription = "Close",
                                    tint = Color.Gray,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }

                        // Liste des items
                        LazyColumn(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            val filteredItems = foods.filter { food ->
                                if (searchQuery.isEmpty()) true
                                else food.name.lowercase().contains(searchQuery.lowercase())
                            }

                            items(filteredItems) { food ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            onItemSelected(food)
                                            expanded = false
                                            searchQuery = ""
                                        }
                                        .padding(horizontal = 16.dp, vertical = 12.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = food.name,
                                        style = AppTypo.Body,
                                        color = Color.Black
                                    )

                                    Box(
                                        modifier = Modifier
                                            .size(16.dp)
                                            .background(
                                                color = AppColors.LightGrey,
                                                shape = AppShapes.CornerS
                                            )
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
