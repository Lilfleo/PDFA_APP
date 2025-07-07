package com.pdfa.pdfa_app.user_interface.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.pdfa.pdfa_app.ui.theme.AppShapes
import com.pdfa.pdfa_app.ui.theme.AppSpacing
import com.pdfa.pdfa_app.user_interface.rooting.Screen

@Composable
fun RecipeCard(
    navController: NavController
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
            ) {
                Text(
                    text = "Super long nom pour voir ce qu'il se passe",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .weight(1f, fill = false),
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = "~35min"
                )
            }

            //Régime
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(top = 2.dp, end = 2.dp)
                    .horizontalScroll(rememberScrollState()),

                ) {
                TagsBox("Facile", "Easy")
                TagsBox("Facile", "Easy")
                TagsBox("Facile", "Easy")
                TagsBox("Facile", "Easy")
                TagsBox("Facile", "Easy")
                TagsBox("Facile", "Easy")
                TagsBox("Facile", "Easy")

            }
            //TAG
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(top = 2.dp, end = 2.dp)
                    .horizontalScroll(rememberScrollState()),
            ) {
                TagsBox("Facile", "Easy")
                TagsBox("Facile", "Easy")
                TagsBox("Facile", "Easy")
                TagsBox("Facile", "Easy")
                TagsBox("Facile", "Easy")
                TagsBox("Facile", "Easy")
                TagsBox("Facile", "Easy")
            }
        }
    }
}