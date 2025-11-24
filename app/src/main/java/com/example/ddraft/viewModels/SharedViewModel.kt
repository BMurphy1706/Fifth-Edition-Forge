package com.example.ddraft.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.example.ddraft.R
import com.example.ddraft.models.NavBarContent
import com.example.ddraft.models.SRD.character_Elements.Character
import com.example.ddraft.ui.theme.OrangeB
import com.example.ddraft.ui.theme.OrangeD
import com.example.ddraft.ui.theme.OrangeM

class SharedVM: ViewModel(){
    //UI Colouring
    private val _brightColor = mutableStateOf(OrangeB)
    val brightColor: State<Color> = _brightColor

    private val _midColor = mutableStateOf(OrangeM)
    val midColor: State<Color> = _midColor

    private val _darkColor = mutableStateOf(OrangeD)
    val darkColor: State<Color> = _darkColor

    //NavBar
    private val _navItems = mutableStateOf<List<NavBarContent>>(navList())
    val navItems: State<List<NavBarContent>> = _navItems

    private val _navIndex = mutableIntStateOf(0)
    val navIndex: State<Int> = _navIndex

    //Character list
    private val _characters = mutableStateOf<List<Character>>(demoCharacters())
    val characters: State<List<Character>> = _characters

    //Current Character
    private val _current = mutableStateOf<Character>(_characters.value[0])//make a default character with no info
    val current: State<Character> = _current

    //Alteration functions
    fun onCurrentChange(newCurrent: Character){
        _current.value = newCurrent
        _brightColor.value = newCurrent.lightColor
        _midColor.value = newCurrent.mediumColor
        _darkColor.value = newCurrent.darkColor
    }

    fun onNavIndexChange(newIdx: Int){_navIndex.intValue=newIdx}



    //Demo data functions
    fun demoCharacters(): List<Character> {
        val testCharacters = listOf(
            Character(
                name = "Amber Knight",
                iconId = R.drawable.search,
                lightColor = Color(0xFFFFB550), // Light orange tone
                mediumColor = Color(0xFFFF7A30), // Mid orange
                darkColor = Color(0xFFCC4A1F) // Darker, deeper variant
            ),
            Character(
                name = "Verdant Sage",
                iconId = R.drawable.search,
                lightColor = Color(0xFF81D97B),  // Light green
                mediumColor = Color(0xFF4CAF50), // Medium green
                darkColor = Color(0xFF2E7D32)   // Dark green
            ),
            Character(
                name = "Azure Whisper",
                iconId = R.drawable.search,
                lightColor = Color(0xFF6EC6FF),  // Light blue
                mediumColor = Color(0xFF2196F3), // Medium blue
                darkColor = Color(0xFF0B5BC5)   // Dark blue
            ),
            Character(
                name = "Crimson Dawn",
                iconId = R.drawable.search,
                lightColor = Color(0xFFFF867C),  // Light red-orange
                mediumColor = Color(0xFFE53935), // Mid red
                darkColor = Color(0xFFB71C1C)   // Darker red
            ),
            Character(
                name = "Violet Dreamer",
                iconId = R.drawable.search,
                lightColor = Color(0xFFD1A3FF),  // Light purple
                mediumColor = Color(0xFF9C27B0), // Medium purple
                darkColor = Color(0xFF6A0080)   // Deep purple
            )
        )
        return testCharacters
    }

    fun navList():List<NavBarContent> {
        val testNav = listOf(
            NavBarContent(
                title = "Home",
                iconId = 0,
                "home"
            ),

            NavBarContent(
                title = "Forge",
                iconId = 0,
                "forge"
            ),

            NavBarContent(
                title = "Simulator",
                iconId = 0,
                "home"
            ),

            NavBarContent(
                title = "Port",
                iconId = 0,
                "port"
            )
        )
        return testNav
    }
}