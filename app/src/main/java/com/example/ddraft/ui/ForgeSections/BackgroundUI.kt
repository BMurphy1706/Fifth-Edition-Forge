package com.example.ddraft.ui.ForgeSections

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ddraft.api.responses.ApiListItem
import com.example.ddraft.ui.ForgeSections.common.ExpandableSection
import com.example.ddraft.ui.ForgeSections.common.NextStepButton
import com.example.ddraft.ui.util.PlatinumSummary
import com.example.ddraft.viewModels.ForgeViewModel
import com.example.ddraft.viewModels.SharedVM

@Composable
fun BackgroundSection(forgeVM: ForgeViewModel, sharedVM: SharedVM){
    Column{
        AnimatedVisibility(
            visible = !forgeVM.bgVisi.value,
            enter = slideInHorizontally() + fadeIn(),
            exit = slideOutHorizontally() + fadeOut()
        ){
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(35.dp),
                modifier = Modifier.padding(bottom = 100.dp)
            ) {
                item { BackgroundSelection(forgeVM, sharedVM) }
                item { AlignmentSelection(forgeVM, sharedVM) }
                item { CurrencySection(forgeVM, sharedVM) }
                item { NextStepButton(forgeVM, sharedVM) }
            }
        }
        AnimatedVisibility(
            visible = forgeVM.bgVisi.value,
            enter = slideInHorizontally() + fadeIn(),
            exit = slideOutHorizontally() + fadeOut()
        ){
            BackgroundDetails(forgeVM, sharedVM)
        }
    }
}

@Composable
private fun BackgroundSelection(forgeVM: ForgeViewModel, sharedVM: SharedVM){
    Column{
        Text(
            text="Choose a Background",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(5.dp)
        )
        Column(Modifier.padding(bottom = 0.dp)){
            forgeVM.backgrounds.value.forEach { bgItem ->
                BGBox(
                    bgItem = bgItem,
                    forgeVM = forgeVM,
                    sharedVM = sharedVM,
                )
            }
        }
    }
}

@Composable
private fun BGBox(
    bgItem: ApiListItem,
    forgeVM: ForgeViewModel,
    sharedVM: SharedVM,
){
    Row(modifier = Modifier
        .padding(15.dp)
        .clip(RoundedCornerShape(10.dp))
        .background(sharedVM.midColor.value)
        .padding(horizontal = 15.dp , vertical = 20.dp)
        .fillMaxWidth()
        .clickable {
            forgeVM.GetBG(bgItem.index)
            Log.d("Background out", bgItem.name)
            forgeVM.bgVisiChange()
        },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Text(bgItem.name)
    }
}

