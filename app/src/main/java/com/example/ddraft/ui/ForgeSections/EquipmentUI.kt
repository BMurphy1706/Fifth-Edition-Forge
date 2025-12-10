package com.example.ddraft.ui.ForgeSections

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ddraft.api.responses.ApiListItem
import com.example.ddraft.viewModels.ForgeViewModel
import com.example.ddraft.viewModels.SharedVM
import kotlinx.coroutines.delay

@Composable
fun EquipmentSection(forgeVM: ForgeViewModel, sharedVM: SharedVM, nc: NavController) {
    Column {
        Text(
            text = "Equipment",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(15.dp)
        )

        // Categories Dropdown
        EquipmentCategorySelector(forgeVM, sharedVM)

        Spacer(modifier = Modifier.height(16.dp))

        // Equipment List
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .padding(horizontal = 15.dp)
                .fillMaxHeight(0.6F)
        ){
            val itemsList = forgeVM.catList.value
            if (itemsList.isNotEmpty()) {
                items(itemsList.size) { index ->
                    EquipmentItemCard(itemsList[index], forgeVM, sharedVM)
                }
            } else {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(sharedVM.midColor.value)
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = if (forgeVM.catChoice.value == null)
                                "Select a category first"
                            else "No items in this category",
                            color = Color.Gray,
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
        ConfirmButton(forgeVM, sharedVM, nc)
    }
}

@Composable
private fun EquipmentCategorySelector(forgeVM: ForgeViewModel, sharedVM: SharedVM) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(sharedVM.midColor.value)
            .clickable { expanded = !expanded }
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = forgeVM.catChoice.value?.name ?: "All Categories",
            fontSize = 18.sp,
            color = Color.White,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = if (expanded) "▲" else "▼",
            fontSize = 24.sp,
            color = sharedVM.brightColor.value
        )
    }

    AnimatedVisibility(visible = expanded) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .background(sharedVM.darkColor.value.copy(alpha = 0.9f))
                .padding(vertical = 8.dp)
        ) {
            val categories = forgeVM.categories.value
            if (categories.isNotEmpty()) {
                items(categories.size) { index ->
                    val category = categories[index]
                    CategoryChip(
                        category = category,
                        isSelected = forgeVM.catChoice.value?.index == category.index,
                        onClick = {
                            forgeVM.GetItemsList(category.index)
                            forgeVM.updateCatChoice(category)
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
private fun CategoryChip(
    category: ApiListItem,
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
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Text(
            text = category.name,
            fontSize = 16.sp,
            color = if (isSelected) sharedVM.darkColor.value else Color.White,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
        )
    }
}

@Composable
private fun EquipmentItemCard(
    item: ApiListItem,
    forgeVM: ForgeViewModel,
    sharedVM: SharedVM
) {
    var quantity by remember { mutableStateOf(1) }
    val isSelected = forgeVM.itemChoice.value?.index == item.index
    var showToast by remember { mutableStateOf(false) }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(
                    if (isSelected) sharedVM.brightColor.value
                    else sharedVM.midColor.value
                )
                .clickable {
                    forgeVM.GetItem(item.index)
                }
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isSelected) sharedVM.darkColor.value else Color.White
                )
                Text(
                    text = "Tap to view details",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Quantity selector
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(sharedVM.darkColor.value.copy(alpha = 0.3f))
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    IconButton(
                        onClick = { quantity = maxOf(1, quantity - 1) }
                    ) { Text("-", fontSize = 18.sp, color = Color.White) }

                    Text(
                        text = quantity.toString(),
                        fontSize = 16.sp,
                        color = Color.White
                    )

                    IconButton(
                        onClick = { quantity = quantity + 1 }
                    ) { Text("+", fontSize = 18.sp, color = Color.White) }
                }

                Button(
                    onClick = {
                        Log.d("Equipment", "Added $quantity x ${item.name}")
                        forgeVM.addEquipment(item, quantity)
                        showToast = true
                    },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = sharedVM.brightColor.value),
                    modifier = Modifier.height(40.dp)
                ) {
                    Text("Add", fontSize = 14.sp, color = sharedVM.darkColor.value)
                }
            }
        }

        // Equipment added notification
        AnimatedVisibility(
            visible = showToast,
            enter = fadeIn() + slideInVertically(
                initialOffsetY = { it },
                animationSpec = tween(300)
            ),
            exit = fadeOut(animationSpec = tween(300))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        sharedVM.brightColor.value.copy(alpha = 0.92f)
                    )
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Added ${quantity}x ${item.name} to inventory",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = sharedVM.darkColor.value
                )
            }
        }
    }

    LaunchedEffect(showToast) {
        if (showToast) {
            delay(2000)
            showToast = false
        }
    }
}

@Composable
fun ConfirmButton(forgeVM: ForgeViewModel, sharedVM: SharedVM, nc: NavController){
    Button(
        shape = RectangleShape,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = sharedVM.brightColor.value
        ),
        onClick = {
            forgeVM.onClassChoice()
            forgeVM.onBgChoice()
            forgeVM.onRaceChosen()
            sharedVM.addNewCharacter(forgeVM,{nc.navigate("details")})
        }
    ){
        Text(
            text = "Create Character",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = sharedVM.darkColor.value,
        )
    }
}
