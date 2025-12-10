package com.example.ddraft.ui.ForgeSections.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.ddraft.viewModels.ForgeViewModel
import com.example.ddraft.viewModels.SharedVM

@Composable
fun NextStepButton(forgeVM: ForgeViewModel, sharedVM: SharedVM){
    Button(
        shape = RectangleShape,
        modifier = Modifier
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = sharedVM.brightColor.value
        ),
        onClick = {
            forgeVM.onClassChoice()
            forgeVM.onStepChange(forgeVM.selectedStep.value+1)
        }
    ){
        Text(
            text = "Next step",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = sharedVM.darkColor.value,
        )
    }
}