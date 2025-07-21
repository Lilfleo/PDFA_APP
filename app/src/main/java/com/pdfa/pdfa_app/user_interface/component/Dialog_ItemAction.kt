package com.pdfa.pdfa_app.user_interface.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.pdfa.pdfa_app.ui.theme.AppColors
import com.pdfa.pdfa_app.ui.theme.AppShapes
import com.pdfa.pdfa_app.ui.theme.AppSpacing
import com.pdfa.pdfa_app.data.model.Food


import java.text.SimpleDateFormat
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

@Composable
fun FridgeItemActionDialog(
    item: Food,
    onDismiss: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.90f)
                .background(color = Color.White, shape = AppShapes.CornerL)
                .padding(AppSpacing.L)
        ) {
            Column {
                // Titre + image en haut à droite
                Box(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = item.name,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.align(Alignment.TopStart)
                    )
                    Box(
                        modifier = Modifier
                            .size(AppSpacing.XXXXL)
                            .align(Alignment.TopEnd)
                            .background(Color.LightGray, shape = AppShapes.CornerS)
                    ) {
                        // Placeholder image
                    }
                }

                Spacer(modifier = Modifier.height(AppSpacing.XL))

                // Données en 2 colonnes
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(AppSpacing.XS)
                ) {
                    DialogRow(label = "Nombre de kcal", value = "${item.caloriesPerKg} kcal/kg")
                    //DialogRow(label = "Date d’achat", value = formatDateDisplay(item.expirationTime))
                    DialogRow(label = "À manger dans les", value = formatDateDisplay(item.expirationTime))
                }

                Spacer(modifier = Modifier.height(AppSpacing.M))

                // Bouton Modifier (gris clair)
                Button(
                    onClick = onEditClick,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = AppColors.LightGrey)
                ) {
                    Text("Modifier", color = Color.Black)
                }

                Spacer(modifier = Modifier.height(AppSpacing.M))

                // Bouton Supprimer (rouge)
                Button(
                    onClick = onDeleteClick,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text("Supprimer", color = Color.White)
                }
            }
        }
    }
}

// 🔧 Fonction de formatage
fun formatDateDisplay(date: Any?): String {
    return try {
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        when (date) {
            is Date -> {
                date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(formatter)
            }
            is String -> {
                val parsed = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH).parse(date)
                parsed?.toInstant()?.atZone(ZoneId.systemDefault())?.toLocalDate()?.format(formatter) ?: ""
            }
            else -> ""
        }
    } catch (e: Exception) {
        ""
    }
}


@Composable
private fun DialogRow(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
