package com.example.ddraft.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ddraft.ui.theme.LightBlack
import com.example.ddraft.ui.util.NavMenu
import com.example.ddraft.viewModels.DiceViewModel
import com.example.ddraft.viewModels.RollResult
import com.example.ddraft.viewModels.SharedVM

@Composable
fun DiceScreen(dVM: DiceViewModel, sharedVM: SharedVM, nc: NavController){
    val selectedDie by dVM.selectedDie
    val rollMode by dVM.rollMode
    val rollResult1 by dVM.rollResult1
    val rollResult2 by dVM.rollResult2
    val finalResult by dVM.finalResult
    val rollHistory by dVM.rollHistory

    Box(modifier = Modifier
        .background(LightBlack)
        .fillMaxSize()
    ){
        Column(modifier = Modifier
            .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Spacer(Modifier.height(35.dp))

            DiceSelectorSection(
                selectedDie = selectedDie,
                onDieSelected = dVM::setDie,
                brightColor = sharedVM.brightColor
            )

            Spacer(Modifier.height(16.dp))

            RollControlsSection(
                rollMode = rollMode,
                selectedDie = selectedDie,
                onModeSelected = dVM::setRollMode,
                onRoll = dVM::rollDice,
                brightColor = sharedVM.brightColor
            )

            Spacer(Modifier.height(16.dp))

            if (finalResult > 0) {
                DiceResultsSection(
                    rollResult1 = rollResult1,
                    rollResult2 = rollResult2,
                    finalResult = finalResult,
                    brightColor = sharedVM.brightColor
                )
            }

            Spacer(Modifier.height(16.dp))

            RollHistorySection(
                rollHistory = rollHistory,
                onClear = dVM::clearHistory,
                brightColor = sharedVM.brightColor
            )
        }
        NavMenu(
            sharedVM,
            nc,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
private fun DiceSelectorSection(
    selectedDie: Int,
    onDieSelected: (Int) -> Unit,
    brightColor: State<Color>
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Select Die",
            style = MaterialTheme.typography.headlineSmall,
            color = brightColor.value
        )
        Spacer(Modifier.height(8.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            DiceButton(4, selectedDie, onDieSelected, brightColor)
            DiceButton(6, selectedDie, onDieSelected, brightColor)
            DiceButton(8, selectedDie, onDieSelected, brightColor)
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            DiceButton(10, selectedDie, onDieSelected, brightColor)
            DiceButton(12, selectedDie, onDieSelected, brightColor)
            DiceButton(20, selectedDie, onDieSelected, brightColor)
        }
    }
}

@Composable
private fun DiceButton(
    sides: Int,
    selectedDie: Int,
    onDieSelected: (Int) -> Unit,
    brightColor: State<Color>
) {
    val isSelected = selectedDie == sides
    Button(
        onClick = { onDieSelected(sides) },
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) brightColor.value else MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Text(
            text = "d$sides",
            fontSize = 18.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            color = if (isSelected) Color.White else brightColor.value
        )
    }
}

@Composable
private fun RollControlsSection(
    rollMode: DiceViewModel.RollMode,
    selectedDie: Int,
    onModeSelected: (DiceViewModel.RollMode) -> Unit,
    onRoll: () -> Unit,
    brightColor: State<Color>
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Roll Mode",
            style = MaterialTheme.typography.headlineSmall,
            color = brightColor.value
        )
        Spacer(Modifier.height(8.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            RollModeButton("Normal", DiceViewModel.RollMode.NORMAL, rollMode, onModeSelected, brightColor)
            RollModeButton("Advantage", DiceViewModel.RollMode.ADVANTAGE, rollMode, onModeSelected, brightColor)
            RollModeButton("Disadvantage", DiceViewModel.RollMode.DISADVANTAGE, rollMode, onModeSelected, brightColor)
        }
        Spacer(Modifier.height(16.dp))
        Button(
            onClick = onRoll,
            colors = ButtonDefaults.buttonColors(containerColor = brightColor.value),
            modifier = Modifier.height(56.dp)
        ) {
            Text(
                text = when (rollMode) {
                    DiceViewModel.RollMode.NORMAL -> "Roll d$selectedDie"
                    DiceViewModel.RollMode.ADVANTAGE -> "Roll d$selectedDie (Adv)"
                    DiceViewModel.RollMode.DISADVANTAGE -> "Roll d$selectedDie (Dis)"
                },
                fontSize = 18.sp,
                color = Color.White
            )
        }
    }
}

@Composable
private fun RollModeButton(
    label: String,
    mode: DiceViewModel.RollMode,
    selectedMode: DiceViewModel.RollMode,
    onModeSelected: (DiceViewModel.RollMode) -> Unit,
    brightColor: State<Color>
) {
    val isSelected = selectedMode == mode
    Button(
        onClick = { onModeSelected(mode) },
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) brightColor.value else MaterialTheme.colorScheme.surfaceVariant
        )
    ){
        Text(
            text = label,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            color = if (isSelected) Color.White else brightColor.value
        )
    }
}

@Composable
private fun DiceResultsSection(
    rollResult1: Int,
    rollResult2: Int,
    finalResult: Int,
    brightColor: State<Color>
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Results",
            style = MaterialTheme.typography.headlineSmall,
            color = brightColor.value
        )
        Spacer(Modifier.height(12.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            DiceFace(rollResult1, brightColor)
            if (rollResult2 > 0) {
                DiceFace(rollResult2, brightColor)
            }
        }
        Spacer(Modifier.height(8.dp))
        Text(
            text = "FINAL: $finalResult",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = brightColor.value,
            fontSize = 28.sp
        )
    }
}

@Composable
private fun DiceFace(result: Int, brightColor: State<Color>) {
    Box(
        modifier = Modifier
            .size(52.dp)
            .clip(CircleShape)
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = result.toString(),
            fontSize = 22.sp,
            fontWeight = FontWeight.Black,
            color = brightColor.value
        )
    }
}

@Composable
private fun RollHistorySection(
    rollHistory: List<RollResult>,
    onClear: () -> Unit,
    brightColor: State<Color>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                text = "Recent Rolls",
                style = MaterialTheme.typography.headlineSmall,
                color = brightColor.value,
                modifier = Modifier.padding(start = 16.dp, top = 8.dp)
            )
            TextButton(
                onClick = onClear,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text("Clear History", color = brightColor.value)
            }
        }
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                bottom = 8.dp
            ),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(rollHistory) { roll ->
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${roll.mode}: d${roll.die} [${roll.rolls.joinToString(", ")}]",
                            color = brightColor.value
                        )
                        Text(
                            text = roll.result.toString(),
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = brightColor.value
                        )
                    }
                }
            }
        }
    }
}
