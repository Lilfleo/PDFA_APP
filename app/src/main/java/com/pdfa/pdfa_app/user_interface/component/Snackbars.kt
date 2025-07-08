package com.pdfa.pdfa_app.user_interface.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
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
            modifier = Modifier
                .clickable { snackbarData.dismiss() } // clique n'importe où
                .widthIn(max = 250.dp)
                .padding(8.dp)

        ) {
            Snackbar(
                shape = RoundedCornerShape(12.dp),
                containerColor = backgroundColor,
                contentColor = Color.White,

            ) {
                Text(
                    text = snackbarData.visuals.message,
                    maxLines = 2,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

            }
        }
    }
}
