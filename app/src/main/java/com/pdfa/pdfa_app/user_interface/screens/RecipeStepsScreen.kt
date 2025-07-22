package com.pdfa.pdfa_app.user_interface.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.pdfa.pdfa_app.user_interface.component.ScrollbarPersonnalisee

@Composable
fun RecipeStepsScreen(
    navController: NavController,
    viewModel: RecipeViewModel
){

    val selectedRecipe by viewModel.selectedRecipe
    val scrollState = rememberScrollState()

    selectedRecipe?.let { recipe ->

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
        }
    }

}