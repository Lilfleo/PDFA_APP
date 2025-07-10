package com.pdfa.pdfa_app.user_interface.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.pdfa.pdfa_app.ui.theme.AppColors
import com.pdfa.pdfa_app.ui.theme.AppShapes
import com.pdfa.pdfa_app.ui.theme.AppSpacing
import com.pdfa.pdfa_app.ui.theme.AppTypo
import com.pdfa.pdfa_app.user_interface.component.AddButton
import com.pdfa.pdfa_app.user_interface.component.ListElement
import com.pdfa.pdfa_app.user_interface.component.ScrollbarPersonnalisee

@Composable
fun ShoplistListScreen(){

    val scrollState = rememberScrollState()


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppColors.Primary)
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(AppSpacing.XS),
            ){
                Text(
                    text = "Ici, retrouve ta liste de course réalisé avec les recettes que tu as choisi",
                    style = AppTypo.SubTitle,
                    color = Color.Black
                )
            }

            // Catégories
            ListElement("Légumes")
            ListElement("Légumes")
            ListElement("Légumes")
            ListElement("Légumes")
            ListElement("Légumes")

            // Modèle de donné pour la shop list:
//            {
//                "catégory": "",
//                "ingredient": Ingredient[]
//            }

        }
        ScrollbarPersonnalisee(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .fillMaxHeight()
                .width(10.dp),
            scrollState = scrollState
        )
        FloatingActionButton(
            onClick = {  },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(AppSpacing.L),
            containerColor = AppColors.MainGreen,
            contentColor = AppColors.MainGrey
        ) {
            Icon(Icons.Filled.Add, contentDescription = "Add")
        }
    }
}
