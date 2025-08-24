package com.pdfa.pdfa_app.user_interface.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.pdfa.pdfa_app.data.model.FoodDetail
import com.pdfa.pdfa_app.ui.theme.AppColors
import com.pdfa.pdfa_app.ui.theme.AppShapes
import com.pdfa.pdfa_app.ui.theme.AppSpacing
import com.pdfa.pdfa_app.ui.theme.AppTypo

@Composable
fun DeleteConfirmationGeneral(
    itemName: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    onDelete: () -> Unit
){
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.90f)
                .background(color = Color.White, shape = AppShapes.CornerL)
                .padding(AppSpacing.L)
        ) {
            Column {
                Text(
                    text = "Supprimer cette section ?",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )

                Spacer(modifier = Modifier.height(AppSpacing.M))

                Text(
                    text = "Es-tu sûr de vouloir supprimer \"$itemName\" ? Cette action est irréversible.",
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(AppSpacing.L))

                // Bouton Annuler

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .background(
                            color = AppColors.MainGrey,
                            shape = AppShapes.CornerM)
                        .clickable {
                            onDismiss()
                        }
                        .padding(vertical = AppSpacing.S)

                ) {
                    Text(
                        text = "Annuler",
                        style = AppTypo.SubTitle,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(AppSpacing.S))

                // Bouton Supprimer

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .background(
                            color = AppColors.MainRed,
                            shape = AppShapes.CornerM)
                        .clickable {
                            onDelete()
                            onDismiss()
                        }
                        .padding(vertical = AppSpacing.S)

                ) {
                    Text(
                        text = "Supprimer",
                        style = AppTypo.SubTitle,
                        color = Color.White
                    )
                }

            }
        }
    }
}