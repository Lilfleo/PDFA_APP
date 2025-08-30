package com.pdfa.pdfa_app.user_interface.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.RemoveShoppingCart
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pdfa.pdfa_app.ui.theme.AppColors
import com.pdfa.pdfa_app.ui.theme.AppShapes
import com.pdfa.pdfa_app.ui.theme.AppSpacing
import com.pdfa.pdfa_app.ui.theme.AppTypo
import com.pdfa.pdfa_app.ui.viewmodel.ShoplistViewModel
import com.pdfa.pdfa_app.user_interface.component.ScrollbarPersonnalisee
import com.pdfa.pdfa_app.user_interface.component.ShoplistElement

@Composable
fun ShoplistListScreen(
    shoplistViewModel: ShoplistViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    // États
    val shoplistWithFood by shoplistViewModel.shoplistWithFood.collectAsState(initial = emptyList())
    val selectedItems by shoplistViewModel.selectedItems.collectAsState()
    val isMovingToFridge by shoplistViewModel.isMovingToFridge.collectAsState()
    val moveToFridgeResult by shoplistViewModel.moveToFridgeResult.collectAsState()

    // Gestion du résultat du déplacement
    LaunchedEffect(moveToFridgeResult) {
        moveToFridgeResult?.let { message ->
            // Afficher ton message (Snackbar, Toast, etc.)
            Log.d("ShoplistScreen", message)
            kotlinx.coroutines.delay(3000)
            shoplistViewModel.clearMoveToFridgeResult()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppColors.Primary)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {

            // Affichage des données
            if (shoplistWithFood.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(AppSpacing.M),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(32.dp)
                    ) {
                        Text(
                            text = "⚠\uFE0F Attention",
                            style = AppTypo.Title,
                            color = AppColors.MainGreen
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Ta liste de course est vide.\nAjoute des recettes à ta liste depuis l'onglet Recette!",
                            style = AppTypo.Body,
                            color = AppColors.MainGreen,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                ) {
                    shoplistWithFood.forEach { shoplistItem ->
                        val foodName = shoplistItem.food.name
                        val quantity = shoplistItem.shoplist.quantity
                        val unit = shoplistItem.shoplist.quantityType
                        val isSelected = selectedItems.contains(shoplistItem.shoplist.id)

                        ShoplistElement(
                            name = foodName,
                            quantity = quantity,
                            unit = unit,
                            isSelected = isSelected,
                            onSelectionChanged = { isChecked ->
                                shoplistViewModel.toggleItemSelection(shoplistItem.shoplist.id)
                            }
                        )
                    }
                }
            }
        }

        ScrollbarPersonnalisee(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .fillMaxHeight()
                .width(10.dp),
            scrollState = scrollState
        )

        // ✅ FAB adaptatif
        FloatingActionButton(
            onClick = {
                if (selectedItems.isNotEmpty()) {
                    shoplistViewModel.moveSelectedItemsToFridge()
                } else {
                    shoplistViewModel.clearShoplist()
                }
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(AppSpacing.L),
            containerColor = if (selectedItems.isNotEmpty()) AppColors.MainGreen else AppColors.MainGrey,
            contentColor = Color.White
        ) {
            if (isMovingToFridge) {
                CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            } else {
                Icon(
                    imageVector = when {
                        selectedItems.isNotEmpty() -> Icons.Filled.Add // Icône pour ajouter au frigo
                        shoplistWithFood.isEmpty() -> Icons.Filled.ShoppingCart
                        else -> Icons.Filled.RemoveShoppingCart
                    },
                    contentDescription = when {
                        selectedItems.isNotEmpty() -> "Ajouter au frigo (${selectedItems.size})"
                        shoplistWithFood.isEmpty() -> "Liste vide"
                        else -> "Vider la liste"
                    }
                )
            }
        }
    }
}

