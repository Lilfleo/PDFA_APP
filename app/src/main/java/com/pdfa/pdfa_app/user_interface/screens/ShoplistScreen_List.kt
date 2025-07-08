package com.pdfa.pdfa_app.user_interface.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.pdfa.pdfa_app.ui.theme.AppColors
import com.pdfa.pdfa_app.ui.theme.AppSpacing
import com.pdfa.pdfa_app.user_interface.component.HorizontalDividerPointille

@Composable
fun ShoplistListScreen(){

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppColors.Primary)
    ){
        Column {

            Box(){
                Text(
                    text = "Ici, retrouve ta liste de course réalisé avec les recettes que tu as choisi"
                )
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp).background(AppColors.MainGrey))

            // Catégories
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(AppSpacing.XL)
            ){
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Légumes",
                    )
                }
                HorizontalDividerPointille()
            }
        }
    }
}
