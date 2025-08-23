package com.pdfa.pdfa_app.user_interface.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.pdfa.pdfa_app.api.Ingredient
import com.pdfa.pdfa_app.api.RecipeForShoplist
import com.pdfa.pdfa_app.api.RecipeWithFood
import com.pdfa.pdfa_app.api.RecipeWithFoodPrompt
import com.pdfa.pdfa_app.api.RecipeWithoutFoodPrompt
import com.pdfa.pdfa_app.api.Tags
import com.pdfa.pdfa_app.ui.theme.AppColors
import com.pdfa.pdfa_app.ui.theme.AppShapes
import com.pdfa.pdfa_app.ui.theme.AppSpacing
import com.pdfa.pdfa_app.ui.theme.AppTypo
import com.pdfa.pdfa_app.ui.viewmodel.RecipeViewModel
import com.pdfa.pdfa_app.ui.viewmodel.TagPreferenceViewModel
import com.pdfa.pdfa_app.ui.viewmodel.TagViewModel
import com.pdfa.pdfa_app.user_interface.rooting.Screen

@Composable
fun EditTag(
    onDismiss: () -> Unit,
    tagViewModel: TagViewModel = hiltViewModel(),
    tagPreferenceViewModel: TagPreferenceViewModel = hiltViewModel()
){

    val allTags by tagViewModel.tags.collectAsState()
    val tagPreferences by tagPreferenceViewModel.tagPreferenceList.collectAsState()

    // Séparer les tags en deux listes
    val preferredTags = tagPreferences.map { it.tag }
    val otherTags = allTags.filter { tag ->
        !preferredTags.any { it.id == tag.id }
    }


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
                    text = "Modifie tes Tags",
                    style = AppTypo.SubTitle,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.padding(AppSpacing.XXS))

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
                FlowRow(
                    verticalArrangement = Arrangement.spacedBy(AppSpacing.S)
                ) {
                    if (preferredTags.isNotEmpty()) {

                        preferredTags.forEach { tag ->
                            TagsBox(
                                tag = tag,
                                onRemove = {
                                    tagPreferenceViewModel.removeTagPreferenceByTagId(tag.id)
                                }
                            )
                        }
                    }
                }

                //Autres Tag par catégorie
                Column(
                    modifier = Modifier
                        .padding(vertical = AppSpacing.S)
                ){
                    Text(
                        text = "Les Tags disponibles",
                        style = AppTypo.Body,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.padding(AppSpacing.XXS))

                    FlowRow(
                        modifier = Modifier
                            .heightIn(max = 250.dp)
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(AppSpacing.S)
                    ) {
                        if (otherTags.isNotEmpty()) {

                            otherTags.forEach { tag ->
                                TagsBox(
                                    tag = tag,
                                    isSelected = false,
                                    onClick = {
                                        tagPreferenceViewModel.addTagPreference(tag.id)
                                    }
                                )
                            }
                        }
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
                        text = "Sauvegarder",
                        style = AppTypo.SubTitle,
                        color = Color.White
                    )
                }
            }
        }
    }
}