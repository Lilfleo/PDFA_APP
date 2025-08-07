package com.pdfa.pdfa_app.user_interface.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
fun EditCookbook(
    onDismiss: () -> Unit
) {

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
                )
                .padding(AppSpacing.M),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Modifie tes sections",
                        style = AppTypo.SubTitle,
                        color = Color.Black
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .sizeIn(
                            maxHeight = 300.dp
                        )
                        .wrapContentHeight()
                        .padding(vertical = AppSpacing.M)
                        .shadow(
                            elevation = 2.dp,
                            shape = AppShapes.CornerL
                        )
                        .background(
                            color = Color.White,
                            shape = AppShapes.CornerM
                        )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .verticalScroll(scrollState)
                            .padding(AppSpacing.M),
                        verticalArrangement = Arrangement.spacedBy(AppSpacing.XS)
                    ) {
                        EditSection("Section 1")
                        EditSection("Section 2")
                        EditSection("Section 3")
                        EditSection("Section 1")
                        EditSection("Section 2")
                        EditSection("Section 3")

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
                        .height(AppSpacing.XXXXLL)
                        .padding(vertical = AppSpacing.M)
                        .shadow(
                            elevation = 2.dp,
                            shape = AppShapes.CornerL
                        )
                        .background(
                            color = Color.White,
                            shape = AppShapes.CornerM
                        )
                        .clickable {  },
                    contentAlignment = Alignment.Center
                ){
                    Icon(Icons.Default.Add, contentDescription = "Add", modifier = Modifier.size(AppSpacing.XXXXXL))
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ){
                    Box(
                        modifier = Modifier
//                            .padding(bottom = AppSpacing.L),
                    ){
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(AppShapes.CornerL)
                                .background(AppColors.MainGreen)
                                .clickable {
                                    onDismiss()
                                }
                        ) {
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
    }
}