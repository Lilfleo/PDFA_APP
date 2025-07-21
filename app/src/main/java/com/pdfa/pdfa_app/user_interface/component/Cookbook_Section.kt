package com.pdfa.pdfa_app.user_interface.component

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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.pdfa.pdfa_app.ui.theme.AppColors
import com.pdfa.pdfa_app.ui.theme.AppSpacing
import com.pdfa.pdfa_app.ui.theme.AppTypo

@Composable
fun CookbookSection(
    navController: NavController
){

    var isExpanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { isExpanded = !isExpanded },
    ) {
        Column() {
            HorizontalDivider(modifier = Modifier.background(AppColors.MainGrey))

            Row(
                modifier = Modifier.fillMaxWidth().padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                ){
                    Icon(
                        imageVector = Icons.Outlined.Star,
                        contentDescription = "Icon",
                    )
                    Text(
                        text = "Favoris",
                        style = AppTypo.SubTitle,
                        color = Color.Black
                    )
                }

                Icon(
                    imageVector = Icons.Default.KeyboardArrowUp,
                    contentDescription = if (isExpanded) "Réduire" else "Étendre",
                    modifier = Modifier.rotate(if (isExpanded) 180f else 0f)
                )

            }

            AnimatedVisibility(
                visible = isExpanded,
                enter = expandVertically(
                    animationSpec = tween(300),
                    expandFrom = Alignment.Top
                ) + fadeIn(animationSpec = tween(300)),
                exit = shrinkVertically(
                    animationSpec = tween(300),
                    shrinkTowards = Alignment.Top
                ) + fadeOut(animationSpec = tween(300))
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = AppSpacing.L, vertical = AppSpacing.M),
                    verticalArrangement = Arrangement.spacedBy(16.dp)

                ) {
                    RecipeCard(navController)
                    RecipeCard(navController)
                }
            }
        }
    }
}