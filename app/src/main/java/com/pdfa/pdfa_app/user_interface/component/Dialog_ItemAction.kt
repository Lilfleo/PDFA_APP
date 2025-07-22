package com.pdfa.pdfa_app.user_interface.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import com.pdfa.pdfa_app.data.model.FoodDetailWithFood
import com.pdfa.pdfa_app.ui.theme.AppTypo


import java.text.SimpleDateFormat
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

@Composable
fun FridgeItemActionDialog(
    item: FoodDetailWithFood,
    onDismiss: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    val unit = if (item.foodDetail.isWeight) "gramme${if (item.foodDetail.quantity > 1) "s" else ""}" else if (item.foodDetail.quantity > 1) "pièces" else "pièce"

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
            Column(
                verticalArrangement = Arrangement.spacedBy(AppSpacing.S)
            ){
                // Titre + image
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = item.food.name,
                        style = AppTypo.SubTitle2
                    )
                    Box(
                        modifier = Modifier
                            .size(AppSpacing.XXXXL)
                            .background(Color.LightGray, shape = AppShapes.CornerS)
                    )
                }

                Spacer(modifier = Modifier.height(AppSpacing.S))

                // Infos
                Column(
                    verticalArrangement = Arrangement.spacedBy(AppSpacing.XS),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    DialogRow(
                        label = "Quantité",
                        value = "${item.foodDetail.quantity} $unit"
                    )
                    DialogRow(
                        label = "Nombre de kcal",
                        value = "${item.food.caloriesPerKg} kcal/kg"
                    )
                    DialogRow(
                        label = "Date d’achat",
                        value = formatDateDisplay(item.foodDetail.buyingTime)
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "À manger dans les",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        DaysLeftLabel(expirationTime = item.foodDetail.expirationTime)
                    }

                }

                Spacer(modifier = Modifier.height(AppSpacing.M))

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .background(
                            color = AppColors.LightGrey,
                            shape = AppShapes.CornerM
                        )
                        .clickable {
                            onEditClick
                        }
                        .padding(vertical = AppSpacing.S)

                ) {
                    Text(
                        text = "Modifier",
                        style = AppTypo.SubTitle,
                        color = Color.Black
                    )
                }
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .background(
                            color = AppColors.MainRed,
                            shape = AppShapes.CornerM
                        )
                        .clickable {
                            onDeleteClick
                        }
                        .padding(vertical = AppSpacing.S)

                ) {
                    Text(
                        text = "Supprimer",
                        style = AppTypo.SubTitle,
                        color = Color.Black
                    )
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
