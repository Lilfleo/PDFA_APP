package com.pdfa.pdfa_app.user_interface.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import com.pdfa.pdfa_app.ui.theme.AppColors
import com.pdfa.pdfa_app.ui.theme.AppSpacing
import com.pdfa.pdfa_app.ui.theme.AppTypo

@Composable
fun ShoplistElement(
    name: String,
    quantity: Int,
    unit: String,
    isSelected: Boolean = false,
    onSelectionChanged: (Boolean) -> Unit = {},
){

    var isChecked by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppSpacing.M),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.CenterStart
            ){
                Text(
                    text = name,
                    style = AppTypo.Body,
                    color = Color.Black,
                    overflow = TextOverflow.Ellipsis,
                    )
            }
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ){
                Row(){
                    Text(
                        text = quantity.toString(),
                        style = AppTypo.Body,
                        color = Color.Black,
                        overflow = TextOverflow.Ellipsis,
                        )
                    Text(
                        text = unit,
                        style = AppTypo.LightQte,
                        color = Color.Black,

                    )
                }
            }

            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.CenterEnd
            ){
                Checkbox(
                    checked = isSelected,
                    onCheckedChange = onSelectionChanged
                )
            }
        }
        HorizontalDivider(modifier = Modifier.background(AppColors.MainGrey))
    }
}