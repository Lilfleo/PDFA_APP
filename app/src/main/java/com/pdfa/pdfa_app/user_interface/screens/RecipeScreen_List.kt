package com.pdfa.pdfa_app.user_interface.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.ui.Alignment
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.pdfa.pdfa_app.ui.theme.AppColors
import com.pdfa.pdfa_app.ui.theme.AppShapes
import com.pdfa.pdfa_app.ui.theme.AppSpacing
import com.pdfa.pdfa_app.ui.theme.AppTypo
import com.pdfa.pdfa_app.ui.viewmodel.RecipeViewModel
import com.pdfa.pdfa_app.user_interface.component.RecipeItemCard
import com.pdfa.pdfa_app.user_interface.component.RecipeParameter
import com.pdfa.pdfa_app.user_interface.component.ScrollbarPersonnalisee

@Composable
fun RecipeListScreen(
    navController: NavController,
    viewModel: RecipeViewModel
){
    // 🔄 Changer pour utiliser les nouvelles propriétés
    val recipesFromCookbook by viewModel.recipesWithoutFoodFromCookbook.collectAsState()
    val isLoading by viewModel.isLoadingWithoutFood
    val isGeneratingMore by viewModel.isGeneratingMoreWithoutFood.collectAsState()
    val error by viewModel.errorWithoutFood

    val scrollState = rememberScrollState()
    var showDialog by remember { mutableStateOf(false) }

    // 🎯 Effet pour scroller vers le bas quand de nouvelles recettes arrivent
    LaunchedEffect(recipesFromCookbook.size) {
        if (isGeneratingMore && recipesFromCookbook.isNotEmpty()) {
            scrollState.animateScrollTo(scrollState.maxValue)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Primary),
    ){
        when {
            // 🔄 Loading initial (première génération)
            isLoading && recipesFromCookbook.isEmpty() -> {
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

            // ❌ Erreur ET pas de recettes existantes
            error != null && recipesFromCookbook.isEmpty() -> {
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

            // ✅ Affichage des recettes (même s'il y a une erreur mais qu'on a des recettes)
            recipesFromCookbook.isNotEmpty() -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                        .padding(AppSpacing.L),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // 📋 Afficher toutes les recettes du cookbook
                    recipesFromCookbook.forEach { recipe ->
                        RecipeItemCard(navController, recipe, viewModel)
                    }

                    // 🔄 Indicateur de génération en cours (en bas)
                    if (isGeneratingMore) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(32.dp),
                                    color = AppColors.MainGreen,
                                    strokeWidth = 3.dp
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Génération de nouvelles recettes...",
                                    style = AppTypo.SubTitle,
                                    color = AppColors.LightGrey
                                )
                            }
                        }
                    }

                    // Espace pour le bouton flottant
                    Spacer(modifier = Modifier.height(80.dp))
                }

                // 🚨 Afficher l'erreur en Snackbar si il y a des recettes existantes
                error?.let { errorMessage ->
                    LaunchedEffect(errorMessage) {
                        // Tu peux utiliser un SnackbarHost ou un Toast ici
                        Log.e("RecipeListScreen", "Erreur: $errorMessage")
                        // Optionnel: afficher un toast ou snackbar
                    }
                }
            }

            // 🔍 État vide (pas de recettes, pas de loading)
            else -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(32.dp)
                    ) {
                        Text(
                            text = "🍳",
                            style = AppTypo.Title.copy(fontSize = 48.sp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Aucune recette disponible",
                            style = AppTypo.SubTitle,
                            color = AppColors.MainGreen,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Créez vos premières recettes !",
                            style = AppTypo.Body,
                            color = AppColors.LightGrey,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }

        // 📜 Scrollbar (seulement si on a des recettes)
        if (recipesFromCookbook.isNotEmpty()) {
            ScrollbarPersonnalisee(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .fillMaxHeight()
                    .width(10.dp),
                scrollState = scrollState
            )
        }

        // ➕ Bouton pour plus de recettes
        Box(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .height(70.dp)
                .align(Alignment.BottomCenter)
        ){
            Box(
                modifier = Modifier
                    .padding(bottom = AppSpacing.L, start = AppSpacing.L, end = AppSpacing.L),
            ){
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(AppShapes.CornerL)
                        .background(
                            if (isGeneratingMore) AppColors.MainGreen.copy(alpha = 0.7f)
                            else AppColors.MainGreen
                        )
                        .clickable(enabled = !isGeneratingMore) {
                            showDialog = true
                        }
                ) {
                    if (isGeneratingMore) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                color = Color.White,
                                strokeWidth = 2.dp
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Génération...",
                                style = AppTypo.SubTitle,
                                color = Color.White
                            )
                        }
                    } else {
                        Text(
                            text = if (recipesFromCookbook.isEmpty()) "Créer des recettes" else "Plus de recettes",
                            style = AppTypo.SubTitle,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }

    if (showDialog) {
        RecipeParameter(
            navController = navController,
            isWithIngredient = false,
            onDismiss = { showDialog = false },
            viewModel = viewModel
        )
    }
}
