package com.pdfa.pdfa_app.user_interface.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.pdfa.pdfa_app.data.model.Diet
import com.pdfa.pdfa_app.data.model.Food
import com.pdfa.pdfa_app.ui.theme.AppColors
import com.pdfa.pdfa_app.ui.theme.AppShapes
import com.pdfa.pdfa_app.ui.theme.AppSpacing
import com.pdfa.pdfa_app.ui.theme.AppTypo
import com.pdfa.pdfa_app.ui.viewmodel.DietPreferenceViewModel
import com.pdfa.pdfa_app.ui.viewmodel.DietViewModel

@Composable
fun DietDialog(
    onDismiss: () -> Unit,
//    addDiet: (String) -> Unit,
    dietViewModel: DietViewModel = hiltViewModel(),
    dietPreferenceViewModel: DietPreferenceViewModel = hiltViewModel()
){

    val dietNames by dietViewModel.dietNames.collectAsState()
    val allDiet by dietViewModel.diets.collectAsState()
    val dietPreference by dietPreferenceViewModel.dietPreferenceList.collectAsState()

    var selectedDiet by remember { mutableStateOf("") }

    val preferedDiets = dietPreference.map { it.diet }

    fun findDietId(dietName: String): Int? {
        println(dietName)
        val diet = allDiet.find { diet ->
            diet.name == dietName
        }
        return diet?.id
    }


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
                    text = "Ajouter un régime",
                    style = AppTypo.SubTitle,
                    color = Color.Black
                )


                CustomDropdown(
                    selectedValue = selectedDiet,
                    placeholder = "Les régimes alimentaires",
                    onItemSelected = { item ->
                        val dietId = findDietId(item)
                        if (dietId != null) {
                            dietPreferenceViewModel.addDietPreference(dietId)
                        }
                    },
                    elements = dietNames
                )
                //Tags
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = "Mes régimes",
                    style = AppTypo.SubTitle,
                    color = Color.Black

                )

                Spacer(modifier = Modifier.padding(AppSpacing.XXS))

                //Tag sélectionné
                FlowRow(
                    verticalArrangement = Arrangement.spacedBy(AppSpacing.S)
                ) {
                    if (preferedDiets.isNotEmpty()) {

                        preferedDiets.forEach { diet ->
                            TagsBoxProfil(
                                name = diet.name,
                                type = "Diet",
                                onRemove = {
                                    dietPreferenceViewModel.removePreferenceByDietId(diet.id)
                                }
                            )
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(AppSpacing.XXXXL)
                        .background(
                            color = AppColors.MainGreen,
                            shape = AppShapes.CornerL
                        )
                        .clickable{ onDismiss() },
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        text = "Ajouter",
                        style = AppTypo.SubTitle,
                        color = Color.White,
                    )
                }
            }
        }
    }
}