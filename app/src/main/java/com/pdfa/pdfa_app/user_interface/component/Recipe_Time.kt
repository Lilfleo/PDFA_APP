package com.pdfa.pdfa_app.user_interface.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.pdfa.pdfa_app.ui.theme.AppShapes


@Composable
fun RecipeCard(
    data: String,
    type: String
){

    var extension: String = ""

    when (type) {
        "time" -> extension = "min"
        "calories" -> extension = "kcal"
    }

    Box(
        modifier = Modifier
            .wrapContentSize()
            .shadow(
                elevation = 5.dp,
                shape = AppShapes.CornerM
            )
            .background(
                color = Color.White,
                shape = AppShapes.CornerM
            )
            .clip(AppShapes.CornerM)

    ){
        //Icon
        Text(
            modifier = Modifier
                .padding(7.dp),
            text = "$data $extension"
        )
    }
}