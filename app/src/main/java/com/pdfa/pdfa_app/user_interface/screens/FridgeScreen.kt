package com.pdfa.pdfa_app.user_interface.screens

import CustomCheckbox
import FoodAddDetailDialog
import FoodModifDialog


import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Checkbox
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.pdfa.pdfa_app.data.model.Food
import com.pdfa.pdfa_app.data.model.FoodDetailWithFood

import com.pdfa.pdfa_app.ui.theme.AppColors
import com.pdfa.pdfa_app.ui.theme.AppShapes
import com.pdfa.pdfa_app.ui.theme.AppSpacing
import com.pdfa.pdfa_app.ui.theme.AppTypo
import com.pdfa.pdfa_app.ui.viewmodel.FoodDetailViewModel
import com.pdfa.pdfa_app.ui.viewmodel.FoodViewModel

import com.pdfa.pdfa_app.user_interface.component.AddButton
import com.pdfa.pdfa_app.user_interface.component.CustomDropdown
import com.pdfa.pdfa_app.user_interface.component.CustomFridgeSearchBar
import com.pdfa.pdfa_app.user_interface.component.CustomSnackbarHost
import com.pdfa.pdfa_app.user_interface.component.DeleteConfirmationDialog
import com.pdfa.pdfa_app.user_interface.component.FridgeItemActionDialog
import com.pdfa.pdfa_app.user_interface.component.SearchBar

@Composable
fun FridgeScreen(
    onAddClick: () -> Unit,
    foodDetailviewModel: FoodDetailViewModel = hiltViewModel()
) {
    val foodDetail by foodDetailviewModel.foodDetail.collectAsState()
    var selectedFoodId by remember { mutableStateOf<Int?>(null) }
    var foodToEdit by remember { mutableStateOf<FoodDetailWithFood?>(null) }
    var showDialogEdit by remember { mutableStateOf(false) }
    val foodViewModel: FoodViewModel = hiltViewModel()
    val foodList by foodViewModel.foodList.collectAsState()

    var expanded by remember { mutableStateOf(false) }
    var selectedFoodName by remember { mutableStateOf("") }
    var selectedCategorie by remember { mutableStateOf("") }

    var searchQuery by remember { mutableStateOf("") }
    var showDialogAdd by remember { mutableStateOf(false) }
    var showFilterBox by remember { mutableStateOf(false) }
    var filter_1 by remember { mutableStateOf(false) }
    var filter_2 by remember { mutableStateOf(false) }
    var filter_3 by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }
    Log.d("FridgeScreen", "SnackbarHostState created: $snackbarHostState")

    var snackbarMessage by remember { mutableStateOf<String?>(null) }
    var snackbarType by remember { mutableStateOf<String?>(null) }
    var selectedItem by remember { mutableStateOf<Food?>(null) }

    var selectedFoodDetail by remember { mutableStateOf<FoodDetailWithFood?>(null) }
    var isDeleteConfirmationVisible by remember { mutableStateOf(false) }


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
    LaunchedEffect(selectedFoodId, foodDetail) {
        selectedFoodId?.let { id ->
            val detail = foodDetail.find { it.food.id == id }
            if (detail != null) {
                foodToEdit = detail
                showDialogEdit = true
            }
            selectedFoodId = null // reset pour éviter des doublons
        }
    }

    if (showDialogAdd) {
        FoodAddDetailDialog(
            foodList = foodList,
            onDismiss = { showDialogAdd = false },
            onSnackbarMessage = { msg, type ->
                snackbarMessage = msg
                snackbarType = type
            }
        )
    }



    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = AppColors.Primary)
    ) {
        // Barre de recherche FIXE
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(AppSpacing.S)
//                .align(Alignment.TopCenter)
//        ) {
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                SearchBar(
//                    query = searchQuery,
//                    onQueryChange = { searchQuery = it },
//                    modifier = Modifier.weight(1f)
//                )
//                IconButton(
//                    onClick = { },
//                    modifier = Modifier.padding(start = AppSpacing.S)
//                ) {
//                    Icon(Icons.Default.Tune, contentDescription = "Filter")
//                }
//            }
//        }

        // TOUT EST DANS UNE SEULE BOX
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = AppColors.Primary)
        ) {

            // Ton contenu
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppSpacing.L, vertical = AppSpacing.S)
                    .shadow(
                        elevation = 5.dp,
                        shape = AppShapes.CornerL
                    )
                    .background(
                        color = Color.White,
                        shape = AppShapes.CornerL
                    )
                    .animateContentSize(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioNoBouncy,
                            stiffness = Spring.StiffnessHigh
                        )
                    ),
            ) {
                Row{
                    CustomFridgeSearchBar(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        onFilterClick = { showFilterBox = !showFilterBox }
                    )
                }

                AnimatedVisibility(
                    visible = showFilterBox,
                    enter = fadeIn(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioNoBouncy,
                            stiffness = Spring.StiffnessMedium
                        )
                    ) + expandVertically(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioNoBouncy,
                            stiffness = Spring.StiffnessMedium
                        )
                    ),
                    exit = fadeOut(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioNoBouncy,
                            stiffness = Spring.StiffnessMedium // Plus rapide que shrinkVertically
                        )
                    )
                            + shrinkVertically(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioNoBouncy,
                            stiffness = Spring.StiffnessMedium
                        )
                    )
                ) {

                    Column(
                        modifier = Modifier.padding(vertical = AppSpacing.S, horizontal = AppSpacing.L),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            text = "Trier par :",
                            style = AppTypo.Body,
                            color = Color.Black
                        )

                        FlowRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalArrangement = Arrangement.spacedBy(5.dp)
                        ){
                            Row (
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(3.dp)
                            ) {
                                CustomCheckbox(
                                    checked = filter_1,
                                    onCheckedChange = { filter_1 = it }
                                )
                                Text(
                                    text = "Qté croissante",
                                    style = AppTypo.Body,
                                    color = Color.Black
                                )
                            }
                            Row (
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(2.dp)
                            ) {
                                CustomCheckbox(
                                    checked = filter_2,
                                    onCheckedChange = { filter_2 = it }
                                )
                                Text(
                                    text = "Qté décroissante",
                                    style = AppTypo.Body,
                                    color = Color.Black
                                )
                            }
                            Row (
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(2.dp)
                            ) {
                                CustomCheckbox(
                                    checked = filter_3,
                                    onCheckedChange = { filter_3 = it }
                                )
                                Text(
                                    text = "Date de péremption",
                                    style = AppTypo.Body,
                                    color = Color.Black
                                )
                            }
                        }

                        Text(
                            text = "Filtrer par :",
                            style = AppTypo.Body,
                            color = Color.Black
                        )

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Catégorie",
                                style = AppTypo.Body,
                                color = Color.Black
                            )

                            Spacer(modifier = Modifier.padding(AppSpacing.M))

                            Box(
                                modifier = Modifier
                                    .wrapContentWidth()
                            ) {
                                CustomDropdown(
                                    selectedValue = selectedCategorie,
                                    placeholder = "Catégories",
                                    onItemSelected = { item ->
                                        selectedCategorie = item },
                                    elements = listOf("Fruits", "Légumes", "Viandes", "Poissons"),
                                )
                            }
                        }
                    }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
