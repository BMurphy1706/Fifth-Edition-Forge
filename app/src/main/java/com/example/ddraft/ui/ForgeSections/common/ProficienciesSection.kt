package com.example.ddraft.ui.ForgeSections.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ddraft.models.SRD.character_Elements.common.Proficiency
import com.example.ddraft.viewModels.SharedVM

@Composable
fun ProficienciesSection(
    proficiencies: List<Proficiency>,
    sharedVM: SharedVM,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(proficiencies.size) { proficiency ->
            ProficiencyChip(proficiencies[proficiency], sharedVM)
        }
    }
}

@Composable
private fun ProficiencyChip(proficiency: Proficiency, sharedVM: SharedVM) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = sharedVM.darkColor.value,
        modifier = Modifier
    ) {
        Text(
            text = proficiency.name.take(20).trimEnd(),  // Truncate long names
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            maxLines = 1,
            color = sharedVM.brightColor.value
        )
    }
}