import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.pdfa.pdfa_app.R
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
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
    val interactionSource = remember { MutableInteractionSource() }



    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(16.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_nav_bag),
                        contentDescription = null,
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Ajoute un aliment à ton frigo",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))

                CustomFoodSelector(
                    foodList = listOf("Abricot", "Banane", "Cerise","Test_1","Test_2","Test_3"), // exemple
                    selectedFood = selectedFood,
                    onFoodSelected = { selectedFood = it }
                )

                Spacer(modifier = Modifier.height(8.dp))

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

                Spacer(modifier = Modifier.height(8.dp))



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
                        shape = RoundedCornerShape(8.dp),
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color(0xFF91AB75),
                            unfocusedIndicatorColor = Color.LightGray,
                            focusedLabelColor = Color(0xFF91AB75),
                            unfocusedLabelColor = Color.Gray
                        )
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    // Les deux boutons
                    Row(
                        modifier = Modifier
                            .height(40.dp)
                            .background(
                                color = Color(0xFFE0E0E0),
                                shape = RoundedCornerShape(8.dp)
                            )
                    ) {
                        unitOptions.forEach { unit ->
                            val isSelected = selectedUnit == unit
                            Box(
                                modifier = Modifier
                                    .width(48.dp)
                                    .fillMaxHeight()
                                    .background(
                                        color = if (isSelected) Color(0xFF91AB75) else Color.Transparent,
                                        shape = RoundedCornerShape(8.dp)
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



                Spacer(modifier = Modifier.height(8.dp))

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
                    Spacer(modifier = Modifier.width(8.dp))
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

                Spacer(modifier = Modifier.height(8.dp))

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
                    Spacer(modifier = Modifier.width(8.dp))
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

                Spacer(modifier = Modifier.height(8.dp))

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

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = onDismiss, //a changer ici pour sauvegarder et pas quitter
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF91AB75)
                    )
                ) {
                    Text("Ajouter")
                }
            }

        }
    }
}
