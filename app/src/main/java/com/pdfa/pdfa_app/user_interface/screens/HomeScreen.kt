package com.pdfa.pdfa_app.user_interface.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pdfa.pdfa_app.data.model.Allergy
import com.pdfa.pdfa_app.ui.theme.AppColors
import com.pdfa.pdfa_app.ui.theme.AppShapes
import com.pdfa.pdfa_app.ui.theme.AppSpacing
import com.pdfa.pdfa_app.ui.viewmodel.ProfilViewModel
import com.pdfa.pdfa_app.user_interface.component.AllergiesDialog
import com.pdfa.pdfa_app.user_interface.component.DietDialog
import com.pdfa.pdfa_app.user_interface.component.EditProfil
import com.pdfa.pdfa_app.user_interface.component.EditTag
import com.pdfa.pdfa_app.user_interface.component.UstensilDialog

@Composable
fun HomeScreen(
    profilViewModel: ProfilViewModel = hiltViewModel()
){

    var openTagDialog by remember { mutableStateOf(false) }
    var showAllergyDialog by remember { mutableStateOf(false) }
    var showUtensilDialog by remember { mutableStateOf(false) }
    var showDietDialog by remember { mutableStateOf(false) }

    val profil by profilViewModel.profil.collectAsState(initial = null)
    var showProfilDialog by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Primary)
            .padding(horizontal = 16.dp)
        ,
        verticalArrangement = Arrangement.spacedBy(AppSpacing.M)
    ) {
        item {
            // Carte de bienvenue verte
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(225.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = AppColors.MainGreen),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp)
                ) {
                    profil?.let { currentProfil ->
                        Text(
                            text = "Bonjour ${currentProfil.name},",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "De votre frigo à votre assiette, l'IA fait le reste",
                        fontSize = 16.sp,
                        color = Color.White,
                        lineHeight = 20.sp
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.BottomEnd
                    ) {
                        Box(
                            modifier = Modifier
                                .size(AppSpacing.XXXLL)
                                .clickable{ showProfilDialog = true }
                                .background(
                                    color = AppColors.MainGreen,
                                    shape = AppShapes.CornerS
                                ),
                            contentAlignment = Alignment.Center
                        ){
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Edit Profil",
                                tint = Color.White,
                                modifier = Modifier.size(AppSpacing.XXL)
                            )
                        }
                    }
                }
            }
        }

        item {
            // Carte vide (placeholder)
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                // Contenu vide pour l'instant
            }
        }

        item {
            // Carte "Envie de recette différente?"
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                onClick = { openTagDialog = true }
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = "Envie de recette différente?",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Change le style des recettes que l'on te propose en changeant tes Tags",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        lineHeight = 18.sp
                    )
                }
            }
        }

        item {
            // Carte allergies/aliments
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                onClick = { showAllergyDialog = true }
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = buildAnnotatedString {
                            append("Met à jour tes ")
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("allergies")
                            }
                            append(" ou les aliments que ")
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("tu n'aimes pas")
                            }
                        },
                        fontSize = 18.sp,
                        color = Color.Black,
                        lineHeight = 22.sp
                    )
                }
            }
        }

        item {
            // Deux petites cartes en bas
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Carte "Un nouvel ustensile?"
                Card(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    onClick = { showUtensilDialog = true }
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Un nouvel",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black
                        )
                        Text(
                            text = "ustensile?",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "ajoute le",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                }

                // Carte "Envie de changer de régime?"
                Card(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    onClick = { showDietDialog = true }
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "Envie de changer",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black
                        )
                        Text(
                            text = "de régime?",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "modifie le",
                            fontSize = 12.sp,
                            color = Color.Gray,
                        )
                    }
                }
            }
        }
    }

    if (showProfilDialog) {
        EditProfil(
            onDismiss = {showProfilDialog = false}
        )
    }

    if (openTagDialog) {
        EditTag(
            onDismiss = { openTagDialog = false }
        )
    }

    if (showAllergyDialog) {
        AllergiesDialog(
            onDismiss = { showAllergyDialog = false }
        )
    }

    if (showUtensilDialog) {
        UstensilDialog(
            onDismiss = { showUtensilDialog = false }
        )
    }

    if (showDietDialog) {
        DietDialog(
            onDismiss = { showDietDialog = false }
        )
    }
}
