package com.example.ddraft.ui.views

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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ddraft.R
import com.example.ddraft.ui.ForgeSections.AbilityScoresSection
import com.example.ddraft.ui.ForgeSections.BackgroundSection
import com.example.ddraft.ui.ForgeSections.ClassSection
import com.example.ddraft.ui.ForgeSections.EquipmentSection
import com.example.ddraft.ui.ForgeSections.SpeciesSection
import com.example.ddraft.ui.theme.ClassTheme
import com.example.ddraft.ui.util.NavMenu
import com.example.ddraft.ui.theme.LightBlack
import com.example.ddraft.viewModels.ForgeViewModel
import com.example.ddraft.viewModels.SharedVM

@Composable
fun ForgeScreen(forgeVM: ForgeViewModel, sharedVM: SharedVM, nc: NavController) {

    Box(modifier = Modifier
        .background(LightBlack)
        .fillMaxSize()
    ){
        Column{
            Spacer(Modifier.height(35.dp))
            StepSelection(forgeVM, sharedVM, listOf("1.Class", "2.Background", "3.Species", "4.Abilities", "5.Equipment"))
            HeaderSection(forgeVM, sharedVM)
            StepSection(forgeVM, sharedVM, nc)
        }
        NavMenu(
            sharedVM,
            nc,
            modifier = Modifier.align(Alignment.BottomCenter))
    }
}

@Composable
private fun StepSelection(
    forgeVM: ForgeViewModel,
    sharedVM: SharedVM,
    steps: List<String>
){
    LazyRow{
        items(steps.size){
            Box(modifier = Modifier
                .padding(start=15.dp, top=15.dp, bottom=15.dp)
                .fillParentMaxWidth(0.375F)
                .clickable {forgeVM.onStepChange(it)}
                .clip(RoundedCornerShape(10.dp))
                .background(if(forgeVM.selectedStep.value==it) sharedVM.darkColor.value else sharedVM.brightColor.value)
                .padding(15.dp),
                contentAlignment = Alignment.Center
            ){
                Text(text=steps[it], color = if(forgeVM.selectedStep.value==it) sharedVM.brightColor.value else sharedVM.darkColor.value)
            }
        }
    }
}

@Composable
private fun HeaderSection(
    forgeVM: ForgeViewModel,
    sharedVM: SharedVM
){
    Row(modifier = Modifier
        .padding(15.dp)
        .clip(RoundedCornerShape(10.dp))
        .background(sharedVM.midColor.value)
        .padding(horizontal = 15.dp , vertical = 20.dp)
        .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Box(
            modifier = Modifier
                .size(50.dp)
                .background(sharedVM.brightColor.value)
                .padding(5.dp),
            contentAlignment = Alignment.Center
        ){
            val classTheme = ClassTheme.getTheme(forgeVM.classChoice.value?.name?:"")
            Image(
                painter = painterResource(classTheme?.iconRes?:sharedVM.current.value.iconRes),
                contentDescription = "Class Icon",
                Modifier.size(30.dp)
            )
        }
        OutlinedTextField(
            value = forgeVM.name.value,
            onValueChange = {forgeVM.onNameChange(it)},
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = sharedVM.midColor.value,
                focusedContainerColor = sharedVM.brightColor.value,
                unfocusedTextColor = LightBlack,
                focusedTextColor = sharedVM.darkColor.value,
                unfocusedBorderColor = sharedVM.brightColor.value,
                focusedBorderColor = sharedVM.darkColor.value
            ),
            modifier = Modifier.fillMaxWidth(0.9F)
        )
    }
}

@Composable
private fun StepSection(forgeVM: ForgeViewModel, sharedVM: SharedVM, nc: NavController){
    Column(modifier = Modifier
        .padding(start = 7.5.dp, end = 7.5.dp)){
        AnimatedVisibility(
            visible = forgeVM.selectedStep.value==0,
            enter = slideInHorizontally() + fadeIn(),
            exit = slideOutHorizontally() + fadeOut()
        ){
            ClassSection(forgeVM, sharedVM)
        }
        AnimatedVisibility(
            visible = forgeVM.selectedStep.value==1,
            enter = slideInHorizontally() + fadeIn(),
            exit = slideOutHorizontally() + fadeOut()
        ){
            BackgroundSection(forgeVM, sharedVM)
        }
        AnimatedVisibility(
            visible = forgeVM.selectedStep.value==2,
            enter = slideInHorizontally() + fadeIn(),
            exit = slideOutHorizontally() + fadeOut()
        ){
            SpeciesSection(forgeVM, sharedVM)
        }
        AnimatedVisibility(
            visible = forgeVM.selectedStep.value==3,
            enter = slideInHorizontally() + fadeIn(),
            exit = slideOutHorizontally() + fadeOut()
        ){
            AbilityScoresSection(forgeVM, sharedVM)
        }
        AnimatedVisibility(
            visible = forgeVM.selectedStep.value==4,
            enter = slideInHorizontally() + fadeIn(),
            exit = slideOutHorizontally() + fadeOut()
        ){
            EquipmentSection(forgeVM, sharedVM, nc)
        }
    }
}