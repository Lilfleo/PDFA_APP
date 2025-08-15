package com.pdfa.pdfa_app.utilz

import androidx.compose.ui.graphics.Color
import com.pdfa.pdfa_app.ui.theme.AppColors

fun String.toComposeColor(): Color {
    return try {
        Color(this.removePrefix("0x").toLong(16) or 0x00000000)
    } catch (e: Exception) {
        AppColors.MainGreen // couleur par défaut en cas d'erreur
    }
}
