package com.pdfa.pdfa_app.user_interface.screens

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.pdfa.pdfa_app.data.model.Recipe
import com.pdfa.pdfa_app.ui.theme.AppColors
import com.pdfa.pdfa_app.ui.theme.AppShapes
import com.pdfa.pdfa_app.ui.theme.AppSpacing
import com.pdfa.pdfa_app.ui.viewmodel.CookbookViewModel
import com.pdfa.pdfa_app.ui.viewmodel.RecipeViewModel
import com.pdfa.pdfa_app.user_interface.component.CookbookSection
import com.pdfa.pdfa_app.user_interface.component.CustomCookbookSearchBar
import com.pdfa.pdfa_app.user_interface.component.EditCookbook
import com.pdfa.pdfa_app.user_interface.component.RecipeItemCard
import com.pdfa.pdfa_app.user_interface.component.ScrollbarPersonnalisee

@Composable
fun CookbookScreen(
    navController: NavController,
    recipeViewModel: RecipeViewModel,
    cookbookViewModel: CookbookViewModel = hiltViewModel()
){
    val cookbooks by cookbookViewModel.userCookbooks.collectAsState()
    val searchQuery by cookbookViewModel.searchQuery.collectAsState()
    val searchResults by cookbookViewModel.searchResults.collectAsState()

    val scrollState = rememberScrollState()
    var openEditCookbookDialog by remember { mutableStateOf(false) }

    Column (modifier = Modifier
        .fillMaxSize()
        .background(AppColors.Primary),
    ){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(AppSpacing.S)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(
                        elevation = 5.dp,
                        shape = AppShapes.CornerL
                    )
                    .background(
                        color = Color.White,
                        shape = AppShapes.CornerL
                    ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                CustomCookbookSearchBar(
                    value = searchQuery,
                    onValueChange = { cookbookViewModel.updateSearchQuery(it) },
                    placeholder = "Search...",
                    onEditClick = {
                        openEditCookbookDialog = true
                    }
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.spacedBy(AppSpacing.XS)
            ) {
                // Section des résultats de recherche
                if (searchQuery.isNotBlank() && searchResults.isNotEmpty()) {
                    SearchResultsSection(
                        navController = navController,
                        searchResults = searchResults,
                        recipeViewModel = recipeViewModel,
                        searchQuery = searchQuery
                    )
                }

                // Message si aucun résultat
                if (searchQuery.isNotBlank() && searchResults.isEmpty()) {
                    NoSearchResultsSection(searchQuery = searchQuery)
                }

                // Sections cookbooks (masquées pendant la recherche ou affichées normalement)
                if (searchQuery.isBlank()) {
                    cookbooks.forEach { (cookbook, recipes) ->
                        CookbookSection(
                            navController = navController,
                            cookbookName = cookbook.name,
                            recipes = recipes,
                            recipeViewModel = recipeViewModel
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

        if (openEditCookbookDialog) {
            EditCookbook(
                onDismiss = { openEditCookbookDialog = false }
            )
        }
    }
}

@Composable
fun SearchResultsSection(
    navController: NavController,
    searchResults: List<Recipe>,
    recipeViewModel: RecipeViewModel,
    searchQuery: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = AppSpacing.S),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Liste des recettes trouvées
        searchResults.forEach { recipe ->
            RecipeItemCard(
                navController = navController,
                recipe = recipe,
                viewModel = recipeViewModel
            )
        }

        // Séparateur
        Spacer(modifier = Modifier.height(AppSpacing.M))
        Divider(
            color = Color.White.copy(alpha = 0.3f),
            thickness = 1.dp,
            modifier = Modifier.padding(horizontal = AppSpacing.M)
        )
        Spacer(modifier = Modifier.height(AppSpacing.S))
    }
}

@Composable
fun NoSearchResultsSection(searchQuery: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(AppSpacing.S),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),

        ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(AppSpacing.L),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.SearchOff,
                contentDescription = "Aucun résultat",
                tint = Color.Black.copy(alpha = 0.6f),
                modifier = Modifier.size(48.dp)
            )

            Spacer(modifier = Modifier.height(AppSpacing.S))

            Text(
                text = "Aucune recette trouvée",
                style = MaterialTheme.typography.titleMedium,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "pour \"$searchQuery\"",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black.copy(alpha = 0.7f)
            )
        }
    }
}
