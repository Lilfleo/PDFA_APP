package com.pdfa.pdfa_app.user_interface.component


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.pdfa.pdfa_app.R





@Composable
fun AddFoodDialog(
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(16.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_nav_bag),
                        contentDescription = null,
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Ajoute un aliment à ton frigo",
                        style = MaterialTheme.typography.titleMedium
                    )
                }



                Spacer(modifier = Modifier.height(16.dp))

                // La liste des catégories
                val categories = remember {
                    mutableStateListOf(
                        FoodCategory("Fruits"),
                        FoodCategory("Légumes"),
                        FoodCategory("Produits Laitiers"),
                        FoodCategory("Viandes"),
                        FoodCategory("Boissons")
                    )
                }


                CustomSearchWithSuggestions(
                    categories = categories,
                    onItemSelected = { selected ->
                        // Tu peux faire ce que tu veux ici
                        println("Sélectionné : $selected")
                    }
                )


                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onDismiss,
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Fermer")
                }
            }
        }
    }
}


