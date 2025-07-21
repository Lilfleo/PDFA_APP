package com.pdfa.pdfa_app.user_interface.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.pdfa.pdfa_app.ui.theme.AppColors
import com.pdfa.pdfa_app.ui.theme.AppSpacing
import com.pdfa.pdfa_app.ui.theme.AppTypo

@Composable
fun ListElement(
    categoryName: String,

){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ){
        HorizontalDivider(modifier = Modifier.background(AppColors.MainGrey))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = AppSpacing.M, vertical = AppSpacing.XS),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = categoryName,
                style = AppTypo.Body,
                color = AppColors.MainGrey
            )
        }
        HorizontalDividerPointille()

        ShoplistElement("Courgette", 300, "g")
        ShoplistElement("Carotte", 300, "g")
        ShoplistElement("Courge Butternut", 300, "g")
        ShoplistElement("Concombre", 300, "g")

    }
}