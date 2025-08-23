package com.pdfa.pdfa_app.user_interface.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.Alignment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    showBackButton: Boolean = false,
    onOpenDrawer: () -> Unit,
    onBackClick: () -> Unit = {}

) {
    TopAppBar(
        title = {
 
            },
        navigationIcon = {
            if (showBackButton){
                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = "Back",
                    modifier = Modifier.clickable { onBackClick() }
                )
            } else {
//                Icon(
//                    imageVector = Icons.Default.Menu,
//                    contentDescription = "Menu",
//                    modifier = Modifier.clickable { onOpenDrawer() }
//                )
            }

        }
    )
}

