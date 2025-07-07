package com.pdfa.pdfa_app.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp

object AppShapes {
    // Formes basiques
    val None = RoundedCornerShape(0.dp)
    val CornerXS = RoundedCornerShape(4.dp)
    val CornerS = RoundedCornerShape(6.dp)
    val CornerM = RoundedCornerShape(8.dp)
    val CornerL = RoundedCornerShape(12.dp)
    val CornerXL = RoundedCornerShape(16.dp)
    val CornerXXL = RoundedCornerShape(24.dp)

    // Formes spécifiques
    val Button = RoundedCornerShape(8.dp)
    val Card = RoundedCornerShape(12.dp)
    val Dialog = RoundedCornerShape(16.dp)
    val BottomSheet = RoundedCornerShape(
        topStart = 16.dp,
        topEnd = 16.dp,
        bottomStart = 0.dp,
        bottomEnd = 0.dp
    )
    val TopBar = RoundedCornerShape(
        topStart = 0.dp,
        topEnd = 0.dp,
        bottomStart = 16.dp,
        bottomEnd = 16.dp
    )

    // Formes asymétriques
    val CardTopRounded = RoundedCornerShape(
        topStart = 12.dp,
        topEnd = 12.dp,
        bottomStart = 0.dp,
        bottomEnd = 0.dp
    )
    val CardBottomRounded = RoundedCornerShape(
        topStart = 0.dp,
        topEnd = 0.dp,
        bottomStart = 12.dp,
        bottomEnd = 12.dp
    )
}