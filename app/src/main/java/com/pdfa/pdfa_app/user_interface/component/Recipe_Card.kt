package com.pdfa.pdfa_app.user_interface.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.pdfa.pdfa_app.api.Recipe
import com.pdfa.pdfa_app.api.RecipeResponse
import com.pdfa.pdfa_app.ui.theme.AppShapes
import com.pdfa.pdfa_app.ui.theme.AppSpacing
import com.pdfa.pdfa_app.ui.theme.AppTypo
import com.pdfa.pdfa_app.ui.viewmodel.RecipeViewModel
import com.pdfa.pdfa_app.user_interface.rooting.Screen

@Composable
fun RecipeItemCard(
    navController: NavController,
    recipe: Recipe,
    viewModel: RecipeViewModel
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(AppSpacing.CardHeight)
            .shadow(
                elevation = 2.dp,
                shape = AppShapes.CornerL
            )
            .clip(AppShapes.CornerL)
            .background(Color.White)
            .clickable {
                viewModel.selectRecipe(recipe)
                navController.navigate(Screen.RecipeDetailScreen.rout)
            }
            .padding(AppSpacing.M),
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize(),
//            verticalArrangement = Arrangement.spacedBy(AppSpacing.M)
        ) {
            //Titre
            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = recipe.title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .weight(1f, fill = false),
                    style = AppTypo.SubTitle
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = "~${recipe.totalCookingTime}mn",
                    style = AppTypo.Body
                )
            }

            //Régime
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 2.dp)
                    .horizontalScroll(rememberScrollState()),

                ) {

                recipe.tags.tag?.forEach { x ->
                    TagsBox(x, "Easy", true)
                }
            }
            //TAG
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 2.dp)
                    .horizontalScroll(rememberScrollState()),
            ) {
                recipe.tags.diet?.forEach { x ->
                    TagsBox(x, "Diet", true)
                }
                recipe.tags.allergies?.forEach { x ->
                    TagsBox(x, "Allergy", true)
                }
            }
        }
    }
}