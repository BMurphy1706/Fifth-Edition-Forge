package com.example.ddraft.ui.ForgeSections.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ddraft.viewModels.SharedVM

@Composable
fun ExpandableSection(
    title: String,
    sharedVM: SharedVM,
    content: @Composable () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Card(modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.background(sharedVM.brightColor.value)){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded }
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    title,
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
                content()
            }
        }
    }
}