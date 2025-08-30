package com.pdfa.pdfa_app.user_interface.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.pdfa.pdfa_app.ui.theme.AppColors
import com.pdfa.pdfa_app.ui.theme.AppSpacing
import com.pdfa.pdfa_app.ui.theme.AppTypo
import com.pdfa.pdfa_app.ui.viewmodel.RecipeViewModel
import com.pdfa.pdfa_app.user_interface.component.RecipeCardCheck
import com.pdfa.pdfa_app.user_interface.component.ScrollbarPersonnalisee

@Composable
fun ShoplistRecipeScreen(
    navController: NavController,
    recipeViewModel: RecipeViewModel
) {

    val recipesShoplist by recipeViewModel.recipesForShoplistFromCookbook.collectAsState()
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Primary),
    ){

        if (recipesShoplist.isEmpty()) {

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
            Column(modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(AppSpacing.L),
                verticalArrangement = Arrangement.spacedBy(16.dp)

            ) {
                recipesShoplist.forEach { recipe ->
                    RecipeCardCheck(
                        navController = navController,
                        recipe = recipe,
                        recipeViewModel = recipeViewModel
                    )
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
    }
}