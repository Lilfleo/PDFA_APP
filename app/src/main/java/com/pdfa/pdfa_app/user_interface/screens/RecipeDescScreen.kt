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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.outlined.BookmarkAdd
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.pdfa.pdfa_app.api.Recipe
import com.pdfa.pdfa_app.data.model.Tag
import com.pdfa.pdfa_app.ui.theme.AppColors
import com.pdfa.pdfa_app.ui.theme.AppShapes
import com.pdfa.pdfa_app.ui.theme.AppSpacing
import com.pdfa.pdfa_app.ui.theme.AppTypo
import com.pdfa.pdfa_app.ui.viewmodel.RecipeViewModel
import com.pdfa.pdfa_app.ui.viewmodel.ShoplistViewModel
import com.pdfa.pdfa_app.ui.viewmodel.TagViewModel
import com.pdfa.pdfa_app.user_interface.component.AddToCookbook
import com.pdfa.pdfa_app.user_interface.component.OldTagsBox
import com.pdfa.pdfa_app.user_interface.component.RecipeTimeCard
import com.pdfa.pdfa_app.user_interface.component.ScrollbarPersonnalisee
import com.pdfa.pdfa_app.user_interface.component.TagsBox
import com.pdfa.pdfa_app.user_interface.rooting.Screen

