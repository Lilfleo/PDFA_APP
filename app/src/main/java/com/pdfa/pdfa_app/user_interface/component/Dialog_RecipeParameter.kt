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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.pdfa.pdfa_app.ui.theme.AppColors
import com.pdfa.pdfa_app.ui.theme.AppShapes

@Composable
fun RecipeParameter(
    onDismiss: () -> Unit
){
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false // Permet d'utiliser toute la largeur
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.90f)
                .wrapContentHeight()
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(16.dp)
                ),

        ){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {

                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = "Modifie les paramètres de création de recettes"
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier
                            .weight(3f),
                        text = "Modifie tes régimes alimentaires"
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
                                .padding(
                                    start = 2.dp,
                                    end = 2.dp,
                                    top = 5.dp,
                                    bottom = 5.dp)

                        ){
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                text = "Mes Régimes",
                            )
                        }
                    }
                }
                //Tags
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = "Mes Tags"
                )

                //Tag sélectionné
                Row {
                    TagsBox("Facile", "Easy")
                    TagsBox("Facile", "Easy")
                    TagsBox("Facile", "Easy")
                }

                //Autres Tag par catégorie

                Column(
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                ){
                    Text(
                        text = "Titre de la catégorie"
                    )
                    Row {
                        TagsBox("Facile", "Easy")
                        TagsBox("Facile", "Easy")
                        TagsBox("Facile", "Easy")
                    }
                }

                Spacer(modifier = Modifier.padding(10.dp))
                //Bouton
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .background(
                            color = AppColors.MainGreen,
                            shape = AppShapes.CornerS)
                        .clickable {
                            onDismiss()
                        }
                        .padding(top = 7.dp, bottom = 7.dp)

                ) {
                    Text(
                        text = "Nouvelle les recettes"
                    )
                }
            }
        }
    }
}