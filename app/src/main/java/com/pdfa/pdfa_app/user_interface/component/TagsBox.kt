package com.pdfa.pdfa_app.user_interface.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.pdfa.pdfa_app.ui.theme.AppColors
import com.pdfa.pdfa_app.ui.theme.AppShapes
import com.pdfa.pdfa_app.ui.theme.AppSpacing
import com.pdfa.pdfa_app.ui.theme.AppTypo

@Composable
fun TagsBox(
    name: String,
    type: String,
    isColored: Boolean
    ){

    val colorTag: Color =
        when (type) {
            "Easy" -> AppColors.Easy
            "Allergy" -> AppColors.Hard
            "Diet" -> AppColors.Diet
            else -> {
                Color.White
            }
        }

    if (isColored) {
        Box(
            modifier = Modifier
                .wrapContentWidth()
                .height(32.dp) // Légèrement plus haut pour matcher l'image
                .padding(end = AppSpacing.S)
                .shadow(
                    elevation = 3.dp, // Ombre plus prononcée
                    shape = AppShapes.CornerM // Coins plus arrondis
                )
                .clip(AppShapes.CornerM)
                .background(colorTag)
        ){
            // Texte principal
            Text(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = 12.dp, vertical = 6.dp),
                text = name,
                style = AppTypo.TagBody,
                color = Color.Black,
            )
        }
    } else {
        Box(
            modifier = Modifier
                .wrapContentWidth()
                .height(32.dp)
                .padding(end = AppSpacing.S)
                .shadow(
                    elevation = 3.dp,
                    shape = AppShapes.CornerM
                )
                .clip(AppShapes.CornerM)
                .background(Color.White)
        ){
            // Texte principal
            Text(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = 12.dp, vertical = 6.dp),
                text = name,
                style = AppTypo.TagBody,
                color = Color.Black,
            )
        }
    }

}