@Composable
fun RecipeDetailScreen(
    navController: NavController,
    viewModel: RecipeViewModel,
    tagViewModel: TagViewModel = hiltViewModel(),
    shoplistViewModel: ShoplistViewModel = hiltViewModel()
){

    var nbPeople by remember { mutableIntStateOf(1) }
    var expanded by remember { mutableStateOf(false) }
    var addToCookbook by remember { mutableStateOf(false) }
    var openAddToCookbookDialog by remember { mutableStateOf(false) }
    val selectedRecipe by viewModel.selectedRecipe
    val scrollState = rememberScrollState()

    val recipe by viewModel.selectedRecipe
    val isAddingToShoplist by shoplistViewModel.isAddingToShoplist.collectAsState()
    val addResult by shoplistViewModel.addToShoplistResult.collectAsState()

    selectedRecipe?.let { recipe ->

        var recipeTags by remember { mutableStateOf(listOf<Tag>()) }

        LaunchedEffect(recipe) {
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
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(AppColors.Primary)
                .padding(AppSpacing.M)
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {

                //Titre
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        text = recipe.title,
                        modifier = Modifier
                            .weight(1f, fill = false),
                        style = AppTypo.SubTitle2,
                        color = Color.Black
                    )

                    Box(
                        modifier = Modifier
                            .height(AppSpacing.XXXXL)
                            .width(AppSpacing.XXXXL)
                            .background(
                                color = Color.White,
                                shape = AppShapes.CornerL
                            )
                            .clip(shape = AppShapes.CornerL)
                            .clickable {
                                addToCookbook = !addToCookbook
                                openAddToCookbookDialog = true
                            },
                        contentAlignment = Alignment.Center
                    ){
                        Icon(
                            imageVector = if(addToCookbook) Icons.Default.Bookmark else Icons.Outlined.BookmarkAdd,
                            contentDescription = "Add to Cookbook"
                        )
                    }
                }
                //Subtitle
                Text(
                    text = recipe.subTitle,
                    style = AppTypo.BodyLight,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.padding(AppSpacing.XXS))

                //Tags
                Row {
                    recipeTags.forEach { x ->
                        TagsBox(x, true)
                    }
                }

                Spacer(modifier = Modifier.padding(AppSpacing.XXXS))

                //Régimes
                Row {
                    recipe.tags.diet?.forEach { x ->
                        OldTagsBox(x, "Diet", true)
                    }
                    recipe.tags.allergies?.forEach { x ->
                        OldTagsBox(x, "Allergy", true)
                    }
                }

                //Temps
                Row(
                    modifier = Modifier
                        .padding(vertical = AppSpacing.M),
                    horizontalArrangement = Arrangement.spacedBy(AppSpacing.M)
                ) {
                    RecipeTimeCard(recipe.preparationTime, "time")

                    RecipeTimeCard(recipe.totalCookingTime, "time")

//                    RecipeTimeCard("20", "calories")
                }

                Text(
                    text = "Ingrédient"
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Pour"
                    )
                    Spacer(Modifier.padding(AppSpacing.XXXXS))
                    Box(
                        modifier = Modifier
                            .width(50.dp)
                            .height(30.dp)
                            .padding(AppSpacing.XXXS)
                    ){
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .shadow(
                                    elevation = 2.dp,
                                    shape = AppShapes.CornerS
                                )
                                .background(
                                    color = Color.White,
                                    shape = AppShapes.CornerS
                                )
                                .clickable { expanded = !expanded },
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ){
                            Text(
                                text = nbPeople.toString(),
                                textAlign = TextAlign.Center,
                                modifier = Modifier.weight(1f)
                            )
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowDown,
                                contentDescription = "Back",
                                modifier = Modifier.weight(1f)

                            )
                            DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false },
                                modifier = Modifier
                                    .width(40.dp)
                                    .background(Color.White)
                            ) {
                                DropdownMenuItem(
                                    text = { Text("1") },
                                    onClick = { nbPeople = 1; expanded = false },
                                    modifier = Modifier
                                        .height(30.dp)
                                )
                                DropdownMenuItem(
                                    text = { Text("2") },
                                    onClick = { nbPeople = 2; expanded = false },
                                    modifier = Modifier
                                        .height(30.dp)
                                )
                                DropdownMenuItem(
                                    text = { Text("3") },
                                    onClick = { nbPeople = 3; expanded = false },
                                    modifier = Modifier
                                        .height(30.dp)
                                )
                                DropdownMenuItem(
                                    text = { Text("4") },
                                    onClick = { nbPeople = 4; expanded = false },
                                    modifier = Modifier
                                        .height(30.dp)
                                )
                                DropdownMenuItem(
                                    text = { Text("5") },
                                    onClick = { nbPeople = 6; expanded = false },
                                    modifier = Modifier
                                        .height(30.dp)
                                )
                                DropdownMenuItem(
                                    text = { Text("7") },
                                    onClick = { nbPeople = 7; expanded = false },
                                    modifier = Modifier
                                        .height(30.dp)
                                )
                                DropdownMenuItem(
                                    text = { Text("8") },
                                    onClick = { nbPeople = 8; expanded = false },
                                    modifier = Modifier
                                        .height(30.dp)
                                )
                                DropdownMenuItem(
                                    text = { Text("9") },
                                    onClick = { nbPeople = 9; expanded = false },
                                    modifier = Modifier
                                        .height(30.dp)
                                )
                                DropdownMenuItem(
                                    text = { Text("10") },
                                    onClick = { nbPeople = 10; expanded = false },
                                    modifier = Modifier
                                        .height(30.dp)
                                )
                            }
                        }
                    }

                    Spacer(Modifier.padding(AppSpacing.XXXXS))
                    Text(
                        text = "Personnes"
                    )
                }

                //Liste des ingrédients
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(vertical = AppSpacing.M)
                        .shadow(
                            elevation = 2.dp,
                            shape = AppShapes.CornerL
                        )
                        .background(
                            color = Color.White,
                            shape = AppShapes.CornerM
                        )
                ){
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(scrollState),
                    ) {
                        recipe.ingredients.forEach { ingredient ->

                            val ingredientQte = ingredient.quantity?.times(nbPeople)

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(AppSpacing.XXXXL)
                                    .padding(horizontal = AppSpacing.M, AppSpacing.S),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
//                                Box(
//                                    modifier = Modifier
//                                        .size(AppSpacing.XXXXL)
//                                        .background(Color.LightGray, shape = AppShapes.CornerXS)
//                                )
//                                Spacer(modifier = Modifier.width(AppSpacing.M))
                                Text(
                                    text = ingredient.name,
                                    modifier = Modifier.weight(1f),
                                    style = MaterialTheme.typography.bodyLarge
                                )
                                Text(
                                    text = "$ingredientQte ${ingredient.unit}",
                                    style = MaterialTheme.typography.bodyMedium
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

                //Boutons
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f)
                            .clip(AppShapes.CornerL)
                            .background(AppColors.MainGreen)
                            .clickable {
                                shoplistViewModel.addRecipeToShoplist(recipe)
                            }
                    ) {
                        Text(
                            text = "Ajouter à la liste",
                            style = AppTypo.SubTitle,
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.padding(AppSpacing.XXS))

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f)
                            .clip(AppShapes.CornerL)
                            .background(AppColors.MainGreen)
                            .clickable {
                                navController.navigate(Screen.RecipeStepsScreen.rout)
                            }
                    ) {
                        Text(
                            text = "Voir la recette",
                            style = AppTypo.SubTitle,
                            color = Color.White
                        )
                    }
                }
            }

            if (openAddToCookbookDialog) {
                AddToCookbook( onDismiss = { openAddToCookbookDialog = false })
            }
        }
    }
}