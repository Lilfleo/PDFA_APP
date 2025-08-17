package com.pdfa.pdfa_app.user_interface.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.pdfa.pdfa_app.ui.theme.AppColors
import com.pdfa.pdfa_app.ui.theme.AppShapes
import com.pdfa.pdfa_app.ui.theme.AppSpacing
import com.pdfa.pdfa_app.ui.theme.AppTypo

@Composable
fun UstensilDialog(
    onDismiss: () -> Unit
){

    val listeUstensil = remember { mutableStateListOf(    "couteau de chef",
        "couteau d'office",
        "économe",
        "cuillère en bois",
        "spatule",
        "fouet",
        "louche",
        "écumoire",
        "pinces de cuisine",
        "planche à découper",
        "râpe",
        "passoire",
        "chinois",
        "presse-ail",
        "ouvre-boîte",
        "ouvre-bouteille",
        "tire-bouchon",
        "rouleau à pâtisserie",
        "balance de cuisine",
        "verre doseur",
        "mixeur plongeant",
        "saladier",
        "ciseaux de cuisine",
        "pinceau de cuisine",
        "pelle à tarte",
        "zesteur",
        "casserole",
        "poêle",
        "faitout",
        "marmite",
        "cocotte en fonte",
        "cuiseur vapeur",
        "mixeur",
        "blender",
        "robot multifonction",
        "presse-agrumes",
        "bouilloire",
        "grille-pain",
        "batteur électrique",
        "moule à gâteau",
        "plat à gratin",
        "tamis",
        "poche à douille",
        "minuteur",
        "spatule maryse",
        "emporte-pièces",
        "siphon",
        "planche à pain",
        "mortier et pilon",
        "torchon",
        "gant de cuisine" ) }

    val listeUstensilSelected = remember { mutableStateListOf<String>() }
    var isSelected by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

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
                    .padding(AppSpacing.M),
                verticalArrangement = Arrangement.spacedBy(AppSpacing.S)
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(AppSpacing.M),
                    contentAlignment = Alignment.Center
                    ){
                    Text(
                        text = "Mes Ustensiles",
                        style = AppTypo.SubTitle2,
                        color = Color.Black
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(550.dp)
                        .verticalScroll(scrollState),
                    contentAlignment = Alignment.TopCenter
                ){
                    FlowRow(
                        modifier = Modifier
                            .fillMaxWidth(),
                    ) {
                        listeUstensil.forEach { tag ->
                            isSelected = if (listeUstensilSelected.contains(tag)){
                                true
                            } else {
                                false
                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(0.5f)
                                    .height(70.dp)
                                    .padding(vertical = AppSpacing.XS, horizontal = AppSpacing.XS),
                                contentAlignment = Alignment.Center
                            ){
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .clickable {
                                            isSelected = !isSelected
                                            if (listeUstensilSelected.contains(tag)) {
                                                listeUstensilSelected.remove(tag)
                                            } else {
                                                listeUstensilSelected.add(tag)
                                            }
                                        }
                                        .shadow(
                                            elevation = 2.dp,
                                            shape = AppShapes.CornerM
                                        )
                                        .background(
                                            color = if (isSelected) {
                                                AppColors.MainGreen
                                            } else {
                                                Color.White
                                            },
                                            shape = AppShapes.CornerM
                                        ),
                                    contentAlignment = Alignment.Center
                                ){
                                    Text(
                                        text = tag,
                                        style = AppTypo.Body,
                                        color = if (isSelected) {
                                            Color.White
                                        } else {
                                            Color.Black
                                        }
                                    )
                                }
                            }
                        }
                    }
                    ScrollbarPersonnalisee(
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .fillMaxHeight()
                            .width(10.dp),
                        scrollState = scrollState
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(AppSpacing.XXXLL)
                        .clickable { onDismiss() }
                        .background(
                            color = AppColors.MainGreen,
                            shape = AppShapes.CornerM
                        ),
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        text = "Sauvegarder",
                        style = AppTypo.SubTitle,
                        color = Color.White
                    )
                }
            }
        }
    }
}