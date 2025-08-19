package com.pdfa.pdfa_app.user_interface.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.RemoveShoppingCart
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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

    // 🔄 Récupération des données réelles
    val shoplistWithFood by shoplistViewModel.shoplistWithFood.collectAsState(initial = emptyList())

    Log.d("ShoplistScreen", "📊 Shoplist items: ${shoplistWithFood.size}")
    shoplistWithFood.forEach { item ->
        Log.d("ShoplistScreen", "  🛒 ${item.shoplist.quantity} ${item.shoplist.quantityType} de ${item.food?.name ?: "FOOD NULL"} (food_id: ${item.shoplist.foodId})")
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppColors.Primary)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(AppSpacing.XS),
            ) {
                Text(
                    text = "Ici, retrouve ta liste de course réalisée avec les recettes que tu as choisi",
                    style = AppTypo.SubTitle,
                    color = Color.Black
                )
            }

            // 📊 Affichage conditionnel selon les données
            if (shoplistWithFood.isEmpty()) {
                // Message si la liste est vide
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(AppSpacing.M),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Ta liste de course est vide.\nAjoute des recettes pour commencer !",
                        style = AppTypo.Body,
                        color = AppColors.MainGrey
                    )
                }
            } else {
                // 🔄 Affichage des données réelles
                shoplistWithFood.forEach { shoplistItem ->
                    val foodName = shoplistItem.food?.name ?: "Aliment inconnu"
                    val quantity = shoplistItem.shoplist.quantity
                    val unit = shoplistItem.shoplist.quantityType

                    ShoplistElement(
                        elementName = foodName,
                        elementQte = quantity,
                        elementQteName = unit
                    )
                }
            }

            // 🐛 Section de debug (à retirer en production)
            if (shoplistWithFood.isNotEmpty()) {
                Text(
                    text = "🐛 DEBUG: ${shoplistWithFood.size} éléments dans la liste",
                    style = AppTypo.Body,
                    color = Color.Red,
                    modifier = Modifier.padding(AppSpacing.S)
                )
            }
        }

        ScrollbarPersonnalisee(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .fillMaxHeight()
                .width(10.dp),
            scrollState = scrollState
        )

        FloatingActionButton(
            onClick = {
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(AppSpacing.L),
            containerColor = AppColors.MainGreen,
            contentColor = AppColors.MainGrey
        ) {
            Icon(
                imageVector = if (shoplistWithFood.isEmpty()) Icons.Filled.ShoppingCart else Icons.Filled.RemoveShoppingCart,
                contentDescription = if (shoplistWithFood.isEmpty()) "Liste vide" else "Vider la liste"
            )
        }
    }
}
