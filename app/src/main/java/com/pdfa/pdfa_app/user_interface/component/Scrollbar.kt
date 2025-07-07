package com.pdfa.pdfa_app.user_interface.component

import android.annotation.SuppressLint
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.pdfa.pdfa_app.ui.theme.AppColors

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun ScrollbarPersonnalisee(
    modifier: Modifier = Modifier,
    scrollState: ScrollState
) {
    val maxScroll = scrollState.maxValue.toFloat()
    val currentScroll = scrollState.value.toFloat()

    val scrollPercentage = if (maxScroll > 0) currentScroll / maxScroll else 0f
    val thumbSizeRatio = if (maxScroll > 0) 0.3f else 1f

    BoxWithConstraints(
        modifier = modifier
            .padding(2.dp)
    ) {
        val trackHeight = maxHeight - 4.dp
        val thumbHeight = trackHeight * thumbSizeRatio
        val maxThumbOffset = trackHeight - thumbHeight

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(thumbHeight)
                .offset(y = maxThumbOffset * scrollPercentage)
                .background(
                    AppColors.Scrollbar,
                    RoundedCornerShape(4.dp)
                )
        )
    }
}