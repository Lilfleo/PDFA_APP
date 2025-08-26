package com.pdfa.pdfa_app.user_interface.screens

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import com.pdfa.pdfa_app.user_interface.component.RecipeParameter
import com.pdfa.pdfa_app.user_interface.component.ScrollbarPersonnalisee

@Composable
fun RecipeMakerScreen(
    navController : NavController,
    viewModel: RecipeViewModel
){
    // 🔄 Utiliser les nouvelles propriétés pour les recettes sauvegardées
    val savedRecipes by viewModel.recipesWithFoodFromCookbook.collectAsState()
    val isLoading by viewModel.isLoadingWithFood
    val isGeneratingMore by viewModel.isGeneratingMoreWithFood.collectAsState()
    val error by viewModel.errorWithFood

    val scrollState = rememberScrollState()
    var showDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Primary),
    ){
        when {
            isLoading -> {
                // 📱 Loading complet - première génération
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
                // ❌ Gestion d'erreur
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = if (savedRecipes.isEmpty())
                        Arrangement.Center else Arrangement.Top
                ) {
                    // Si on a des recettes, les afficher d'abord
                    if (savedRecipes.isNotEmpty()) {
                        Column(modifier = Modifier
                            .weight(1f)
                            .verticalScroll(scrollState)
                            .padding(AppSpacing.L),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            savedRecipes.forEach { recipe ->
                                RecipeItemCard(navController, recipe, viewModel)
                            }
                        }
                    }

                    // Message d'erreur
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
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
            }

            savedRecipes.isNotEmpty() -> {
                // ✅ Affichage des recettes sauvegardées
                Column(modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(AppSpacing.L),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    savedRecipes.forEach { recipe ->
                        RecipeItemCard(navController, recipe, viewModel)
                    }

                    // 🔄 Indicateur de génération en cours (en bas de la liste)
                    if (isGeneratingMore) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(24.dp),
                                    color = AppColors.MainGreen,
                                    strokeWidth = 3.dp
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = "Nouvelles recettes en cours...",
                                    style = AppTypo.Body,
                                    color = AppColors.LightGrey
                                )
                            }
                        }
                    }

                    // Espace pour le bouton fixe en bas
                    Spacer(modifier = Modifier.height(80.dp))
                }
            }

            else -> {
                // 🌟 État initial - aucune recette
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Créez vos premières recettes avec vos ingrédients !",
                        style = AppTypo.SubTitle,
                        color = AppColors.MainGreen,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(32.dp)
                    )
                }
            }
        }

        // 📜 Scrollbar
        ScrollbarPersonnalisee(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .fillMaxHeight()
                .width(10.dp),
            scrollState = scrollState
        )

        // 🎯 Bouton fixe en bas (adaptatif)
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
                            text = if (savedRecipes.isEmpty()) "Créer des recettes" else "Plus de recettes",
                            style = AppTypo.SubTitle,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }

    // 🎛️ Dialog des paramètres
    if (showDialog) {
        RecipeParameter(
            navController = navController,
            isWithIngredient = true,
            onDismiss = { showDialog = false },
            viewModel = viewModel
        )
    }
}
