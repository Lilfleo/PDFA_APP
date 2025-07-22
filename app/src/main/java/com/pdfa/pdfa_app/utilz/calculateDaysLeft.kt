package com.pdfa.pdfa_app.utilz

import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.util.*



fun calculateDaysLeft(buyingTime: Date?, expirationTime: Date?): String {
    if (buyingTime == null || expirationTime == null) return ""

    val today = LocalDate.now(ZoneId.systemDefault())
    val expirationDate = expirationTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()

    val daysLeft = ChronoUnit.DAYS.between(today, expirationDate).toInt()

    val label = if (daysLeft == 1) "jour" else "jours"
    return "$daysLeft $label"
}

fun calculateDaysLeftText(expirationTime: Date?): String {
    if (expirationTime == null) return ""

    val today = LocalDate.now(ZoneId.systemDefault())
    val expirationDate = expirationTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
    val daysLeft = ChronoUnit.DAYS.between(today, expirationDate).toInt()

    return when {
        daysLeft < 0 -> "Expiré"
        daysLeft == 0 -> "Aujourd’hui"
        daysLeft == 1 -> "1 jour"
        else -> "$daysLeft jours"
    }
}

