package com.pdfa.pdfa_app.user_interface.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.pdfa.pdfa_app.data.model.Tag
import com.pdfa.pdfa_app.ui.theme.AppColors
import com.pdfa.pdfa_app.ui.theme.AppShapes
import com.pdfa.pdfa_app.ui.theme.AppSpacing
import com.pdfa.pdfa_app.ui.theme.AppTypo


@Composable
fun TagsBox(
    tag: Tag,
    isSelected: Boolean = true,
    onRemove: (() -> Unit)? = null,
    onClick: (() -> Unit)? = null
){
    val colorTag: Color =
        if (isSelected) {
            when (tag.color) {
                "Easy" -> AppColors.Easy
                "Allergy" -> AppColors.Hard
                "Diet" -> AppColors.Diet
                else -> {
                    Color.White
                }
            }
        } else {
            Color.White
        }

    Box(
        modifier = Modifier
            .wrapContentSize()
    ) {
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
                .clickable { onClick?.let { onClick() } }
        ){
            // Texte principal
            Text(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = 12.dp, vertical = 6.dp),
                text = tag.name,
                style = AppTypo.TagBody,
                color = Color.Black,
            )
        }
        // Croix de suppression (si onRemove est fourni)
        onRemove?.let {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = 2.dp, y = (-10).dp) // Positionnement en dehors du tag
                    .size(AppSpacing.XL)
                    .clip(CircleShape)
                    .background(Color.Black)
                    .clickable { onRemove() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Supprimer ${tag.name}",
                    tint = Color.White,
                    modifier = Modifier.size(AppSpacing.L)
                )
            }
        }
    }
}

@Composable
fun OldTagsBox(
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