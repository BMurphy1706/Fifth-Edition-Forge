package com.example.ddraft.ui.util

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ddraft.R
import com.example.ddraft.models.NavBarContent
import com.example.ddraft.ui.theme.LightBlack
import com.example.ddraft.ui.theme.OrangeB
import com.example.ddraft.ui.theme.OrangeD
import com.example.ddraft.viewModels.SharedVM

@Composable
fun NavMenu(
    sharedVM: SharedVM,
    nc: NavController,
    modifier: Modifier = Modifier
) {
    val items = sharedVM.navItems.value
    Row(modifier=modifier
        .fillMaxWidth()
        .background(LightBlack)
        .padding(15.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ){
        items.forEachIndexed{index, item ->
            NavBarItem(
                item=item,
                isSelected = index==sharedVM.navIndex.value,
                activeHighlight = sharedVM.brightColor.value,
                activeTextColor = sharedVM.darkColor.value,
                inactiveTextColor = sharedVM.brightColor.value,
                nc = nc
            ){
                sharedVM.onNavIndexChange(index)
            }
        }
    }
}

@Composable
fun NavBarItem(
    item: NavBarContent,
    isSelected: Boolean = false,
    activeHighlight: Color = OrangeB,
    activeTextColor: Color = OrangeD,
    inactiveTextColor: Color = OrangeB,
    nc: NavController,
    onItemClick: () -> Unit
){
    Column(modifier = Modifier
        .clickable{
            onItemClick()
            nc.navigate(item.route)
        },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Box(modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(if(isSelected) activeHighlight else Color.Transparent)
            .padding(10.dp),
            contentAlignment = Alignment.Center
        ){
            Icon(
                painter = painterResource(item.icon),
                contentDescription = item.title,
                tint = (if(isSelected) activeTextColor else inactiveTextColor),
                modifier = Modifier.size(40.dp)
            )
        }
        Text(
            text = item.title,
            color = (if (isSelected) activeTextColor else inactiveTextColor)
        )
    }
}