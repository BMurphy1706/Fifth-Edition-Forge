package com.example.ddraft.ui.views

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ddraft.R
import com.example.ddraft.models.Character
import com.example.ddraft.ui.util.NavMenu
import com.example.ddraft.ui.theme.LightBlack
import com.example.ddraft.ui.util.standardQuadFromTo
import com.example.ddraft.viewModels.SharedVM

@Composable
fun HomeScreen(sharedVM: SharedVM, nc:NavController){
    val allCharacters by sharedVM.characters
    val searchQuery by sharedVM.searchQuery

    val filteredCharacters = allCharacters.filter {
        it.name.contains(searchQuery, ignoreCase = true) ||
                it.className.contains(searchQuery, ignoreCase = true)
    }

    Box(modifier = Modifier
        .background(LightBlack)
        .fillMaxSize()
    ){
        Column{
            Spacer(Modifier.height(35.dp))
            SearchSection(
                query = searchQuery,
                onQueryChange = sharedVM::updateSearchQuery
            )
            CurrentCharacterSection(sharedVM,nc)
            CharacterSection(filteredCharacters, sharedVM, nc)
        }
        NavMenu(
            sharedVM,
            nc,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun SearchSection(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(15.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier
                .size(80.dp)
        )
        Spacer(Modifier.width(10.dp))
        OutlinedTextField(
            value = query,
            onValueChange = onQueryChange,
            placeholder = { Text("Search by name or class", color = Color.Gray) },
            singleLine = true,
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(8.dp)),
            textStyle = TextStyle(color = Color.White)
        )
    }
}

@Composable
fun CurrentCharacterSection(
    sharedVM: SharedVM,
    nc: NavController
){
    Row(modifier = Modifier
        .padding(15.dp)
        .clip(RoundedCornerShape(10.dp))
        .background(sharedVM.midColor.value)
        .padding(horizontal = 15.dp , vertical = 20.dp)
        .fillMaxWidth()
        .clickable { nc.navigate("details") },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Box(
            modifier = Modifier
                .size(50.dp)
                .background(sharedVM.brightColor.value)
                .padding(5.dp),
            contentAlignment = Alignment.Center
        ){
            Image(
                painter = painterResource(sharedVM.current.value.iconRes),
                contentDescription = "Class Icon",
                Modifier.size(30.dp)
            )
        }
        Column{
            Text(
                text = sharedVM.current.value.name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = "Lv ${sharedVM.current.value.level} ${sharedVM.current.value.raceName} ${sharedVM.current.value.className}",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White
            )
        }
    }
}

@Composable
fun CharacterSection(
    characters: List<Character>,
    sharedVM: SharedVM,
    nc: NavController
){
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text="Featured Characters",
            color = Color.White,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(15.dp)
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(start = 7.5.dp, end = 7.5.dp, bottom = 100.dp),
            modifier=Modifier.fillMaxHeight()
        ){
            items(characters) { character ->
                CharacterBox(character=character, sharedVM, nc)
            }
        }
    }
}

@Composable
fun CharacterBox(
    character: Character,
    sharedVM: SharedVM,
    nc: NavController
){
    BoxWithConstraints(
        modifier = Modifier
            .padding(7.5.dp)
            .aspectRatio(1F)
            .clip(RoundedCornerShape(10.dp))
            .background(character.darkColor)
            .clickable {
                sharedVM.onCurrentChange(character)
                nc.navigate("details")
            }
    ){
        val width = constraints.maxWidth
        val height = constraints.maxHeight

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
            Image(
                painter = painterResource(character.iconRes),
                contentDescription = "Class Icon",
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
                text = "Lv ${character.level} ${character.className}",
                lineHeight = 26.sp,
                color = character.darkColor,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .offset(0.dp,10.dp)
            )
        }
    }
}
