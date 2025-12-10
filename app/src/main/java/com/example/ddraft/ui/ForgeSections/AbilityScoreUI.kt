package com.example.ddraft.ui.ForgeSections

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ddraft.ui.ForgeSections.common.NextStepButton
import com.example.ddraft.viewModels.ForgeViewModel
import com.example.ddraft.viewModels.SharedVM

@Composable
fun AbilityScoresSection(forgeVM: ForgeViewModel, sharedVM: SharedVM) {
    Column {
        Text(
            text = "Ability Scores (Point Buy)",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(15.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(sharedVM.midColor.value)
                .padding(12.dp)
        ) {
            Text(
                text = "Points left: ${forgeVM.pointsLeft.value}",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = if (forgeVM.pointsLeft.value == 0) Color.Green else Color.White
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .padding(horizontal = 15.dp)
                .padding(bottom=(100.dp))
        ) {
            // STR
            item { AbilityScoreInput("STR", forgeVM.strScore.value, forgeVM::onStrChange, forgeVM, sharedVM) }
            item { AbilityScoreInput("DEX", forgeVM.dexScore.value, forgeVM::onDexChange, forgeVM, sharedVM) }
            item { AbilityScoreInput("CON", forgeVM.conScore.value, forgeVM::onConChange, forgeVM, sharedVM) }
            item { AbilityScoreInput("INT", forgeVM.intScore.value, forgeVM::onIntChange, forgeVM, sharedVM) }
            item { AbilityScoreInput("WIS", forgeVM.wisScore.value, forgeVM::onWisChange, forgeVM, sharedVM) }
            item { AbilityScoreInput("CHA", forgeVM.chaScore.value, forgeVM::onChaChange, forgeVM, sharedVM) }
            item{ NextStepButton(forgeVM, sharedVM) }
        }
    }
}

@Composable
private fun AbilityScoreInput(
    label: String,
    score: Int,
    onScoreChange: (Int) -> Unit,
    forgeVM: ForgeViewModel,
    sharedVM: SharedVM
) {
    val modifierText = (score - 10) / 2

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(sharedVM.midColor.value)
            .padding(16.dp)
    ) {
        // Header with current score
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = label, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
            Text(text = "$score ($modifierText)", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = sharedVM.brightColor.value)
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Scrollable score circles 8-15
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            items(8) { index ->
                val value = 8 + index  // 8,9,10,11,12,13,14,15
                NumberCircle(
                    value = value,
                    isSelected = score == value,
                    onClick = { onScoreChange(value) },
                    sharedVM = sharedVM
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text("Cost: ${pointBuyCost(score)} pts", fontSize = 14.sp, color = Color.Gray)
    }
}

@Composable
private fun NumberCircle(
    value: Int,
    isSelected: Boolean,
    onClick: () -> Unit,
    sharedVM: SharedVM
) {
    Box(
        modifier = Modifier
            .size(if (isSelected) 60.dp else 56.dp)
            .clip(CircleShape)
            .background(
                if (isSelected) sharedVM.brightColor.value
                else sharedVM.darkColor.value
            )
            .clickable { onClick() }
            .padding(if (isSelected) 4.dp else 2.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = value.toString(),  // SHOWS SCORE 8-15
            fontSize = if (isSelected) 22.sp else 20.sp,
            fontWeight = FontWeight.Bold,
            color = sharedVM.darkColor.value
        )
    }
}


private fun pointBuyCost(score: Int): Int {
    return when(score) {
        9 -> 1
        10 -> 2
        11 -> 3
        12 -> 4
        13 -> 5
        14 -> 7
        15 -> 9
        else -> 0
    }
}
