package com.pdfa.pdfa_app.user_interface.screens

import FoodDetailDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import com.pdfa.pdfa_app.data.FridgeItem
import com.pdfa.pdfa_app.ui.theme.AppColors
import com.pdfa.pdfa_app.ui.theme.AppShapes
import com.pdfa.pdfa_app.ui.theme.AppSpacing
import com.pdfa.pdfa_app.user_interface.component.AddButton
import com.pdfa.pdfa_app.user_interface.component.SearchBar


@Composable
fun FridgeScreen(onAddClick: () -> Unit) {

    var searchQuery by remember { mutableStateOf("") }
    var showDialogAdd by remember { mutableStateOf(false) }
    //var showDialog by remember { mutableStateOf(false) }

    // Exemple de données en dur
    val fridgeItems = listOf(
        FridgeItem("Carottes", 41),
        FridgeItem("Épinards", 23),
        FridgeItem("Épinards", 23),
        FridgeItem("Épinards", 23),
        FridgeItem("Épinards", 23),
        FridgeItem("Épinards", 23),
        FridgeItem("Épinards", 23),
        FridgeItem("Épinards", 23),
        FridgeItem("Épinards", 23),
        FridgeItem("Épinards", 23),
        FridgeItem("Épinards", 23),
        FridgeItem("Épinards", 23),
        FridgeItem("Épinards", 23),
        FridgeItem("Épinards", 23),
        FridgeItem("Épinards", 23),
        FridgeItem("Épinards", 23),
        FridgeItem("Épinards", 23),
        FridgeItem("Épinards", 23),
        FridgeItem("Épinards", 23),
        FridgeItem("Épinards", 23),
        FridgeItem("Épinards", 23),
        FridgeItem("Farine", 364)
    )

    // État de scroll de la liste
    val listState = rememberLazyListState()

    // Est-ce qu'on est en haut ?

    val isAtTop by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex == 0
        }
    }


    // Est-ce qu'on est en bas ?
    val isAtEnd by remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()
            lastVisibleItem != null && lastVisibleItem.index == layoutInfo.totalItemsCount - 1
        }
    }




    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = AppColors.Primary)
    ) {
        // Barre de recherche FIXE
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(AppSpacing.S)
                .align(Alignment.TopCenter)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SearchBar(
                    query = searchQuery,
                    onQueryChange = { searchQuery = it },
                    modifier = Modifier.weight(1f)
                )
                IconButton(
                    onClick = { },
                    modifier = Modifier.padding(start = AppSpacing.S)
                ) {
                    Icon(Icons.Default.Tune, contentDescription = "Filter")
                }
            }
        }



        // Zone de la liste + overlay fade
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = AppSpacing.XXXXXL) // espace sous la SearchBar
        ) {
            // 1️⃣ La Box contenant la liste, avec ombre et coins
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = AppSpacing.S,       // Sépare du haut (de la SearchBar)
                        start = AppSpacing.L,    // marge horizontale gauche
                        end = AppSpacing.L,      // marge horizontale droite
                        bottom = AppSpacing.L    // marge avec le bas de l'écran
                    )
                    .shadow(
                        elevation = AppSpacing.XS,
                        shape = AppShapes.CornerXL,
                        clip = false
                    )
                    .clip(AppShapes.CornerXL)
                    .background(Color.White)

            ) {
                LazyColumn(
                    state = listState,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(fridgeItems) { item ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = AppSpacing.M, AppSpacing.S),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(AppSpacing.XXXXL)
                                    .background(Color.LightGray, shape = AppShapes.CornerXS)
                            )
                            Spacer(modifier = Modifier.width(AppSpacing.M))
                            Text(
                                text = item.name,
                                modifier = Modifier.weight(1f),
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Text(
                                text = "${item.calories} kcal",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }

                }
            }

            // Fade en haut si pas au top
            if (!isAtTop) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(AppSpacing.XXXXL)
                        .align(Alignment.TopCenter)
                )
            }


            // 2️⃣ Le fade en bas
            if (!isAtEnd) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(AppSpacing.XXXXL)
                        .align(Alignment.BottomCenter)

                )
            }
        }


        // Bouton flottant
        AddButton(
            onClick = { showDialogAdd = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(AppSpacing.L)
        )
    }


    if (showDialogAdd) {
        FoodDetailDialog(
            foodName = "",
            onDismiss = { showDialogAdd = false }
        )
    }
}




