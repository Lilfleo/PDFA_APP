package com.pdfa.pdfa_app.user_interface.component

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.pdfa.pdfa_app.ui.theme.AppSpacing
import com.pdfa.pdfa_app.ui.theme.AppTypo
import com.pdfa.pdfa_app.user_interface.rooting.Screen
import com.pdfa.pdfa_app.user_interface.screens.ProfilScreen

@Composable
fun DrawerMenu(
//    onDestinationClicked: (String) -> Unit,
    navController: NavController
) {
    ModalDrawerSheet {
        DrawerContent(
//            onDestinationClicked = onDestinationClicked,
            navController = navController
            )
    }
}

@Composable
fun DrawerContent(
//    onDestinationClicked: (String) -> Unit,
    navController: NavController
) {

    Column (
        modifier = Modifier.padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(Modifier.height(12.dp))
        Text("Tirroir",modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.titleLarge)
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

        Box(modifier = Modifier
            .clickable { navController.navigate(Screen.ProfilScreen.rout) }
            .fillMaxHeight()
            .height(AppSpacing.XL)
            ,
            contentAlignment = Alignment.CenterStart
        ){
            Text(
                text = "Mon Profil",
                style = AppTypo.Body,
                color = Color.Black
            )
        }
    }
}
