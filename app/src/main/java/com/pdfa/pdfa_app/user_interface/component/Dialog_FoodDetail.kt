import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.window.Dialog
import com.pdfa.pdfa_app.R
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.window.DialogProperties
import com.pdfa.pdfa_app.ui.theme.AppColors
import com.pdfa.pdfa_app.ui.theme.AppShapes
import com.pdfa.pdfa_app.ui.theme.AppSpacing
import com.pdfa.pdfa_app.user_interface.component.CustomFoodSelector
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@SuppressLint("DefaultLocale")
@Composable
fun FoodDetailDialog(
    foodName: String,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    var selectedDate by remember { mutableStateOf("") }
    var selectedFood by remember { mutableStateOf("") }
    var priceText by remember { mutableStateOf("") }




    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false // Permet d'utiliser toute la largeur
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.90f)
                .background(
                    color = Color.White,
                    shape = AppShapes.CornerL
                )
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
                        text = "Ajoute un aliment à ton frigo",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                Spacer(modifier = Modifier.height(AppSpacing.S))

                CustomFoodSelector(
                    foodList = listOf("Abricot", "Banane", "Cerise","Test_1","Test_2","Test_3"), // exemple
                    selectedFood = selectedFood,
                    onFoodSelected = { selectedFood = it }
                )

                Spacer(modifier = Modifier.height(AppSpacing.S))

                // Checkbox conditionnelle

                AnimatedVisibility(visible = selectedFood == "Autre") {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        var isChecked by remember { mutableStateOf(false) }
                        Checkbox(
                            checked = isChecked,
                            onCheckedChange = { isChecked = it }
                        )
                        Text("Souhaites-tu l’ajouter au catalogue ?")
                    }
                }

                Spacer(modifier = Modifier.height(AppSpacing.S))



                // Etat quantité
                var quantityText by remember { mutableStateOf("") }

            // Etat unité
                var selectedUnit by remember { mutableStateOf("Gramme") }
                val unitOptions = listOf("Gramme", "Pièce")

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Champ quantité à gauche
                    OutlinedTextField(
                        value = quantityText,
                        onValueChange = { quantityText = it },
                        label = { Text("Quantité") },
                        singleLine = true,
                        modifier = Modifier.weight(1f),
                        shape = AppShapes.CornerM,
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = AppColors.MainGreen,
                            unfocusedIndicatorColor = Color.LightGray,
                            focusedLabelColor = AppColors.MainGreen,
                            unfocusedLabelColor = Color.Gray
                        )
                    )

                    Spacer(modifier = Modifier.width(AppSpacing.S))

                    // Les deux boutons
                    Row(
                        modifier = Modifier
                            .height(AppSpacing.XXXLL)
                            .background(
                                color = Color.White,
                                shape = AppShapes.CornerM
                            )
                    ) {
                        unitOptions.forEach { unit ->
                            val isSelected = selectedUnit == unit
                            Box(
                                modifier = Modifier
                                    .width(AppSpacing.XXXXL)
                                    .fillMaxHeight()
                                    .background(
                                        color = if (isSelected) AppColors.MainGreen else Color.Transparent,
                                        shape = AppShapes.CornerM
                                    )
                                    .clickable { selectedUnit = unit },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = if (unit == "Gramme") "g" else "Pce",
                                    color = if (isSelected) Color.White else Color.Black
                                )
                            }
                        }
                    }
                }



                Spacer(modifier = Modifier.height(AppSpacing.S))

                // Etat de la date
                var selectedDateText by remember { mutableStateOf("") }

                val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = selectedDateText,
                        onValueChange = { selectedDateText = it },
                        label = { Text("Date d'achat") },
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.width(AppSpacing.S))
                    IconButton(
                        onClick = {
                            val now = LocalDate.now()
                            val datePicker = android.app.DatePickerDialog(
                                context,
                                { _, year, month, dayOfMonth ->
                                    val date = LocalDate.of(year, month + 1, dayOfMonth)
                                    selectedDateText = date.format(formatter)
                                },
                                now.year,
                                now.monthValue - 1,
                                now.dayOfMonth
                            )
                            datePicker.show()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Choisir une date"
                        )
                    }
                }

                Spacer(modifier = Modifier.height(AppSpacing.S))

                // Etat de la date

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = selectedDateText,
                        onValueChange = { selectedDateText = it },
                        label = { Text("DLC") },
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.width(AppSpacing.S))
                    IconButton(
                        onClick = {
                            val now = LocalDate.now()
                            val datePicker = android.app.DatePickerDialog(
                                context,
                                { _, year, month, dayOfMonth ->
                                    val date = LocalDate.of(year, month + 1, dayOfMonth)
                                    selectedDateText = date.format(formatter)
                                },
                                now.year,
                                now.monthValue - 1,
                                now.dayOfMonth
                            )
                            datePicker.show()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Choisir une date"
                        )
                    }
                }

                Spacer(modifier = Modifier.height(AppSpacing.S))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {


                    OutlinedTextField(
                        value = priceText,
                        onValueChange = { priceText = it },
                        placeholder = { Text("Prix d'achat (optionnel...)") },
                        singleLine = true,
                        modifier = Modifier
                            .weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(AppSpacing.S))

                Button(
                    onClick = onDismiss, //a changer ici pour sauvegarder et pas quitter
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AppColors.MainGreen
                    )
                ) {
                    Text("Ajouter")
                }
            }

        }
    }
}
