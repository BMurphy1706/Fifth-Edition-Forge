package com.example.ddraft.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ddraft.R
import com.example.ddraft.ui.theme.LightBlack
import com.example.ddraft.viewModels.PortViewModel
import com.example.ddraft.viewModels.SharedVM

@Composable
fun PortScreen(portVM: PortViewModel, sharedVM: SharedVM, nc: NavController){
    Box(modifier = Modifier
        .fillMaxSize()
        .background(LightBlack)
    ){
        Column {
            Spacer(Modifier.height(35.dp))
            PortHeader(sharedVM)
            PortButtons(portVM, sharedVM)
        }
        NavMenu(
            sharedVM,
            nc,
            Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun PortHeader(sharedVM: SharedVM){
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(15.dp),
        verticalArrangement = Arrangement.spacedBy(35.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = "The port",
            fontSize = 40.sp,
            color = sharedVM.midColor.value
        )
        Image(
            painter = painterResource(R.drawable.search),
            contentDescription = "Port Image",
            //modifier = Modifier.height(275.dp),
            modifier = Modifier.fillMaxHeight(0.45F),
            alignment = Alignment.Center,
        )
        Text(
            text = "Whether you are looking to hire some new adventurers or send out some of your own, the port offers every opportunity for both",
            fontSize = 20.sp,
            color = sharedVM.midColor.value,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun PortButtons(portVM: PortViewModel, sharedVM: SharedVM){
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 7.5.dp, end = 7.5.dp, bottom = 100.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        //Import
        Button(
            shape = RectangleShape,
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = sharedVM.brightColor.value
            ),
            onClick = {}
        ){
            Text(
                text = "Import Character",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = sharedVM.darkColor.value,
            )
        }
        //Export
        Button(
            shape = RectangleShape,
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = sharedVM.brightColor.value
            ),
            onClick = {}
        ){
            Text(
                text = "Export Character",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = sharedVM.darkColor.value,
            )
        }
    }
}