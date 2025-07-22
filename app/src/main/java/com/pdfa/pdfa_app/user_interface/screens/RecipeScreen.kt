package com.pdfa.pdfa_app.user_interface.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.pdfa.pdfa_app.ui.theme.AppColors
import com.pdfa.pdfa_app.ui.theme.AppShapes
import com.pdfa.pdfa_app.ui.viewmodel.RecipeViewModel

@Composable
fun RecipeScreen(
    navController: NavController,
    viewModel: RecipeViewModel
){

    var contenuActuel by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .background(AppColors.NavBackground)
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
                    .clip(AppShapes.CornerL)
                    .background(
                        if (contenuActuel == 0 ) AppColors.NavBackgroundHover else AppColors.NavBackground
                    )
                    .clickable { contenuActuel = 0 },
            contentAlignment = Alignment.Center

            ) {
                Text(
                    text = "Pour mes recettes",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
                    .clip(AppShapes.CornerL)
                    .background(
                        if (contenuActuel == 1 ) AppColors.NavBackgroundHover else AppColors.NavBackground
                    )
                    .clickable { contenuActuel = 1 },
                        contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Pour ma liste",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            when (contenuActuel) {
                0 -> RecipeMakerScreen(navController, viewModel)
                1 -> RecipeListScreen(navController, viewModel)
            }
        }

    }
}