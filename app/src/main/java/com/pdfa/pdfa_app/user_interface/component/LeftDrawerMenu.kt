package com.pdfa.pdfa_app.user_interface.component

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pdfa.pdfa_app.user_interface.rooting.Screen

@Composable
fun DrawerMenu(
    onDestinationClicked: (String) -> Unit
) {
    ModalDrawerSheet {
        DrawerContent(onDestinationClicked = onDestinationClicked)
    }
}

@Composable
fun DrawerContent(
    onDestinationClicked: (String) -> Unit
) {


    Column (
        modifier = Modifier.padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(Modifier.height(12.dp))
        Text("Tirroir",modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.titleLarge)
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
        val navigationItems = listOf(
            Screen.Home,
            Screen.Fridge,
            Screen.Recipe,
            Screen.Cookbook,
            Screen.Shoplist
        )

        navigationItems.forEach { screen ->
            Text(
                text = screen.rout.removeSuffix("_screen").replaceFirstChar { it.uppercaseChar() }, // optionnel pour lisibilité
                modifier = Modifier
                    .clickable {
                        Log.d("DrawerClick", "Clicked ${screen.rout}")
                        onDestinationClicked(screen.rout)
                    }
                    .padding(16.dp)
            )
        }

    }
    }
