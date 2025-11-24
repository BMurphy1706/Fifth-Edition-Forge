package com.example.ddraft.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ddraft.R
import com.example.ddraft.models.SRD.character_Elements.Character
import com.example.ddraft.ui.theme.LightBlack
import com.example.ddraft.ui.util.standardQuadFromTo
import com.example.ddraft.viewModels.SharedVM

@Composable
fun HomeScreen(sharedVM: SharedVM, nc:NavController){
    Box(modifier = Modifier
        .background(LightBlack)
        .fillMaxSize()
    ){
        Column{
            Spacer(Modifier.height(35.dp))
            SearchSection()
            CurrentCharacterSection(sharedVM.current.value)
            CharacterSection(sharedVM)
        }
        NavMenu(
            sharedVM,
            nc,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun SearchSection(){
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(15.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Text("This is where the \nlogo goes", color = Color.White)
        Icon(
            painter = painterResource(R.drawable.search),
            contentDescription = "Search",
            tint = Color.Unspecified,
            modifier = Modifier.height(24.dp)
        )
    }
}

@Composable
fun CurrentCharacterSection(
    character: Character
){
    Row(modifier = Modifier
        .padding(15.dp)
        .clip(RoundedCornerShape(10.dp))
        .background(character.mediumColor)
        .padding(horizontal = 15.dp , vertical = 20.dp)
        .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Box(
            modifier = Modifier
                .size(50.dp)
                .background(character.lightColor)
                .padding(5.dp),
            contentAlignment = Alignment.Center
        ){
            Icon(
                painter = painterResource(R.drawable.search),
                contentDescription = "Class Icon",
                Modifier.size(30.dp)
            )
        }
        Text("This is where last viewed \ncharacter info goes")
    }
}

@Composable
fun CharacterSection(sharedVM: SharedVM){
    val characters = sharedVM.characters.value
    Column(modifier = Modifier.fillMaxWidth())
    {
        Text(
            text="Featured Characters",
            color = Color.White,
            //style=
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(15.dp)
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(start = 7.5.dp, end = 7.5.dp, bottom = 100.dp),
            modifier=Modifier.fillMaxHeight()
        ){
            items(characters.size){
                CharacterBox(character=characters[it], sharedVM)
            }
        }
    }
}

@Composable
fun CharacterBox(
    character: Character,
    sharedVM: SharedVM
){
    BoxWithConstraints(
        modifier = Modifier
            .padding(7.5.dp)
            .aspectRatio(1F) //Makes width = height of GridCell (Makes it a square)
            .clip(RoundedCornerShape(10.dp))
            .background(character.darkColor)
            .clickable {sharedVM.onCurrentChange(character)}
    ){
        //Colour waves
        val width = constraints.maxWidth
        val height = constraints.maxHeight

        //Medium coloured path
        val mediumColoredPoint1 = Offset(0f, height * 0.3f)
        val mediumColoredPoint2 = Offset(width * 0.1f, height * 0.35f)
        val mediumColoredPoint3 = Offset(width * 0.4f, height * 0.05f)
        val mediumColoredPoint4 = Offset(width * 0.75f, height * 0.7f)
        val mediumColoredPoint5 = Offset(width * 1.4f, -height.toFloat())

        val mediumColoredPath = Path().apply {
            moveTo(mediumColoredPoint1.x, mediumColoredPoint1.y)
            standardQuadFromTo(mediumColoredPoint1, mediumColoredPoint2)
            standardQuadFromTo(mediumColoredPoint2, mediumColoredPoint3)
            standardQuadFromTo(mediumColoredPoint3, mediumColoredPoint4)
            standardQuadFromTo(mediumColoredPoint4, mediumColoredPoint5)
            lineTo(width.toFloat() + 100f, height.toFloat() + 100f)
            lineTo(-100f, height.toFloat() + 100f)
            close()
        }

        // Light colored path
        val lightPoint1 = Offset(0f, height * 0.35f)
        val lightPoint2 = Offset(width * 0.1f, height * 0.4f)
        val lightPoint3 = Offset(width * 0.3f, height * 0.35f)
        val lightPoint4 = Offset(width * 0.65f, height.toFloat())
        val lightPoint5 = Offset(width * 1.4f, -height.toFloat() / 3f)

        val lightColoredPath = Path().apply {
            moveTo(lightPoint1.x, lightPoint1.y)
            standardQuadFromTo(lightPoint1, lightPoint2)
            standardQuadFromTo(lightPoint2, lightPoint3)
            standardQuadFromTo(lightPoint3, lightPoint4)
            standardQuadFromTo(lightPoint4, lightPoint5)
            lineTo(width.toFloat() + 100f, height.toFloat() + 100f)
            lineTo(-100f, height.toFloat() + 100f)
            close()
        }
        Canvas(
            modifier = Modifier
                .fillMaxSize()
        ) {
            drawPath(
                path = mediumColoredPath,
                color = character.mediumColor
            )
            drawPath(
                path = lightColoredPath,
                color = character.lightColor
            )
        }
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
        ){
            Icon(
                painter = painterResource(R.drawable.search),
                contentDescription = "Class Icon",
                tint = Color.White,
                modifier = Modifier
                    .size(60.dp)
                    .align(Alignment.Center)
                    .offset(0.dp, -10.dp)
            )
            Text(
                text = character.name,
                lineHeight = 26.sp,
                color = character.lightColor,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .offset(0.dp, -10.dp)
            )
            Text(
                text = "Class and lv info here",
                lineHeight = 26.sp,
                color = character.darkColor,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .offset(0.dp,10.dp)
            )
        }
    }
}