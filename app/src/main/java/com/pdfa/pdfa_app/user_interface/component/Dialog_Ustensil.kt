package com.pdfa.pdfa_app.user_interface.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.pdfa.pdfa_app.ui.theme.AppColors
import com.pdfa.pdfa_app.ui.theme.AppShapes
import com.pdfa.pdfa_app.ui.theme.AppSpacing
import com.pdfa.pdfa_app.ui.theme.AppTypo
import com.pdfa.pdfa_app.ui.viewmodel.UtensilViewModel

@Composable
fun UstensilDialog(
    onDismiss: () -> Unit,
    viewModel: UtensilViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()

    // Observer les données
// LIGNE 51 - CORRIGÉE
    val allUtensils by viewModel.utensils.collectAsState(initial = emptyList())
    val utensilPreferences by viewModel.utensilPreferences.collectAsState(initial = emptyList())

    // IDs sélectionnés
    val selectedUtensilIds = remember(utensilPreferences) {
        utensilPreferences.map { it.utensilPreference.utensilId }.toSet()
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.90f)
                .wrapContentHeight()
                .background(color = Color.White, shape = AppShapes.CornerXL),
        ){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(AppSpacing.M),
                verticalArrangement = Arrangement.spacedBy(AppSpacing.S)
            ) {
                // Titre (inchangé)
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

                // Zone des ustensiles
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(550.dp)
                        .verticalScroll(scrollState),
                    contentAlignment = Alignment.TopCenter
                ){
                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        allUtensils.forEach { utensil ->
                            val isSelected = selectedUtensilIds.contains(utensil.id)

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
                                            viewModel.toggleUtensilSelection(utensil.id)
                                        }
                                        .shadow(elevation = 2.dp, shape = AppShapes.CornerM)
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
                                        text = utensil.name,
                                        style = AppTypo.Body,
                                        color = if (isSelected) Color.White else Color.Black
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

                // Bouton sauvegarder (inchangé)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(AppSpacing.XXXLL)
                        .clickable { onDismiss() }
                        .background(color = AppColors.MainGreen, shape = AppShapes.CornerM),
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
