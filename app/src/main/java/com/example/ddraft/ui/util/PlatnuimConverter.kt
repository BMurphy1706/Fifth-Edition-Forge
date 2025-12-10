package com.example.ddraft.ui.util

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ddraft.viewModels.ForgeViewModel
import com.example.ddraft.viewModels.SharedVM

class PlatnuimConverter@Composable
fun PlatinumSummary(forgeVM: ForgeViewModel, sharedVM: SharedVM) {
    val totalGpEquivalent by remember {
        derivedStateOf {
            val gp = forgeVM.goldPieces.value.toIntOrNull() ?: 0
            val sp = forgeVM.silverPieces.value.toIntOrNull() ?: 0
            val cp = forgeVM.copperPieces.value.toIntOrNull() ?: 0
            val platinumEquivalent = (gp + sp/10f + cp/100f) / 10f
            platinumEquivalent.toInt()
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(sharedVM.midColor.value)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Platinum Equivalent",
            fontSize = 16.sp,
            color = Color.White,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = "${totalGpEquivalent} pp",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = sharedVM.brightColor.value
        )
    }
}