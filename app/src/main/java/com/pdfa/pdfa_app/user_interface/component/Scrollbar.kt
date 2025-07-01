package com.pdfa.pdfa_app.user_interface.component

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pdfa.pdfa_app.ui.theme.AppColors

@Composable
fun ScrollbarPersonnalisee(
    modifier: Modifier = Modifier,
    scrollState: ScrollState
) {
    val maxScroll = scrollState.maxValue.toFloat()
    val currentScroll = scrollState.value.toFloat()

    // N'affiche la scrollbar que si on peut scroll
    if (maxScroll <= 0) return

    val scrollPercentage = currentScroll / maxScroll

    BoxWithConstraints(
        modifier = modifier.padding(2.dp)
    ) {
        val trackHeight = maxHeight - 4.dp

        val viewportHeight = trackHeight // Hauteur de la zone visible
        val contentHeight = trackHeight + maxScroll.dp // Hauteur totale du contenu
        val thumbSizeRatio = (viewportHeight / contentHeight).coerceIn(0.1f, 1f) // Min 10% de la track

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