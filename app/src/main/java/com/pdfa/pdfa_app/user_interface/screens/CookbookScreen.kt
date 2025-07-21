package com.pdfa.pdfa_app.user_interface.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.pdfa.pdfa_app.ui.theme.AppColors
import com.pdfa.pdfa_app.ui.theme.AppShapes
import com.pdfa.pdfa_app.ui.theme.AppSpacing
import com.pdfa.pdfa_app.ui.theme.AppTypo
import com.pdfa.pdfa_app.user_interface.component.CookbookSection
import com.pdfa.pdfa_app.user_interface.component.CustomTextField
import com.pdfa.pdfa_app.user_interface.component.RecipeCard
import com.pdfa.pdfa_app.user_interface.component.ScrollbarPersonnalisee
import com.pdfa.pdfa_app.user_interface.component.SearchBar

@Composable
fun CookbookScreen(
    navController: NavController
){
    val scrollState = rememberScrollState()
    var searchQuery by remember { mutableStateOf("") }

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
                CustomTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = "Search...",
                    onFilterClick = {
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
                CookbookSection(navController)
                CookbookSection(navController)
                CookbookSection(navController)
                CookbookSection(navController)
                CookbookSection(navController)
                CookbookSection(navController)
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
