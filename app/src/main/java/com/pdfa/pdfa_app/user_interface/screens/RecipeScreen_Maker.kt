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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.pdfa.pdfa_app.ui.theme.AppColors
import com.pdfa.pdfa_app.ui.theme.AppShapes
import com.pdfa.pdfa_app.ui.theme.AppSpacing
import com.pdfa.pdfa_app.ui.theme.AppTypo
import com.pdfa.pdfa_app.user_interface.component.RecipeCard
import com.pdfa.pdfa_app.user_interface.component.RecipeParameter
import com.pdfa.pdfa_app.user_interface.component.ScrollbarPersonnalisee

@Composable
fun RecipeMakerScreen(
    navController : NavController
){

    val scrollState = rememberScrollState()
    var showDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Primary),
    ){
        Column(modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(AppSpacing.L),
            verticalArrangement = Arrangement.spacedBy(16.dp)

        ) {
            RecipeCard(navController)
            RecipeCard(navController)
            RecipeCard(navController)
            RecipeCard(navController)
            RecipeCard(navController)

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
                    .background(AppColors.Primary)
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
        RecipeParameter(onDismiss = { showDialog = false })
    }
}