package com.pdfa.pdfa_app.user_interface.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.toInt
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.pdfa.pdfa_app.data.model.Allergy
import com.pdfa.pdfa_app.data.model.Food
import com.pdfa.pdfa_app.ui.theme.AppColors
import com.pdfa.pdfa_app.ui.theme.AppShapes
import com.pdfa.pdfa_app.ui.theme.AppSpacing
import com.pdfa.pdfa_app.ui.theme.AppTypo
import com.pdfa.pdfa_app.ui.viewmodel.AllergyViewModel
import com.pdfa.pdfa_app.ui.viewmodel.FoodViewModel

@Composable
fun AllergiesDialog(
    onDismiss: () -> Unit,
    foodViewModel: FoodViewModel = hiltViewModel(),
    allergyViewModel: AllergyViewModel = hiltViewModel()
){
    val foodList by foodViewModel.foodList.collectAsState()
    var selectedFood by remember { mutableStateOf<Food?>(null) } // ✅ type Food
    var selectedFoodName by remember { mutableStateOf("") }

    val allergyList by allergyViewModel.allAllergy.collectAsState()


    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.90f)
                .wrapContentHeight()
                .background(
                    color = Color.White,
                    shape = AppShapes.CornerXL
                ),
        ){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(AppSpacing.L),
                verticalArrangement = Arrangement.spacedBy(AppSpacing.L)
            ) {

                Text(
                    text = "Ajouter une Allergie",
                    style = AppTypo.SubTitle,
                    color = Color.Black
                )


//                CustomFoodSelector(
//                    foodList = foodList.map { it.name },
//                    selectedFood = selectedFood?.name?:"",
//                    onFoodSelected = { foodName ->
//                        selectedFood = foodList.find { it.name == foodName }
//                    }
//                )


                CustomFoodDropdown(
                    selectedValue = selectedFoodName,
                    placeholder = "Type d'aliment",
                    onItemSelected = { item ->
                        selectedFood = item
//                        selectedFoodName = item.name
                        allergyViewModel.insertAllergy(Allergy(foodId=item.id))
                    },
                    foods = foodList
                )

                FlowRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = AppSpacing.S),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(AppSpacing.XXS)
                ) {
                    allergyList.forEach { allergyWithFood ->
                        TagsBoxProfil(
                            name = allergyWithFood.food.name,
                            type = "Allergy",
                            onRemove = {
                                allergyViewModel.deleteAllergy(allergyWithFood.allergy)
                            }
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(AppSpacing.XXXXL)
                        .clickable {
                            onDismiss()
                        }
                        .background(
                            color =  AppColors.MainGreen,
                            shape = AppShapes.CornerL
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Ajouter",
                        style = AppTypo.SubTitle,
                        color = if (selectedFood == null) Color.Black else Color.White,
                    )
                }

            }
        }
    }
}