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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.pdfa.pdfa_app.data.model.Recipe
import com.pdfa.pdfa_app.data.model.Tag
import com.pdfa.pdfa_app.ui.theme.AppShapes
import com.pdfa.pdfa_app.ui.theme.AppSpacing
import com.pdfa.pdfa_app.ui.viewmodel.CookbookViewModel
import com.pdfa.pdfa_app.ui.viewmodel.DietViewModel
import com.pdfa.pdfa_app.ui.viewmodel.RecipeViewModel
import com.pdfa.pdfa_app.ui.viewmodel.TagViewModel
import com.pdfa.pdfa_app.user_interface.rooting.Screen

@Composable
fun RecipeCardCheck(
    navController: NavController,
    recipe: Recipe,
    recipeViewModel: RecipeViewModel,
    tagViewModel: TagViewModel = hiltViewModel(),
    dietViewModel: DietViewModel = hiltViewModel(),
    cookbookViewModel: CookbookViewModel = hiltViewModel()
) {

    var showDeleteConfirmation by remember { mutableStateOf(false) }
    val allDiet by dietViewModel.diets.collectAsState()
    var recipeDiets by remember { mutableStateOf(listOf<String>()) }
    var recipeTags by remember { mutableStateOf(listOf<Tag>()) }

    LaunchedEffect(recipe, allDiet) {
        if (allDiet.isNotEmpty()) {
            val tags = mutableListOf<Tag>()
            recipe.tags.tag?.forEach { tagList ->
                tagViewModel.getTagByName(
                    name = tagList
                ) { convertedTags ->
                    if (convertedTags != null) {
                        tags.add(convertedTags)
                        recipeTags = tags.toList()
                    }
                }
            }

            recipe.tags.diet?.let { recipeDietList ->
                val dietNames = allDiet.map { it.name }.toSet()
                recipeDiets = recipeDietList.filter { dietName ->
                    dietNames.contains(dietName)
                }
            }
        }
    }

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
                recipeViewModel.selectRecipe(recipe)
                navController.navigate(Screen.RecipeDetailScreen.rout)
            }
            .padding(AppSpacing.M),
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            //Titre
            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = recipe.title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .weight(1f, fill = false),
                )
                Spacer(modifier = Modifier.width(2.dp))
                Box(
                    modifier = Modifier
                        .size(AppSpacing.XXXL)
                        .clickable{
                            showDeleteConfirmation = true
                        },
                    contentAlignment = Alignment.Center
                ){
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = Color.Black,
                        modifier = Modifier.size(AppSpacing.XL)
                    )
                }
            }

            //Régime
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(top = 2.dp, end = 2.dp)
                    .horizontalScroll(rememberScrollState()),

                ) {
                recipeTags.forEach { x ->
                    TagsBox(
                        tag = x,
                        isSelected = true
                    )
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
                recipeDiets.forEach { x ->
                    OldTagsBox(x, "Diet", true)
                }
                recipe.tags.allergies?.forEach { x ->
                    OldTagsBox(x, "Allergy", true)
                }
            }
        }
    }

    if (showDeleteConfirmation){
        DeleteConfirmationGeneral(
            title = "Supprimer cette recette ?",
            desc = "Es-tu sûr de vouloir supprimer \"${recipe.title}\" de votre liste de course? Cette action est irréversible et supprimera les ingrédients associés de votre liste.",
            onConfirm = {
                showDeleteConfirmation = false

            },
            onDismiss = {
                showDeleteConfirmation = false
            },
            onDelete = {
                showDeleteConfirmation = false
                cookbookViewModel.removeRecipeAndIngredientFromCookbook(3, recipe)
            }
        )
    }
}