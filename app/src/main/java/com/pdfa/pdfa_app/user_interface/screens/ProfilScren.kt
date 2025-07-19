package com.pdfa.pdfa_app.user_interface.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.pdfa.pdfa_app.ui.theme.AppColors
import com.pdfa.pdfa_app.ui.theme.AppShapes
import com.pdfa.pdfa_app.ui.theme.AppSpacing
import com.pdfa.pdfa_app.ui.theme.AppTypo
import com.pdfa.pdfa_app.user_interface.component.AllergiesDialog
import com.pdfa.pdfa_app.user_interface.component.DietDialog
import com.pdfa.pdfa_app.user_interface.component.RecipeParameter
import com.pdfa.pdfa_app.user_interface.component.ScrollbarPersonnalisee
import com.pdfa.pdfa_app.user_interface.component.TagsBoxProfil
import com.pdfa.pdfa_app.user_interface.component.UstensilDialog

@Composable
fun ProfilScreen(
    navController: NavController
){
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier.fillMaxSize().padding(vertical = AppSpacing.M).background(AppColors.Primary)
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(AppSpacing.M)
                .background(AppColors.Primary)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            ){
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(100.dp)
                        .background(AppColors.MainGrey)
                )

                Spacer(modifier = Modifier.padding(AppSpacing.S))

                Box(
                    modifier = Modifier
                        .fillMaxHeight(),
                    contentAlignment = Alignment.BottomStart
                ){
                    Text(
                        text = "Antoine",
                        style = AppTypo.Title,
                        color = Color.Black,
                    )
                }
            }

            AllergiesSection()
            DietSection()
            UstensilSection()

        }
        ScrollbarPersonnalisee(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .fillMaxHeight()
                .width(10.dp),
            scrollState = scrollState
        )
    }
}

@Composable
fun AllergiesSection() {

    var showAllergyDialog by remember { mutableStateOf(false) }
    val listeTags = remember { mutableStateListOf<String>() }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical =  AppSpacing.S),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Mes Allergies",
                style = AppTypo.SubTitle,
                color = Color.Black
            )

            Box(
                modifier = Modifier
                    .size(AppSpacing.XXXLLL)
                    .clickable { showAllergyDialog = true }
                    .shadow(
                        elevation = 3.dp,
                        shape = AppShapes.CornerM
                    )
                    .background(
                        color = Color.White,
                        shape = AppShapes.CornerS
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Allergies",
                    tint = Color.Black,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = AppSpacing.S),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(AppSpacing.XXS)
        ) {
            listeTags.forEach { tag ->
                TagsBoxProfil(
                    name = tag,
                    type = "Allergy",
                    onRemove = {
                        listeTags.remove(tag)
                    }
                )
            }
            if (showAllergyDialog) {
                AllergiesDialog(
                    onDismiss = { showAllergyDialog = false },
                    addAllergy = { selectedTag ->
                        listeTags.add(selectedTag)
                        showAllergyDialog = false
                    }
                )
            }
        }
    }
}

@Composable
fun DietSection() {

    var showDietDialog by remember { mutableStateOf(false) }
    val listeTags = remember { mutableStateListOf<String>() }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical =  AppSpacing.S),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Mes Régimes Alimentaires",
                style = AppTypo.SubTitle,
                color = Color.Black
            )

            Box(
                modifier = Modifier
                    .size(AppSpacing.XXXLLL)
                    .clickable { showDietDialog = true }
                    .shadow(
                        elevation = 3.dp,
                        shape = AppShapes.CornerM
                    )
                    .background(
                        color = Color.White,
                        shape = AppShapes.CornerS
                    ),
                contentAlignment = Alignment.Center
            ){
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Allergies",
                    tint = Color.Black,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        FlowRow (
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = AppSpacing.S),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(AppSpacing.XXS)
        ) {
            listeTags.forEach { tag ->
                TagsBoxProfil(
                    name = tag,
                    type = "Diet",
                    onRemove = {
                        listeTags.remove(tag)
                    }
                )
            }
        }

        if (showDietDialog) {
            DietDialog(
                onDismiss = { showDietDialog = false },
                addDiet = { selectedTag ->
                    listeTags.add(selectedTag)
                    showDietDialog = false
                }
            )
        }

    }
}

@Composable
fun UstensilSection() {

    var showUstensilDialog by remember { mutableStateOf(false) }


    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical =  AppSpacing.S),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Mes ustensiles",
                style = AppTypo.SubTitle,
                color = Color.Black
            )

            Box(
                modifier = Modifier
                    .size(AppSpacing.XXXLLL)
                    .clickable { showUstensilDialog = true }
                    .shadow(
                        elevation = 3.dp,
                        shape = AppShapes.CornerM
                    )
                    .background(
                        color = Color.White,
                        shape = AppShapes.CornerS
                    ),
                contentAlignment = Alignment.Center
            ){
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Allergies",
                    tint = Color.Black,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
    if (showUstensilDialog) {
        UstensilDialog (
            onDismiss = { showUstensilDialog = false },
        )
    }
}