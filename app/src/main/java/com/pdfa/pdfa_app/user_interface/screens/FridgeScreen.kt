package com.pdfa.pdfa_app.user_interface.screens

import FoodDetailDialog
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import com.pdfa.pdfa_app.data.FridgeItem
import com.pdfa.pdfa_app.data.model.Food
import com.pdfa.pdfa_app.ui.theme.AppColors
import com.pdfa.pdfa_app.ui.theme.AppShapes
import com.pdfa.pdfa_app.ui.theme.AppSpacing
import com.pdfa.pdfa_app.ui.viewmodel.FoodDetailViewModel
import com.pdfa.pdfa_app.ui.viewmodel.FoodViewModel
import com.pdfa.pdfa_app.user_interface.component.AddButton
import com.pdfa.pdfa_app.user_interface.component.CustomSnackbarHost
import com.pdfa.pdfa_app.user_interface.component.DeleteConfirmationDialog
import com.pdfa.pdfa_app.user_interface.component.EditFoodDialog
import com.pdfa.pdfa_app.user_interface.component.FridgeItemActionDialog
import com.pdfa.pdfa_app.user_interface.component.SearchBar





@Composable
fun FridgeScreen(onAddClick: () -> Unit, viewModel: FoodViewModel= hiltViewModel(),foodDetailviewModel: FoodDetailViewModel = hiltViewModel()) {

    val foodList by viewModel.foodList.collectAsState()
    val foodDetailList by foodDetailviewModel.foodDetail.collectAsState()


    var searchQuery by remember { mutableStateOf("") }
    var showDialogAdd by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }
    Log.d("FridgeScreen", "SnackbarHostState created: $snackbarHostState")

    var snackbarMessage by remember { mutableStateOf<String?>(null) }
    var snackbarType by remember { mutableStateOf<String?>(null) }
    var selectedItem by remember { mutableStateOf<Food?>(null) }
    var isEditing by remember { mutableStateOf(false) }
    var isDeleteConfirmationVisible by remember { mutableStateOf(false) }


    //foodDetailviewModel.addFoodDetail(foodDetail = )


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

    // reception du message pour la snackbar
    LaunchedEffect(snackbarMessage) {
        if (snackbarMessage != null && snackbarType != null) {
            val duration = when (snackbarType) {
                "success" -> SnackbarDuration.Short
                "waiting" -> SnackbarDuration.Short
                "fail" -> SnackbarDuration.Short
                else -> SnackbarDuration.Short
            }

            snackbarHostState.showSnackbar(
                message = snackbarMessage!!,
                actionLabel = snackbarType,
                duration = duration
            )

            snackbarMessage = null
            snackbarType = null
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

        // TOUT EST DANS UNE SEULE BOX
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = AppColors.Primary)
        ) {

            // Ton contenu
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

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = AppSpacing.XXXXXL)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = AppSpacing.S,
                            start = AppSpacing.L,
                            end = AppSpacing.L,
                            bottom = AppSpacing.L
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
                        items(foodList) { item ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { selectedItem = item }
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
                                    text = "${item.caloriesPerKg} kcal",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }

                if (!isAtTop) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(AppSpacing.XXXXL)
                            .align(Alignment.TopCenter)
                    )
                }

                if (!isAtEnd) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(AppSpacing.XXXXL)
                            .align(Alignment.BottomCenter)
                    )
                }
            }

            AddButton(
                onClick = { showDialogAdd = true },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(AppSpacing.L)
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .zIndex(1f)
                    .padding(AppSpacing.L),
                contentAlignment = Alignment.BottomCenter
            ) {
                CustomSnackbarHost(snackbarHostState)
            }

            if (showDialogAdd) {
                FoodDetailDialog(
                    foodName = "",
                    onDismiss = { showDialogAdd = false },
                    onSnackbarMessage = { msg, type ->
                        snackbarMessage = msg
                        snackbarType = type
                    }
                )
            }

        }
    }
    selectedItem?.let { item ->
        when {
            isDeleteConfirmationVisible -> {
                DeleteConfirmationDialog(
                    itemName = item.name,
                    onConfirm = {
                        //fridgeItems.remove(item) // ou ViewModel etc.
                        isDeleteConfirmationVisible = false
                        selectedItem = null
                    },
                    onDismiss = {
                        isDeleteConfirmationVisible = false
                        selectedItem = null
                    }
                )
            }
            isEditing -> {
                EditFoodDialog(
                    item = item,
                    onDismiss = {
                        isEditing = false
                        selectedItem = null
                    },
                    onEditConfirmed = { updatedItem ->
                        // mise à jour ici
                    },
                    onSnackbarMessage = { msg, type -> /* ... */ }
                )
            }
            else -> {
                FridgeItemActionDialog(
                    item = item,
                    onDismiss = { selectedItem = null },
                    onEditClick = { isEditing = true },
                    onDeleteClick = {
                        isDeleteConfirmationVisible = true
                        isEditing = false
                    }
                )
            }
        }

}
    if (isDeleteConfirmationVisible && selectedItem != null) {
        DeleteConfirmationDialog(
            itemName = selectedItem!!.name,
            onConfirm = {
                // Supprime ici ton item de fridgeItems
                // Exemple : fridgeItems.remove(selectedItem)
            },
            onDismiss = {
                isDeleteConfirmationVisible = false
                selectedItem = null
            }
        )
    }

}





