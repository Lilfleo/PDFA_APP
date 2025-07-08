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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.pdfa.pdfa_app.ui.theme.AppColors
import com.pdfa.pdfa_app.ui.theme.AppShapes

@Composable
fun TagsBox(
    name: String,
    type: String,
    isColored: Boolean
    ){

    val colorTag: Color =
        when (type) {
            "Easy" -> AppColors.Easy
            else -> {
                AppColors.Primary
            }
        }

    if (isColored) {
        Box(
            modifier = Modifier
                .wrapContentWidth()
                .height(25.dp)
                .padding(end = 5.dp)
                .clip(AppShapes.CornerM)
                .background(colorTag),
            contentAlignment = Alignment.Center
        ){
            Text(
                modifier = Modifier
                    .padding(horizontal = 6.dp),
                text = "#$name"
            )

        }
    } else {
        Box(
            modifier = Modifier
                .wrapContentWidth()
                .height(25.dp)
                .padding(end = 5.dp)
                .clip(AppShapes.CornerM)
                .background(color = Color.White)
                .border(
                    width = 1.dp,
                    color = Color.Black,
                    shape = AppShapes.CornerM
                ),
            contentAlignment = Alignment.Center
        ){
            Text(
                modifier = Modifier
                    .padding(horizontal = 6.dp),
                text = "#$name"
            )

        }
    }

}