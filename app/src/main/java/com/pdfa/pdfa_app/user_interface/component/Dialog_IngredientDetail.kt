package com.pdfa.pdfa_app.user_interface.component
//
//import androidx.compose.ui.graphics.Color
//import com.pdfa.pdfa_app.ui.theme.AppColors
//import com.pdfa.pdfa_app.ui.theme.AppShapes
//import com.pdfa.pdfa_app.ui.theme.AppSpacing
//import com.pdfa.pdfa_app.R
//import android.app.DatePickerDialog
//import android.content.Context
//import android.util.Log
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.DateRange
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.window.Dialog
//import androidx.compose.ui.window.DialogProperties
//import androidx.lifecycle.viewmodel.compose.viewModel
//import java.time.LocalDate
//import java.time.format.DateTimeFormatter
//import com.pdfa.pdfa_app.data.model.Food
//import com.pdfa.pdfa_app.data.model.FoodDetail
//import com.pdfa.pdfa_app.ui.viewmodel.FoodDetailViewModel
//import java.time.ZoneId
//import java.util.Date
//
//
//@Composable
//fun EditFoodDialog(
//    item: Food,
//    detail: FoodDetail, // <- ici tu passes l’objet existant à modifier
//    onDismiss: () -> Unit,
//    onEditConfirmed: (FoodDetail) -> Unit,
//    onSnackbarMessage: (String, String) -> Unit
//) {
//    val context = LocalContext.current
//    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
//    val viewModel: FoodDetailViewModel = viewModel()
//
//
//
//
//    var selectedFood by remember { mutableStateOf(item.name) }
//    var selectedExpirationDate by remember {
//        mutableStateOf(
//            detail.expirationTime.toInstant()
//                .atZone(ZoneId.systemDefault())
//                .toLocalDate()
//                .format(formatter)
//        )
//    }
//    var quantityText by remember { mutableStateOf(detail.quantity.toString()) }
//    var priceText by remember { mutableStateOf(detail.price.toString()) }
//
//    Dialog(
//        onDismissRequest = onDismiss,
//        properties = DialogProperties(usePlatformDefaultWidth = false)
//    ) {
//        Box(
//            modifier = Modifier
//                .fillMaxWidth(0.90f)
//                .background(color = Color.White, shape = AppShapes.CornerL)
//                .padding(AppSpacing.L)
//        ) {
//            Column {
//                // 🔸 Titre
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(bottom = AppSpacing.S),
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Icon(
//                        painter = painterResource(id = R.drawable.ic_nav_bag),
//                        contentDescription = null,
//                        modifier = Modifier.size(AppSpacing.XXXL)
//                    )
//                    Spacer(modifier = Modifier.width(AppSpacing.S))
//                    Text(
//                        text = "Modifier l’aliment",
//                        style = MaterialTheme.typography.titleMedium
//                    )
//                }
//
//
//
//
//                Spacer(modifier = Modifier.height(AppSpacing.S))
//
//                // 🔸 Nom de l’aliment
//                OutlinedTextField(
//                    value = selectedFood,
//                    onValueChange = { selectedFood = it },
//                    label = { Text("Nom de l’aliment") },
//                    modifier = Modifier.fillMaxWidth(),
//                    shape = AppShapes.CornerM
//                )
//
//                Spacer(modifier = Modifier.height(AppSpacing.S))
//
//                // 🔸 Quantité
//                OutlinedTextField(
//                    value = quantityText,
//                    onValueChange = { quantityText = it },
//                    label = { Text("Quantité") },
//                    singleLine = true,
//                    modifier = Modifier.fillMaxWidth(),
//                    shape = AppShapes.CornerM
//                )
//
//                Spacer(modifier = Modifier.height(AppSpacing.S))
//
//                // 🔸 Date de péremption (remplace "À consommer dans X jours")
//                DatePickerField(
//                    context = context,
//                    label = "Date de péremption",
//                    dateText = selectedExpirationDate,
//                    onDateSelected = { selectedExpirationDate = it }
//                )
//
//                Spacer(modifier = Modifier.height(AppSpacing.S))
//
//                // 🔸 Prix
//                OutlinedTextField(
//                    value = priceText,
//                    onValueChange = { priceText = it },
//                    label = { Text("Prix d’achat (optionnel)") },
//                    singleLine = true,
//                    modifier = Modifier.fillMaxWidth()
//                )
//
//                Spacer(modifier = Modifier.height(AppSpacing.S))
//
//
//                // ✅ Bouton bien placé
//                Button(
//                    onClick = {
//                        Log.d("EditFoodDialog", "✅ Bouton cliqué")
//                        Log.d("EditFoodDialog", "🔍 Avant modif : $detail") // 👈 ici
//                        val expirationDate: Date? = try {
//                            val localDate = LocalDate.parse(selectedExpirationDate, formatter)
//                            Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
//                        } catch (e: Exception) {
//                            null
//                        }
//
//                        if (expirationDate == null) {
//                            onSnackbarMessage("Date invalide", "error")
//                            return@Button
//                        }
//
//                        val updatedFoodDetail = detail.copy(
////                            quantity = quantityText.toIntOrNull() ?: detail.quantity,
////                            isWeight = true,
////                            price = priceText.toIntOrNull() ?: detail.price,
////                            buyingTime = Date(), // tu peux conserver celui de base si tu veux
////                            expirationTime = expirationDate
//                        )
//
//
//
//                        Log.d("EditFoodDialog", "🔁 Envoi vers callback : $updatedFoodDetail")
//                        onEditConfirmed(updatedFoodDetail) // 👈 tu passes le bon objet ici
//                        Log.d("EditFoodDialog", "✅ Modif envoyée") // 👈 ici "après modif"
//                        onSnackbarMessage("Ingrédient modifié", "success")
//                        onDismiss()
//                    },
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(AppSpacing.XXXLL),
//                    shape = AppShapes.CornerM,
//                    colors = ButtonDefaults.buttonColors(containerColor = AppColors.MainGreen),
//                    contentPadding = PaddingValues(vertical = AppSpacing.S)
//                ) {
//                    Text(
//                        "Enregistrer les modifications",
//                        style = MaterialTheme.typography.bodyLarge,
//                        modifier = Modifier.padding(horizontal = AppSpacing.M)
//                    )
//                }
//            }
//        }
//    }
//}
//
//
//
//@Composable
//fun DatePickerField(
//    context: Context,
//    label: String,
//    dateText: String,
//    onDateSelected: (String) -> Unit
//) {
//    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
//    val now = LocalDate.now()
//
//    Row(
//        modifier = Modifier.fillMaxWidth(),
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        OutlinedTextField(
//            value = dateText,
//            onValueChange = { onDateSelected(it) },
//            label = { Text(label) },
//            modifier = Modifier.weight(1f),
//            singleLine = true
//        )
//        Spacer(modifier = Modifier.width(AppSpacing.S))
//        IconButton(
//            onClick = {
//                val datePicker = DatePickerDialog(
//                    context,
//                    { _, year, month, dayOfMonth ->
//                        val date = LocalDate.of(year, month + 1, dayOfMonth)
//                        onDateSelected(date.format(formatter))
//                    },
//                    now.year,
//                    now.monthValue - 1,
//                    now.dayOfMonth
//                )
//                datePicker.show()
//            }
//        ) {
//            Icon(Icons.Default.DateRange, contentDescription = "Choisir une date")
//        }
//    }
//}
//
