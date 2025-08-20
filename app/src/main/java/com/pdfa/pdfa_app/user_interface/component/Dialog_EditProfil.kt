package com.pdfa.pdfa_app.user_interface.component

import android.app.DatePickerDialog
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.pdfa.pdfa_app.R
import com.pdfa.pdfa_app.data.model.FoodDetail
import com.pdfa.pdfa_app.ui.theme.AppColors
import com.pdfa.pdfa_app.ui.theme.AppShapes
import com.pdfa.pdfa_app.ui.theme.AppSpacing
import com.pdfa.pdfa_app.ui.theme.AppTypo
import com.pdfa.pdfa_app.ui.viewmodel.ProfilViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

@Composable
fun EditProfil(
    onDismiss: () -> Unit,
    profilViewModel: ProfilViewModel = hiltViewModel()
) {

    val profil by profilViewModel.profil.collectAsState(initial = null)
    val coroutineScope = rememberCoroutineScope()

    // États pour les champs modifiables
    var profilName by remember(profil) { mutableStateOf(profil?.name ?: "") }
    var profilAge by remember(profil) { mutableStateOf(profil?.age?.toString() ?: "") }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.90f)
                .background(
                    color = Color.White,
                    shape = AppShapes.CornerL
                )
                .padding(AppSpacing.L)
        ) {
            Column {
                Text(
                    text = "Modifie ton profil ici!",
                    style = AppTypo.SubTitle2
                )

                Spacer(modifier = Modifier.height(AppSpacing.L))

                // Champ pour le nom
                OutlinedTextField(
                    value = profilName,
                    onValueChange = { profilName = it },
                    label = { Text("Nom") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(AppSpacing.M))

                // Champ pour l'âge
                OutlinedTextField(
                    value = profilAge,
                    onValueChange = { newValue ->
                        // Filtrer pour n'accepter que les chiffres
                        if (newValue.all { it.isDigit() } && newValue.length <= 3) {
                            profilAge = newValue
                        }
                    },
                    label = { Text("Âge") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(AppSpacing.L))

                // Bouton de sauvegarde
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp),
                ){
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(AppShapes.CornerL)
                            .background(AppColors.MainGreen)
                            .clickable {
                                // Sauvegarder les modifications
                                val updatedProfil = profil?.copy(
                                    name = profilName,
                                    age = profilAge.toIntOrNull() ?: profil!!.age
                                )

                                updatedProfil?.let {
                                    coroutineScope.launch {
                                        profilViewModel.updateProfil(it)
                                        onDismiss()
                                    }
                                }                            }
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
