package com.pdfa.pdfa_app.user_interface.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.pdfa.pdfa_app.ui.theme.AppColors
import com.pdfa.pdfa_app.ui.theme.AppShapes
import com.pdfa.pdfa_app.ui.theme.AppSpacing
import com.pdfa.pdfa_app.ui.theme.Typography
import com.pdfa.pdfa_app.user_interface.component.ScrollbarPersonnalisee
import com.pdfa.pdfa_app.user_interface.component.TagsBox

@Composable
fun RecipeMakerScreen(){

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
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(AppSpacing.CardHeight)
                    .clip(AppShapes.CornerL)
                    .background(Color.White)
                    .border(1.dp, Color.Black)
                    .padding(AppSpacing.M)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                ) {
                    //Titre
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
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
                    }
                    //Régime
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(top = 2.dp, end = 2.dp)
                            .horizontalScroll(rememberScrollState()),

                    ){
                        TagsBox("Facile", "Easy")
                        TagsBox("Facile", "Easy")
                        TagsBox("Facile", "Easy")
                        TagsBox("Facile", "Easy")
                        TagsBox("Facile", "Easy")
                        TagsBox("Facile", "Easy")
                        TagsBox("Facile", "Easy")

                    }
                    //TAG
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .background(Color.Green)
                    ){

                    }
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
}