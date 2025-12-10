package com.example.ddraft.ui.views

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.ddraft.R
import com.example.ddraft.models.Character
import com.example.ddraft.ui.theme.LightBlack
import com.example.ddraft.ui.util.NavMenu
import com.example.ddraft.viewModels.PortViewModel
import com.example.ddraft.viewModels.SharedVM

@Composable
fun PortScreen(portVM: PortViewModel, sharedVM: SharedVM, navController: NavController) {
    val context = LocalContext.current
    val currentCharacter by sharedVM.current
    val statusMessage by portVM.statusMessage.collectAsStateWithLifecycle()

    val exportLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CreateDocument("application/json")
    ) { uri: Uri? ->
        uri?.let { portVM.exportCharacter(context, it, currentCharacter?:return@let) }
    }

    val importLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            portVM.importCharacter(context, it) { importedChar ->
                sharedVM.importCharacter(importedChar)
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LightBlack)
    ) {
        Column {
            Spacer(Modifier.height(35.dp))
            PortHeader(sharedVM)

            if (statusMessage.isNotEmpty()) {
                Text(
                    text = statusMessage,
                    color = sharedVM.brightColor.value,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(16.dp),
                    textAlign = TextAlign.Center
                )
            }

            PortButtons(
                sharedVM = sharedVM,
                currentCharacter = currentCharacter,
                onExport = { exportLauncher.launch("${currentCharacter?.name ?: "Character"}.json") },
                onImport = { importLauncher.launch("application/json") },
                onClear = { portVM.clearMessages() }
            )
        }

        NavMenu(
            sharedVM = sharedVM,
            nc = navController,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
private fun PortHeader(sharedVM: SharedVM) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
        verticalArrangement = Arrangement.spacedBy(35.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "The Port",
            fontSize = 40.sp,
            color = sharedVM.midColor.value
        )
        Image(
            painter = painterResource(R.drawable.port),
            contentDescription = "Port Image",
            modifier = Modifier.fillMaxHeight(0.4f),
            alignment = Alignment.Center,
        )
        Text(
            text = "Recruit new adventurers or send off your own—everything happens here at the port.",
            fontSize = 20.sp,
            color = sharedVM.midColor.value,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun PortButtons(
    sharedVM: SharedVM,
    currentCharacter: Character?,
    onExport: () -> Unit,
    onImport: () -> Unit,
    onClear: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 7.5.dp, vertical = 20.dp)
            .padding(bottom = 100.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            shape = RectangleShape,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = sharedVM.brightColor.value),
            onClick = onExport
        ) {
            Text(
                text = "Export ${currentCharacter?.name ?: "Character"}",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = sharedVM.darkColor.value,
            )
        }

        Button(
            shape = RectangleShape,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = sharedVM.brightColor.value),
            onClick = onImport
        ) {
            Text(
                text = "Import Character",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = sharedVM.darkColor.value,
            )
        }

        Button(
            shape = RectangleShape,
            modifier = Modifier.fillMaxWidth(0.5f),
            colors = ButtonDefaults.buttonColors(containerColor = sharedVM.midColor.value),
            onClick = onClear
        ) {
            Text(
                text = "Clear",
                fontSize = 18.sp,
                color = sharedVM.brightColor.value
            )
        }
    }
}