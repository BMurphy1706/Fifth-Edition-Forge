package com.example.ddraft.ui.ForgeSections

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ddraft.R
import com.example.ddraft.api.responses.ApiListItem
import com.example.ddraft.models.SRD.character_Elements.common.AbilityScore
import com.example.ddraft.models.SRD.class_Elements.Class
import com.example.ddraft.ui.ForgeSections.common.ExpandableSection
import com.example.ddraft.ui.ForgeSections.common.ProficienciesSection
import com.example.ddraft.ui.theme.ClassTheme
import com.example.ddraft.ui.theme.LightBlack
import com.example.ddraft.viewModels.ForgeViewModel
import com.example.ddraft.viewModels.SharedVM


@Composable
fun ClassSection(forgeVM: ForgeViewModel, sharedVM: SharedVM){
    Column{
        AnimatedVisibility(
            visible = !forgeVM.classDetailsVisi.value,
            enter = slideInHorizontally() + fadeIn(),
            exit = slideOutHorizontally() + fadeOut()
        ){
            ClassSelection(forgeVM, sharedVM)
        }
        AnimatedVisibility(
            visible = forgeVM.classDetailsVisi.value,
            enter = slideInHorizontally() + fadeIn(),
            exit = slideOutHorizontally() + fadeOut()
        ){
            ClassDetails(forgeVM, sharedVM)
        }
    }
}

@Composable
private fun ClassSelection(forgeVM: ForgeViewModel, sharedVM: SharedVM){
    Column{
        Text(
            text="Choose a Class",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(15.dp)
        )
        LazyColumn(Modifier.padding(bottom = 100.dp)){
            items(forgeVM.classList.value.size){
                ClassBox(
                    classItem = forgeVM.classList.value[it],
                    forgeVM = forgeVM,
                    sharedVM = sharedVM,
                )
            }
        }
    }
}

@Composable
private fun ClassBox(
    classItem: ApiListItem,
    forgeVM: ForgeViewModel,
    sharedVM: SharedVM,
){
    val classTheme = ClassTheme.getTheme(classItem.name)
    val boxBgColor = classTheme?.medium ?: Color.Gray
    val iconBgColor = classTheme?.light ?: Color.LightGray
    val textColor = classTheme?.dark ?: Color.Black
    val classIcon = classTheme?.iconRes ?: R.drawable.barbarian

    Row(modifier = Modifier
        .padding(15.dp)
        .clip(RoundedCornerShape(10.dp))
        .background(boxBgColor)
        .padding(horizontal = 15.dp , vertical = 20.dp)
        .fillMaxWidth()
        .clickable {
            sharedVM.updateColorsFromClass(classItem.name)
            forgeVM.GetClass(classItem.index)
            Log.d("Class item out", classItem.name)
            forgeVM.classVisiChange()
        },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Box(
            modifier = Modifier
                .size(50.dp)
                .background(iconBgColor)
                .padding(5.dp),
            contentAlignment = Alignment.Center
        ){
            Image(
                painter = painterResource(classIcon),
                contentDescription = "Class Icon",
                Modifier.size(30.dp),
            )
        }
        Text(
            text = classItem.name,
            color = textColor
        )
    }
}


@Composable
private fun ClassDetails(forgeVM: ForgeViewModel, sharedVM: SharedVM){
    val classChoice = forgeVM.classChoice.value
    Column{
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                text= classChoice?.name ?: "Class name",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(15.dp)
            )
            OutlinedTextField(
                value = forgeVM.lv.value.toString(),
                onValueChange = { text ->
                    if (text.isBlank()) {
                        // Option 1: treat empty as 0 (or 1)
                        forgeVM.onLvChange(0)
                    } else {
                        text.toIntOrNull()?.let { newLv ->
                            // Optionally clamp range, e.g. 1–20
                            val clamped = newLv.coerceIn(1, 20)
                            forgeVM.onLvChange(clamped)
                        }
                    }
                                },
                label = { Text("Level", color = sharedVM.darkColor.value) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = sharedVM.brightColor.value,
                    focusedContainerColor = sharedVM.midColor.value,
                    unfocusedTextColor = LightBlack,
                    focusedTextColor = Color.White,
                    unfocusedBorderColor = sharedVM.brightColor.value,
                    focusedBorderColor = sharedVM.darkColor.value
                ),
                modifier = Modifier
                    .fillMaxWidth(0.5F)
                    .padding(15.dp)
            )
        }
        LazyColumn(Modifier.padding(bottom = 100.dp)){
            item{classChoice?.let { HitDieSection(it, sharedVM) }}

            item{Spacer(Modifier.height(35.dp))}

            item {
                ExpandableSection("Saving Throws", sharedVM) {
                    forgeVM.classChoice.value?.saving_throws?.let { savingThrows ->
                        SavingThrowsSection(savingThrows, sharedVM)
                    }
                }
            }

            item{Spacer(Modifier.height(35.dp))}

            item {
                ExpandableSection(
                    title = "Proficiencies",
                    sharedVM = sharedVM
                ){
                    forgeVM.classChoice.value?.proficiencies.let { proficiencies ->
                        if (proficiencies != null) {
                            ProficienciesSection(proficiencies, sharedVM)
                        }
                    }
                }
            }

            item{Spacer(Modifier.height(35.dp))}

            item {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 7.5.dp),
                    horizontalArrangement = Arrangement.spacedBy(7.5.dp)
                ){
                    Button(
                        shape = RectangleShape,
                        modifier = Modifier
                            .weight(1F),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = sharedVM.brightColor.value
                        ),
                        onClick = {forgeVM.classVisiChange()}
                    ){
                        Text(
                            text = "Cancel",
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold,
                            color = sharedVM.darkColor.value,
                        )
                    }
                    Button(
                        shape = RectangleShape,
                        modifier = Modifier
                            .weight(1F),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = sharedVM.brightColor.value
                        ),
                        onClick = {
                            forgeVM.onClassChoice()
                            forgeVM.onStepChange(1)
                        }
                    ){
                        Text(
                            text = "Confirm",
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold,
                            color = sharedVM.darkColor.value,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun HitDieSection(
    c: Class,
    sharedVM: SharedVM
){
    Row(modifier = Modifier
        .clip(RoundedCornerShape(10.dp))
        .fillMaxWidth()
        .background(sharedVM.brightColor.value)
        .padding(16.dp)
    ){
        Text(
            text = "Hit Die: 1d${c.hit_die}",
            fontSize = 16.sp,
            color = sharedVM.darkColor.value
        )
    }
}

@Composable
private fun SavingThrowsSection(
    savingThrows: List<AbilityScore>,
    sharedVM: SharedVM,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(savingThrows.size) { abilityScore ->
            AbilityScoreChip(savingThrows[abilityScore], sharedVM)
        }
    }
}

@Composable
private fun AbilityScoreChip(abilityScore: AbilityScore, sharedVM: SharedVM) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = sharedVM.darkColor.value,
        modifier = Modifier
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "+${abilityScore.index.uppercase()}",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = sharedVM.brightColor.value
            )
        }
    }
}