@Composable
private fun BackgroundDetails(forgeVM: ForgeViewModel, sharedVM: SharedVM) {
    val bgChoice = forgeVM.bgChoice.value
    Column {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = bgChoice?.name ?: "Background name",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(15.dp)
            )
        }

        Spacer(Modifier.height(35.dp))

        bgChoice?.desc?.let { descList ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(sharedVM.midColor.value)
                    .padding(16.dp)
            ) {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    items(descList.size) { index ->
                        Text(
                            text = descList[index],
                            fontSize = 16.sp,
                            color = Color.White,
                            lineHeight = 24.sp
                        )
                    }
                }
            }
        }
        Spacer(Modifier.height(35.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 7.5.dp),
            horizontalArrangement = Arrangement.spacedBy(7.5.dp)
        ) {
            Button(
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.weight(1F),
                colors = ButtonDefaults.buttonColors(
                    containerColor = sharedVM.brightColor.value
                ),
                onClick = { forgeVM.bgVisiChange() }
            ) {
                Text(
                    text = "Cancel",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    color = sharedVM.darkColor.value,
                )
            }
            Button(
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.weight(1F),
                colors = ButtonDefaults.buttonColors(
                    containerColor = sharedVM.brightColor.value
                ),
                onClick = {
                    forgeVM.bgVisiChange()
                    forgeVM.onBgChoice()
                }
            ) {
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

@Composable
private fun AlignmentSelection(forgeVM: ForgeViewModel, sharedVM: SharedVM){
    Column{
        Text(
            text="Choose an Alignment",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(15.dp)
        )
        AlignmentDropdown(forgeVM, sharedVM)
    }
}

@Composable
private fun AlignmentDropdown(forgeVM: ForgeViewModel, sharedVM: SharedVM) {
    var expanded by remember { mutableStateOf(false) }
    var selectedAlignment by remember { mutableStateOf("Choose Alignment") }

    val alignments = listOf(
        "Lawful Good", "Neutral Good", "Chaotic Good",
        "Lawful Neutral", "True Neutral", "Chaotic Neutral",
        "Lawful Evil", "Neutral Evil", "Chaotic Evil"
    )

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(sharedVM.brightColor.value)
                .clickable { expanded = !expanded }
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = selectedAlignment,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = sharedVM.darkColor.value
            )
            Text(
                text = if (expanded) "▲" else "▼",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = sharedVM.darkColor.value
            )
        }

        AnimatedVisibility(visible = expanded) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(sharedVM.darkColor.value.copy(alpha = 0.9f))
                    .padding(vertical = 8.dp)
            ) {
                alignments.forEach { alignment ->
                    AlignmentChip(
                        alignment = alignment,
                        isSelected = selectedAlignment == alignment,
                        onClick = {
                            forgeVM.onAlignChange(alignment)
                            selectedAlignment = alignment
                            expanded = false
                        },
                        sharedVM = sharedVM
                    )
                }
            }
        }
    }
}

@Composable
private fun AlignmentChip(
    alignment: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    sharedVM: SharedVM
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(
                if (isSelected) sharedVM.brightColor.value
                else sharedVM.midColor.value
            )
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = alignment,
            fontSize = 16.sp,
            color = if (isSelected) sharedVM.darkColor.value else Color.White,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
        )
    }
}

@Composable
private fun CurrencySection(forgeVM: ForgeViewModel, sharedVM: SharedVM) {
    Text(
        text="Set starting Currency",
        fontSize = 25.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White,
        modifier = Modifier.padding(15.dp)
    )
    ExpandableSection("Currency", sharedVM) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            CurrencyInputField(
                label = "Gold Pieces (gp)",
                value = forgeVM.goldPieces.value,
                onValueChange = { forgeVM.onGoldChange(it) },
                sharedVM = sharedVM
            )

            CurrencyInputField(
                label = "Silver Pieces (sp)",
                value = forgeVM.silverPieces.value,
                onValueChange = { forgeVM.onSilverChange(it) },
                sharedVM = sharedVM
            )

            CurrencyInputField(
                label = "Copper Pieces (cp)",
                value = forgeVM.copperPieces.value,
                onValueChange = { forgeVM.onCopperChange(it) },
                sharedVM = sharedVM
            )

            Spacer(modifier = Modifier.height(16.dp))
            PlatinumSummary(forgeVM, sharedVM)
        }
    }
}

@Composable
private fun CurrencyInputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    sharedVM: SharedVM
) {
    OutlinedTextField(
        value = value,
        onValueChange = { newValue ->
            if (newValue.matches(Regex("\\d*"))) {
                onValueChange(newValue)
            }
        },
        label = { Text(label) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        shape = RoundedCornerShape(10.dp),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = sharedVM.midColor.value,
            focusedContainerColor = sharedVM.brightColor.value,
            unfocusedTextColor = Color.White,
            focusedTextColor = sharedVM.darkColor.value,
            unfocusedLabelColor = Color.White.copy(alpha = 0.7f),
            focusedLabelColor = sharedVM.brightColor.value,
            unfocusedBorderColor = sharedVM.brightColor.value.copy(alpha = 0.5f),
            focusedBorderColor = sharedVM.darkColor.value
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
    )
}
