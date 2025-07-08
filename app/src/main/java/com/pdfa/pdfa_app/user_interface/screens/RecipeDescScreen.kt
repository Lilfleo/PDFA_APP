package com.pdfa.pdfa_app.user_interface.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.pdfa.pdfa_app.ui.theme.AppColors
import com.pdfa.pdfa_app.ui.theme.AppShapes
import com.pdfa.pdfa_app.user_interface.component.RecipeCard
import com.pdfa.pdfa_app.user_interface.component.TagsBox

@Composable
fun RecipeDetailScreen(
    navController: NavController
){

    var nbPeople by remember { mutableIntStateOf(2) }
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Primary)
            .padding(10.dp)
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {

            //Titre
            Row {
                Text(
                    text = "Recipe title"
                )

            }
            //Subtitle
            Text(
                text = "subtitle"
            )

            //Tags
            Row {
                TagsBox("Facile", "Easy", true)
                TagsBox("Facile", "Easy", true)
            }

            //Régimes
            Row {
                TagsBox("Facile", "Easy", true)
                TagsBox("Facile", "Easy", true)
            }

            //Temps
            Row(
                modifier = Modifier
                    .padding(vertical = 10.dp)
            ) {
                RecipeCard("20", "time")

                Spacer(modifier = Modifier.padding(10.dp))

                RecipeCard("20", "time")

                Spacer(modifier = Modifier.padding(10.dp))

                RecipeCard("20", "calories")
            }

            Text(
                text = "Ingrédient"
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Pour"
                )
                Spacer(Modifier.padding(1.dp))
                Box(
                    modifier = Modifier
                        .width(50.dp)
                        .height(30.dp)
                        .padding(2.dp)
                ){
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .shadow(
                                elevation = 2.dp,
                                shape = AppShapes.CornerS
                            )
                            .background(
                                color = Color.White,
                                shape = AppShapes.CornerS
                            )
                            .clickable { expanded = !expanded },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ){
                        Text(
                            text = nbPeople.toString(),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.weight(1f)
                        )
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = "Back",
                            modifier = Modifier.weight(1f)

                        )
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            modifier = Modifier
                                .width(40.dp)
                                .background(Color.White)
                        ) {
                            DropdownMenuItem(
                                text = { Text("1") },
                                onClick = { nbPeople = 1; expanded = false },
                                modifier = Modifier
                                    .height(30.dp)
                            )
                            DropdownMenuItem(
                                text = { Text("2") },
                                onClick = { nbPeople = 2; expanded = false },
                                modifier = Modifier
                                    .height(30.dp)
                            )
                            DropdownMenuItem(
                                text = { Text("3") },
                                onClick = { nbPeople = 3; expanded = false },
                                modifier = Modifier
                                    .height(30.dp)
                            )
                            DropdownMenuItem(
                                text = { Text("4") },
                                onClick = { nbPeople = 4; expanded = false },
                                modifier = Modifier
                                    .height(30.dp)
                            )
                            DropdownMenuItem(
                                text = { Text("5") },
                                onClick = { nbPeople = 6; expanded = false },
                                modifier = Modifier
                                    .height(30.dp)
                            )
                            DropdownMenuItem(
                                text = { Text("7") },
                                onClick = { nbPeople = 7; expanded = false },
                                modifier = Modifier
                                    .height(30.dp)
                            )
                            DropdownMenuItem(
                                text = { Text("8") },
                                onClick = { nbPeople = 8; expanded = false },
                                modifier = Modifier
                                    .height(30.dp)
                            )
                            DropdownMenuItem(
                                text = { Text("9") },
                                onClick = { nbPeople = 9; expanded = false },
                                modifier = Modifier
                                    .height(30.dp)
                            )
                            DropdownMenuItem(
                                text = { Text("10") },
                                onClick = { nbPeople = 10; expanded = false },
                                modifier = Modifier
                                    .height(30.dp)
                            )
                        }
                    }
                }

                Spacer(Modifier.padding(1.dp))
                Text(
                    text = "Personnes"
                )
            }

            //Liste des ingrédients
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(500.dp)
                    .padding(vertical = 10.dp)
                    .shadow(
                        elevation = 2.dp,
                        shape = AppShapes.CornerL
                    )
                    .background(
                        color = Color.White,
                        shape = AppShapes.CornerM
                    )
            ){

            }

            //Boutons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                        .clip(AppShapes.CornerL)
                        .background(AppColors.MainGreen)
                        .clickable {

                        }
                ) {
                    Text(
                        text = "Ajouter à la liste"
                    )
                }

                Spacer(modifier = Modifier.padding(5.dp))

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                        .clip(AppShapes.CornerL)
                        .background(AppColors.MainGreen)
                        .clickable {

                        }
                ) {
                    Text(
                        text = "Voir la recette",

                    )
                }
            }
        }
    }
}