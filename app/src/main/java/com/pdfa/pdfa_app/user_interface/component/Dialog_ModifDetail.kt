import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.util.Log
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.pdfa.pdfa_app.data.model.Food
import com.pdfa.pdfa_app.data.model.FoodDetail
import com.pdfa.pdfa_app.data.model.FoodDetailWithFood
import com.pdfa.pdfa_app.ui.theme.AppColors
import com.pdfa.pdfa_app.ui.theme.AppShapes
import com.pdfa.pdfa_app.ui.theme.AppSpacing
import com.pdfa.pdfa_app.ui.theme.AppTypo
import com.pdfa.pdfa_app.ui.viewmodel.FoodDetailViewModel
import com.pdfa.pdfa_app.ui.viewmodel.FoodViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("DefaultLocale")
@Composable
fun FoodModifDialog(
    foodToEdit: FoodDetailWithFood? = null,
    onDismiss: () -> Unit,
    onSnackbarMessage: (String, String) -> Unit,
    foodList: List<Food>,
    existingDetail: FoodDetail? = null
) {
    val context = LocalContext.current
    var selectedFood by remember { mutableStateOf("") }

    val foodViewModel: FoodViewModel = hiltViewModel()
    val detailViewModel: FoodDetailViewModel = hiltViewModel()
    var selectedFoodName by remember { mutableStateOf("") }
    var selectedFoodId by remember { mutableStateOf<Int?>(null) }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    var quantityText by remember { mutableStateOf("") }

    var priceText by remember { mutableStateOf("") }
    var buyingDateText by remember {
        mutableStateOf(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
    }
    var expirationDateText by remember { mutableStateOf("") }
    val selectedFoodObject = selectedFoodId?.let { id ->
        foodList.find { it.id == id }
    }

//    val initialUnit = if (foodToEdit?.foodDetail?.isWeight == true) "Gramme" else "Pièce"
    var selectedUnit by remember { mutableStateOf("") }
//    val unitOptions = listOf("Gramme", "Pièce")

    val foodList by foodViewModel.foodList.collectAsState()
    var expanded by remember { mutableStateOf(false) }
    var selectedDateText by remember { mutableStateOf("") }

    val unitOptions = if (selectedFoodObject?.unit?.contains("cl") == true &&
        selectedFoodObject.unit.size == 1) {
        // Si le food n'a que "cl" comme unité, afficher seulement "cl"
        listOf("cl")
    } else {
        // Sinon, garder les options par défaut
        listOf("g", "pcs")
    }

    LaunchedEffect(Unit) {
        snapshotFlow { quantityText }
            .collect { Log.d("DEBUG", "TextField shows quantity = $it") }
    }

    LaunchedEffect(foodToEdit) {
        foodToEdit?.let { detail ->
            val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

            quantityText = foodToEdit.foodDetail.quantity.toString()
            selectedFoodId = detail.foodDetail.foodId
            selectedFoodName = detail.food.name
            priceText = detail.foodDetail.price.toString()

            // Mettre à jour selectedUnit selon l'unité réelle stockée
            selectedUnit = when (detail.foodDetail.unit) {
                "cl" -> "cl"
                "pcs" -> "pcs"
                else -> "g" // par défaut pour "g" ou autres
            }

            buyingDateText = formatter.format(detail.foodDetail.buyingTime)
            expirationDateText = formatter.format(detail.foodDetail.expirationTime)
        }
    }

    LaunchedEffect(selectedFoodId) {
        selectedFoodObject?.let { food ->
            if (food.unit.contains("cl") && food.unit.size == 1) {
                selectedUnit = "cl"
            } else if (selectedUnit == "cl" && !food.unit.contains("cl")) {
                // Si on passe d'un food "cl" à un food sans "cl", revenir à l'unité par défaut
                selectedUnit = if (foodToEdit?.foodDetail?.unit == "g") "g" else "pcs"
            }
        }
    }

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
                        text = "Modification : $selectedFoodName",
                        style = AppTypo.SubTitle
                    )
                }
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

                // Etat unité
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Champ quantité à gauche
                    key(foodToEdit?.foodDetail?.id ?: 0) {
                        OutlinedTextField(
                            value = quantityText.also {
                                Log.d(
                                    "UI",
                                    "TextField shows quantity = $it"
                                )
                            },
                            onValueChange = { quantityText = it },
                            label = { Text("Quantité") },
                            singleLine = true,
                            modifier = Modifier.weight(1f),
                            shape = AppShapes.CornerM,

                        )
                    }

                    Spacer(modifier = Modifier.width(AppSpacing.S))

                    // Les deux boutons
                    if (unitOptions.size == 1 && unitOptions[0] == "cl") {
                        // Bouton unique pour "cl"
                        Box(
                            modifier = Modifier
                                .width(AppSpacing.XXXXL * 2) // Largeur double pour le bouton unique
                                .height(AppSpacing.XXXLL)
                                .background(
                                    color = AppColors.MainGreen, // Toujours sélectionné
                                    shape = AppShapes.CornerM
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "cl",
                                color = Color.White
                            )
                        }
                    } else {
                        // Deux boutons pour g/pcs
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
                                        text = if (unit == "g") "g" else "Pce",
                                        color = if (isSelected) Color.White else Color.Black
                                    )
                                }
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
                        value = buyingDateText,
                        onValueChange = { selectedDateText = it },
                        label = { Text("Date d'achat") },
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.width(AppSpacing.S))
                    IconButton(
                        onClick = {
                            val now = LocalDate.now()
                            val datePicker = DatePickerDialog(
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
                        value = expirationDateText,
                        onValueChange = { expirationDateText = it },
                        label = { Text("DLC") },
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.width(AppSpacing.S))
                    IconButton(
                        onClick = {
                            val now = LocalDate.now()
                            val datePicker = DatePickerDialog(
                                context,
                                { _, year, month, dayOfMonth ->
                                    val date = LocalDate.of(year, month + 1, dayOfMonth)
                                    expirationDateText = date.format(formatter)
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

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .background(
                            color = AppColors.MainGreen,
                            shape = AppShapes.CornerM)
                        .clickable {
                                if (selectedFoodId != null &&
                                    quantityText.isNotBlank() &&
                                    expirationDateText.isNotBlank() &&
                                    buyingDateText.isNotBlank()
                                ) {
                                    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

                                    val newDetail = FoodDetail(
                                        id = foodToEdit?.foodDetail?.id ?: 0,
                                        foodId = selectedFoodId!!,
                                        quantity = quantityText.toIntOrNull() ?: 0,
                                        price = priceText.toFloatOrNull(),
                                        unit = selectedUnit,
                                        buyingTime = sdf.parse(buyingDateText) ?: Date(),
                                        expirationTime = sdf.parse(expirationDateText) ?: Date()
                                    )

                                    detailViewModel.upsertFoodDetail(newDetail)
                                    onDismiss()
                                    onSnackbarMessage("Détail mis à jour avec succès !", "success")

                                } else {
                                    coroutineScope.launch {
                                        snackbarHostState.showSnackbar("Veuillez compléter tous les champs requis.")
                                    }
                                }
                        }
                        .padding(vertical = AppSpacing.S)

                ) {
                    Text(
                        text = "Modifier",
                        style = AppTypo.SubTitle,
                        color = Color.White
                    )
                }

            }
            // ✅ Snackbar intégrée à la Dialog
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = AppSpacing.S)
            )
        }
    }
}
