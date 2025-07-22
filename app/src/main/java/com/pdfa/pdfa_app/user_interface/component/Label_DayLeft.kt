package com.pdfa.pdfa_app.user_interface.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.util.*

@Composable
fun DaysLeftLabel(
    expirationTime: Date?,
    modifier: Modifier = Modifier
) {
    if (expirationTime == null) return

    val today = LocalDate.now(ZoneId.systemDefault())
    val expirationDate = expirationTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
    val daysLeft = ChronoUnit.DAYS.between(today, expirationDate).toInt()

    val label = when {
        daysLeft < 0 -> "Expiré"
        daysLeft == 0 -> "Aujourd’hui"
        daysLeft == 1 -> "1 jour"
        else -> "$daysLeft jours"
    }

    val color = when {
        daysLeft < 0 -> Color.Red
        daysLeft == 0 -> Color(0xFFFF4D00)
        else -> Color(0xFF4CAF50) // Vert doux
    }

    Text(
        text = label,
        color = color,
        style = MaterialTheme.typography.bodyMedium,
        modifier = modifier
    )
}
