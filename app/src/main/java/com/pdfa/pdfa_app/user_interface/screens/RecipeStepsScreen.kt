package com.pdfa.pdfa_app.user_interface.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.pdfa.pdfa_app.ui.theme.AppColors
import com.pdfa.pdfa_app.ui.theme.AppShapes
import com.pdfa.pdfa_app.ui.theme.AppSpacing
import com.pdfa.pdfa_app.ui.theme.AppTypo
import com.pdfa.pdfa_app.ui.viewmodel.FoodDetailViewModel
import com.pdfa.pdfa_app.ui.viewmodel.RecipeViewModel
import com.pdfa.pdfa_app.user_interface.component.Confirmation
import com.pdfa.pdfa_app.user_interface.component.ScrollbarPersonnalisee
import com.pdfa.pdfa_app.user_interface.rooting.Screen

@Composable
fun RecipeStepsScreen(
    navController: NavController,
    viewModel: RecipeViewModel,
){

    val selectedRecipe by viewModel.selectedRecipe
    val scrollState = rememberScrollState()
    var openConfirmation by remember { mutableStateOf(false) }

    val foodDetailViewModel: FoodDetailViewModel = hiltViewModel()
    var canMakeRecipe by remember { mutableStateOf(false) }
    var isChecking by remember { mutableStateOf(true) }


    selectedRecipe?.let { recipe ->

        LaunchedEffect(recipe.ingredients) {
            isChecking = true
            canMakeRecipe = foodDetailViewModel.canMakeRecipe(recipe.ingredients)
            isChecking = false
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = AppColors.Primary)
                .padding(AppSpacing.M),
            contentAlignment = Alignment.TopCenter
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.spacedBy(AppSpacing.L)
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Les étapes de préparation",
                        style = AppTypo.SubTitle2,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )
                }
                recipe.steps.forEach { step ->
                    Text(
                        text = "- $step",
                        style = AppTypo.BodySteps
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
                            .background(if (canMakeRecipe) AppColors.MainGreen else AppColors.MainGrey)
                            .clickable {
                                if (canMakeRecipe) {
                                    openConfirmation = true
                                }
                            }
                    ) {
                        Text(
                            text = when {
                                isChecking -> "Vérification..."
                                canMakeRecipe -> "🍳 Préparer la recette"
                                else -> "❌ Ingrédients manquants"
                            },
                            style = AppTypo.SubTitle,
                            color = Color.White
                        )
                    }
                }
            }
        }
        if (openConfirmation) {
            Confirmation(
                title = "Supprimer ces aliments ?",
                desc = "Vous allez retirer tous les ingrédients de cette recette de votre frigo. Avez vous bien réalisé cette recette ?",
                cancelButton = "Annuler",
                validateButton = "Retirer",
                onConfirm = {
                    openConfirmation = false
                },
                onDelete = {
                    openConfirmation = false
                    navController.navigate(Screen.Recipe.rout)
                    foodDetailViewModel.prepareRecipe(recipe.ingredients) { success, message ->
                        //SNACBAR
                    }
                },
                onDismiss = {
                    openConfirmation = false
                }
            )
        }

    }
}