package com.pdfa.pdfa_app.user_interface.component

import androidx.compose.ui.graphics.Color
import com.pdfa.pdfa_app.ui.theme.AppColors
import com.pdfa.pdfa_app.ui.theme.AppShapes
import com.pdfa.pdfa_app.ui.theme.AppSpacing
import com.pdfa.pdfa_app.R
import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import com.pdfa.pdfa_app.data.FridgeItem
import com.pdfa.pdfa_app.data.model.Food


data class FridgeItem(
    val name: String,
    val quantity: Int,
    val unit: String = "Gramme", // "Gramme" ou "Pièce"
    val calories: Int = 0,
    val purchaseDate: String = "",
    val expiryInDays: Int = 0,
    val price: Float? = null
)

@Composable
fun EditFoodDialog(
    item: Food,
    onDismiss: () -> Unit,
    onEditConfirmed: (FridgeItem) -> Unit,
    onSnackbarMessage: (String, String) -> Unit
) {
    val context = LocalContext.current
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    var selectedFood by remember { mutableStateOf(item.name) }

    var selectedExpiryDays by remember { mutableStateOf(item.expirationTime.toString()) }
    var quantityText by remember { mutableStateOf(item.caloriesPerKg.toString()) }
    var priceText by remember { mutableStateOf(item.caloriesPerUnit.toString()) }

    val unitOptions = listOf("Gramme", "Pièce")

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
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = AppSpacing.S),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_nav_bag),
                        contentDescription = null,
                        modifier = Modifier.size(AppSpacing.XXXL)
                    )
                    Spacer(modifier = Modifier.width(AppSpacing.S))
                    Text(
                        text = "Modifier l’aliment",
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                Spacer(modifier = Modifier.height(AppSpacing.S))

                // Nom de l’aliment
                OutlinedTextField(
                    value = selectedFood,
                    onValueChange = { selectedFood = it },
                    label = { Text("Nom de l’aliment") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = AppShapes.CornerM
                )

                Spacer(modifier = Modifier.height(AppSpacing.S))

                // Quantité + unité
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = quantityText,
                        onValueChange = { quantityText = it },
                        label = { Text("Quantité") },
                        singleLine = true,
                        modifier = Modifier.weight(1f),
                        shape = AppShapes.CornerM
                    )

                    Spacer(modifier = Modifier.width(AppSpacing.S))

                    Row(
                        modifier = Modifier
                            .height(AppSpacing.XXXLL)
                            .background(color = Color.White, shape = AppShapes.CornerM)
                    ) {
//                        unitOptions.forEach { unit ->
//                            val isSelected = selectedUnit == unit
//                            Box(
//                                modifier = Modifier
//                                    .width(AppSpacing.XXXXL)
//                                    .fillMaxHeight()
//                                    .background(
//                                        color = if (isSelected) AppColors.MainGreen else Color.Transparent,
//                                        shape = AppShapes.CornerM
//                                    )
//                                    .clickable { selectedUnit = unit },
//                                contentAlignment = Alignment.Center
//                            ) {
//                                Text(
//                                    text = if (unit == "Gramme") "g" else "Pce",
//                                    color = if (isSelected) Color.White else Color.Black
//                                )
//                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(AppSpacing.S))

                // Date d’achat
//                DatePickerField(
//                    context = context,
//                    label = "Date d'achat",
//                    dateText = selectedPurchaseDate,
//                    onDateSelected = { selectedPurchaseDate = it }
//                )

                Spacer(modifier = Modifier.height(AppSpacing.S))

                // DLC (nombre de jours avant péremption)
                OutlinedTextField(
                    value = selectedExpiryDays,
                    onValueChange = { selectedExpiryDays = it },
                    label = { Text("À consommer dans (jours)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(AppSpacing.S))

                // Prix
                OutlinedTextField(
                    value = priceText,
                    onValueChange = { priceText = it },
                    label = { Text("Prix d’achat (optionnel)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(AppSpacing.S))

                Button(
                    onClick = {
                        val updatedItem = item.copy(
                            name = selectedFood,
                            //quantity = quantityText.toIntOrNull() ?: item.quantity,
                            //unit = selectedUnit,
                            //purchaseDate = selectedPurchaseDate,
                            //expiryInDays = selectedExpiryDays.toIntOrNull() ?: item.expirationTime,
                            //price = priceText.toFloatOrNull()
                        )
                        //onEditConfirmed(updatedItem)
                        onSnackbarMessage("Ingrédient modifié", "success")
                        onDismiss()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = AppColors.MainGreen)
                ) {
                    Text("Enregistrer les modifications")
                }
            }
        }
    }

@Composable
fun DatePickerField(
    context: Context,
    label: String,
    dateText: String,
    onDateSelected: (String) -> Unit
) {
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val now = LocalDate.now()

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = dateText,
            onValueChange = { onDateSelected(it) },
            label = { Text(label) },
            modifier = Modifier.weight(1f),
            singleLine = true
        )
        Spacer(modifier = Modifier.width(AppSpacing.S))
        IconButton(
            onClick = {
                val datePicker = DatePickerDialog(
                    context,
                    { _, year, month, dayOfMonth ->
                        val date = LocalDate.of(year, month + 1, dayOfMonth)
                        onDateSelected(date.format(formatter))
                    },
                    now.year,
                    now.monthValue - 1,
                    now.dayOfMonth
                )
                datePicker.show()
            }
        ) {
            Icon(Icons.Default.DateRange, contentDescription = "Choisir une date")
        }
    }
}
