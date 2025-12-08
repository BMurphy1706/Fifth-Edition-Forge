package com.example.ddraft.ui

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
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
import com.example.ddraft.models.SRD.class_Elements.Class
import com.example.ddraft.models.SRD.class_Elements.ClassListItem
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
fun StepSelection(
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
fun HeaderSection(
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
            Icon(
                painter = painterResource(R.drawable.search),
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
fun StepSection(forgeVM: ForgeViewModel, sharedVM: SharedVM, nc: NavController){
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
            BackgroundSection()
        }
        AnimatedVisibility(
            visible = forgeVM.selectedStep.value==2,
            enter = slideInHorizontally() + fadeIn(),
            exit = slideOutHorizontally() + fadeOut()
        ){
            SpeciesSection()
        }
        AnimatedVisibility(
            visible = forgeVM.selectedStep.value==3,
            enter = slideInHorizontally() + fadeIn(),
            exit = slideOutHorizontally() + fadeOut()
        ){
            AbilitySection()
        }
        AnimatedVisibility(
            visible = forgeVM.selectedStep.value==4,
            enter = slideInHorizontally() + fadeIn(),
            exit = slideOutHorizontally() + fadeOut()
        ){
            EquipmentSection()
        }
    }
}

@Composable
fun ClassSection(forgeVM: ForgeViewModel, sharedVM: SharedVM){
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
                    sharedVM = sharedVM
                )
            }
        }
    }
}

@Composable
fun ClassBox(
    classItem: ClassListItem,
    forgeVM: ForgeViewModel,
    sharedVM: SharedVM
){
    Row(modifier = Modifier
        .padding(15.dp)
        .clip(RoundedCornerShape(10.dp))
        .background(sharedVM.midColor.value)
        .padding(horizontal = 15.dp , vertical = 20.dp)
        .fillMaxWidth()
        .clickable { forgeVM.GetClass(classItem.index)
            Log.d("Class item out", classItem.name)
            forgeVM.classChoice.value?.let { Log.d("Class out", it.name) }
        },
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
            Icon(
                painter = painterResource(R.drawable.search),
                contentDescription = "Class Icon",
                Modifier.size(30.dp)
            )
        }
        Text(classItem.name)
    }
}

@Composable
fun BackgroundSection(){
    Column{
        Text(
            text="Choose a Background",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(15.dp)
        )

        LazyColumn{

        }
    }
}

@Composable
fun SpeciesSection(){
    Column{
        Text(
            text="Choose a Race",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(15.dp)
        )

        LazyColumn{

        }
    }
}

@Composable
fun AbilitySection(){
    Column{
        Text(
            text="Choose your Ability Scores",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(15.dp)
        )

        LazyColumn{

        }
    }
}

@Composable
fun EquipmentSection(){
    Column{
        Text(
            text="Choose your Equipment",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(15.dp)
        )

        LazyColumn{

        }
    }
}