//                    .padding(top = AppSpacing.XXXXXL)
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
                    if (foodDetail.isNotEmpty()) {
                        LazyColumn(
                            state = listState,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            items(
                                items = foodDetail,
                                key = { it.foodDetail.id } // 🧠 clé unique et persistante = identifiant en base
                            ) { detail ->
                                Log.d("FridgeScreen", "🌀 Recomposition avec : ${detail.food.name}")

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            selectedFoodDetail = detail
                                        }
                                        .padding(
                                            horizontal = AppSpacing.M,
                                            vertical = AppSpacing.S
                                        ),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(AppSpacing.XXXXL)
                                            .background(Color.LightGray, shape = AppShapes.CornerXS)
                                    )
                                    Spacer(modifier = Modifier.width(AppSpacing.M))
                                    Text(
                                        text = detail.food.name,
                                        modifier = Modifier.weight(1f),
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                    Text(
                                        text = "${detail.foodDetail.quantity} ${detail.foodDetail.unit}",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }
                        }
                    } else {
                        // éventuellement un message "Frigo vide 🍽️"
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(AppSpacing.L),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Ton frigo est vide pour l’instant")
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


            }
        }
        // 🔄 Fenêtre de gestion sur clic d'un aliment
        selectedFoodDetail?.let { detail ->
            FridgeItemActionDialog(
                item = detail,
                onDismiss = {
                    selectedFoodDetail = null
                },
                onEditClick = {
                    foodToEdit = detail
                    selectedFoodDetail = null
                    showDialogEdit = true
                },
                onDeleteClick = {
                    selectedItem = detail.food
                    selectedFoodDetail = null
                    isDeleteConfirmationVisible = true
                }
            )
        }

// 🗑️ Confirmation de suppression
        if (isDeleteConfirmationVisible && selectedItem != null) {
            val foodDetailToDelete = foodDetail.find { it.food.id == selectedItem!!.id }?.foodDetail

            if (foodDetailToDelete != null) {
                DeleteConfirmationDialog(
                    itemName = selectedItem!!.name,
                    foodDetail = foodDetailToDelete, // 👈 nouveau paramètre
                    onConfirm = {
                        isDeleteConfirmationVisible = false
                        selectedItem = null
                    },
                    onDismiss = {
                        isDeleteConfirmationVisible = false
                        selectedItem = null
                    },
                    onDelete = {
                        foodDetailviewModel.deleteFoodDetail(it)
                        snackbarMessage = "Supprimé"
                        snackbarType = "success"
                        isDeleteConfirmationVisible = false  // 👈 indispensable
                        selectedItem = null                  // 👈 sinon ça se relance
                    }
                )
            }
        }


// ✍️ Dialog de modification
        if (showDialogEdit && foodToEdit != null) {
            FoodModifDialog(
                foodToEdit = foodToEdit,
                foodList = foodList, // ✅ fourni depuis ton viewmodel
                onDismiss = {
                    showDialogEdit = false
                    foodToEdit = null
                },
                onSnackbarMessage = { msg, type ->
                    snackbarMessage = msg
                    snackbarType = type
                }
            )
        }
    }
}





