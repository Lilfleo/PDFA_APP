package com.pdfa.pdfa_app.user_interface.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.pdfa.pdfa_app.ui.theme.AppColors
import com.pdfa.pdfa_app.ui.theme.AppShapes
import com.pdfa.pdfa_app.ui.theme.AppSpacing
import com.pdfa.pdfa_app.ui.theme.AppTypo
import com.pdfa.pdfa_app.ui.viewmodel.RecipeViewModel
import com.pdfa.pdfa_app.user_interface.component.RecipeItemCard
import com.pdfa.pdfa_app.user_interface.component.RecipeTimeCard
import com.pdfa.pdfa_app.user_interface.component.RecipeParameter
import com.pdfa.pdfa_app.user_interface.component.ScrollbarPersonnalisee

@Composable
fun RecipeMakerScreen(
    navController : NavController,
    viewModel: RecipeViewModel
){

    val recipes by viewModel.multipleRecipeWithoutFood
    val isLoading by viewModel.isLoading
    val error by viewModel.error

    val scrollState = rememberScrollState()
    var showDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Primary),
    ){
        when {
            isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(AppSpacing.XXXLL),
                            color = AppColors.MainGreen,
                            strokeWidth = 4.dp
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Génération de vos recettes...",
                            style = AppTypo.SubTitle,
                            color = AppColors.LightGrey
                        )
                    }
                }
            }

            error != null -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(32.dp)
                    ) {
                        Text(
                            text = "❌ Erreur",
                            style = AppTypo.Title,
                            color = Color.Red
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = error!!,
                            style = AppTypo.Body,
                            color = Color.Red,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            recipes != null -> {
                // ✅ Affichage du contenu avec la recette
                Column(modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(AppSpacing.L),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    recipes!!.recipes.forEach { recipeResponse ->
                        RecipeItemCard(navController, recipeResponse, viewModel)
                    }
                }
            }

            else -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Aucune recette disponible",
                        style = AppTypo.SubTitle,
                        color = AppColors.MainGreen
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

        Box(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .height(70.dp)
                .align(Alignment.BottomCenter)
        ){
            Box(
                modifier = Modifier
//                    .background(AppColors.Primary)
                    .padding(bottom = AppSpacing.L, start = AppSpacing.L, end = AppSpacing.L),
            ){
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(AppShapes.CornerL)
                        .background(AppColors.MainGreen)
                        .clickable {
                            showDialog = true
                        }
                ) {
                    Text(
                        text = "Plus de recette",
                        style = AppTypo.SubTitle,
                        color = Color.White
                    )
                }
            }
        }

    }

    if (showDialog) {
        RecipeParameter(
            isWithIngredient = true,
            onDismiss = { showDialog = false },
            viewModel = viewModel
        )
    }
}