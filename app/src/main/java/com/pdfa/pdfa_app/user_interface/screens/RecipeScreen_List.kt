package com.pdfa.pdfa_app.user_interface.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.pdfa.pdfa_app.ui.theme.AppColors
import com.pdfa.pdfa_app.ui.theme.AppSpacing
import com.pdfa.pdfa_app.user_interface.component.RecipeCard
import com.pdfa.pdfa_app.user_interface.component.ScrollbarPersonnalisee

@Composable
fun RecipeListScreen(){

    val scrollState = rememberScrollState()

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
            RecipeCard()
            RecipeCard()
            RecipeCard()
            RecipeCard()
            RecipeCard()
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