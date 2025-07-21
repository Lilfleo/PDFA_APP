package com.pdfa.pdfa_app.user_interface.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.pdfa.pdfa_app.ui.theme.AppColors
import com.pdfa.pdfa_app.ui.theme.AppShapes
import com.pdfa.pdfa_app.ui.theme.AppSpacing
import com.pdfa.pdfa_app.ui.theme.AppTypo

@Composable
fun RecipeParameter(
    onDismiss: () -> Unit
){
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
                ),
        ){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(AppSpacing.XL)
            ) {

                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = "Modifie les paramètres de création de recettes",
                    style = AppTypo.SubTitle,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.padding(AppSpacing.XXS))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier
                            .weight(3f),
                        text = "Modifie tes régimes alimentaires",
                        style = AppTypo.Body,
                        color = Color.Black
                    )
                    Box(
                        modifier = Modifier
                            .weight(2f)
                    ) {
                        Box(
                            modifier = Modifier
                                .background(
                                    color = Color.White,
                                    shape = AppShapes.CornerM
                                )
                                .border(
                                    width =  1.dp,
                                    color = Color.Black,
                                    shape = AppShapes.CornerM
                                )
                                .padding( horizontal =  2.dp, vertical =  5.dp)

                        ){
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                text = "Mes Régimes",
                                style = AppTypo.Body,
                                color = Color.Black
                            )
                        }
                    }
                }
                //Tags
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = "Mes Tags",
                    style = AppTypo.SubTitle,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.padding(AppSpacing.XXS))

                //Tag sélectionné
                Row {
                    TagsBox("Facile", "Easy", true)
                    TagsBox("Facile", "Easy", true)
                    TagsBox("Facile", "Easy", true)
                }

                //Autres Tag par catégorie
                Column(
                    modifier = Modifier
                        .padding(vertical = AppSpacing.S)
                ){
                    Text(
                        text = "Titre de la catégorie",
                        style = AppTypo.Body,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.padding(AppSpacing.XXS))
                    Row {
                        TagsBox("Facile", "Easy", false)
                        TagsBox("Facile", "Easy", false)
                        TagsBox("Facile", "Easy", false)
                    }
                }

                Spacer(modifier = Modifier.padding(AppSpacing.M))
                //Bouton
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .background(
                            color = AppColors.MainGreen,
                            shape = AppShapes.CornerM)
                        .clickable {
                            onDismiss()
                        }
                        .padding(vertical = AppSpacing.S)

                ) {
                    Text(
                        text = "Nouvelle les recettes",
                        style = AppTypo.SubTitle,
                        color = Color.White
                    )
                }
            }
        }
    }
}