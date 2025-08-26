package com.pdfa.pdfa_app.user_interface.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.pdfa.pdfa_app.ui.theme.AppColors
import com.pdfa.pdfa_app.ui.theme.AppShapes
import com.pdfa.pdfa_app.ui.theme.AppSpacing
import com.pdfa.pdfa_app.ui.viewmodel.CookbookViewModel
import com.pdfa.pdfa_app.ui.viewmodel.RecipeViewModel
import com.pdfa.pdfa_app.user_interface.component.CookbookSection
import com.pdfa.pdfa_app.user_interface.component.CustomCookbookSearchBar
import com.pdfa.pdfa_app.user_interface.component.EditCookbook
import com.pdfa.pdfa_app.user_interface.component.ScrollbarPersonnalisee

@Composable
fun CookbookScreen(
    navController: NavController,
    recipeViewModel: RecipeViewModel,
    cookbookViewModel: CookbookViewModel = hiltViewModel()
){
    val cookbooks by cookbookViewModel.userCookbooks.collectAsState()

    val scrollState = rememberScrollState()
    var searchQuery by remember { mutableStateOf("") }
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
                    onValueChange = { searchQuery = it },
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
                cookbooks.forEach { (cookbook, recipes) ->
                    CookbookSection(
                        navController =  navController,
                        cookbookName = cookbook.name,
                        recipes = recipes,
                        recipeViewModel = recipeViewModel
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

        if (openEditCookbookDialog) {
            EditCookbook(
                onDismiss = { openEditCookbookDialog = false }
            )
        }

    }
}
