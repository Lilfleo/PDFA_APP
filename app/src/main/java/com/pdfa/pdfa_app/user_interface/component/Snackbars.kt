package com.pdfa.pdfa_app.user_interface.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.pdfa.pdfa_app.ui.theme.AppColors

@Composable
fun CustomSnackbarHost(
    hostState: SnackbarHostState
) {
    SnackbarHost(
        hostState = hostState
    ) { snackbarData ->
        val isSuccess = snackbarData.visuals.actionLabel == "success"
        val isWaiting = snackbarData.visuals.actionLabel == "waiting"
        val isFail = snackbarData.visuals.actionLabel == "fail"

        val backgroundColor = when {
            isSuccess -> AppColors.MainGreen
            isWaiting -> AppColors.MainYellow
            isFail -> AppColors.MainRed
            else -> AppColors.MainGrey
        }

        Box(
            modifier = Modifier.clickable { snackbarData.dismiss() } // clique n'importe où
        ) {
            Snackbar(
                containerColor = backgroundColor,
                contentColor = Color.White,
                action = {
                    TextButton(onClick = { snackbarData.dismiss() }) {
                        Text("✕", color = Color.White)
                    }
                }
            ) {
                Text(snackbarData.visuals.message)
            }
        }
    }
}
