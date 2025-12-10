package com.example.ddraft.viewModels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ddraft.R
import com.example.ddraft.db.CharacterDAO
import com.example.ddraft.models.Character
import com.example.ddraft.models.EquipmentItem
import com.example.ddraft.models.NavBarContent
import com.example.ddraft.ui.theme.ClassTheme
import com.example.ddraft.ui.theme.OrangeB
import com.example.ddraft.ui.theme.OrangeD
import com.example.ddraft.ui.theme.OrangeM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedVM @Inject constructor(
    private val characterDao: CharacterDAO
) : ViewModel() {
    private val _brightColor = mutableStateOf(OrangeB)
    val brightColor: State<Color> = _brightColor

    private val _midColor = mutableStateOf(OrangeM)
    val midColor: State<Color> = _midColor

    private val _darkColor = mutableStateOf(OrangeD)
    val darkColor: State<Color> = _darkColor

    private val _navItems = mutableStateOf<List<NavBarContent>>(navList())
    val navItems: State<List<NavBarContent>> = _navItems

    private val _navIndex = mutableIntStateOf(0)
    val navIndex: State<Int> = _navIndex

    private val _characters = mutableStateOf<List<Character>>(emptyList())
    val characters: State<List<Character>> = _characters

    private val _current = mutableStateOf<Character?>(null)
    val current: State<Character?> = _current

    private val _searchQuery = mutableStateOf("")
    val searchQuery: State<String> = _searchQuery

    init {
        viewModelScope.launch {
            characterDao.getCharacters().collectLatest { list ->
                _characters.value = list
                if (_current.value == null && list.isNotEmpty()) {
                    _current.value = list.first()
                    updateColorsFromClass(list.first().className)
                }
            }
        }
    }

    fun onCurrentChange(newCurrent: Character) {
        _current.value = newCurrent
        updateColorsFromClass(newCurrent.className)
    }

    fun updateSearchQuery(newQuery: String) {
        _searchQuery.value = newQuery
    }

    fun addNewCharacter(forgeVM: ForgeViewModel, onAdded: (() -> Unit)? = null) {
        val classTheme = ClassTheme.getTheme(forgeVM.classChoice.value?.name ?: "")

        val equipmentList = forgeVM.selectedEquipment.value.map { (item, quantity) ->
            EquipmentItem(item.index, item.name, item.url, quantity)
        }

        val newChar = Character(
            name = forgeVM.name.value,
            raceName = forgeVM.raceChosen.value?.name ?: "",
            className = forgeVM.classChoice.value?.name ?: "",
            backgroundName = forgeVM.bgChoice.value?.name ?: "",
            alignment = forgeVM.align.value,
            level = forgeVM.lv.value,
            strength = forgeVM.strScore.value,
            dexterity = forgeVM.dexScore.value,
            constitution = forgeVM.conScore.value,
            intelligence = forgeVM.intScore.value,
            wisdom = forgeVM.wisScore.value,
            charisma = forgeVM.chaScore.value,
            goldPieces = forgeVM.goldPieces.value,
            silverPieces = forgeVM.silverPieces.value,
            copperPieces = forgeVM.copperPieces.value,
            selectedEquipment = equipmentList,
            iconRes = classTheme?.iconRes ?: R.drawable.search,
            lightColor = classTheme?.light ?: OrangeB,
            mediumColor = classTheme?.medium ?: OrangeM,
            darkColor = classTheme?.dark ?: OrangeD
        )

        viewModelScope.launch {
            characterDao.insertCharacter(newChar)
            onCurrentChange(newChar)
            onAdded?.invoke()
        }
        Log.d("Char created", "${newChar.name} with ${newChar.selectedEquipment.size} items")
    }

    fun deleteCharacter(character: Character) {
        viewModelScope.launch {
            characterDao.deleteCharacter(character)
        }
    }

    fun importCharacter(importedChar: Character) {
        viewModelScope.launch {
            characterDao.insertCharacter(importedChar)
        }
        onCurrentChange(importedChar)
        Log.d("Char imported", "${importedChar.name} with ${importedChar.selectedEquipment.size} items")
    }

    fun onNavIndexChange(newIdx: Int) { _navIndex.intValue = newIdx }

    fun updateColorsFromClass(className: String?) {
        ClassTheme.getTheme(className)?.let { theme ->
            _brightColor.value = theme.light
            _midColor.value = theme.medium
            _darkColor.value = theme.dark
        }
    }

    private fun navList(): List<NavBarContent> = listOf(
        NavBarContent(title = "Tavern", icon = R.drawable.tavern, "home"),
        NavBarContent(title = "Forge", icon = R.drawable.sns, "forge"),
        NavBarContent(title = "Dice", icon = R.drawable.d20, "dice"),
        NavBarContent(title = "Port", icon = R.drawable.boat, "port")
    )
}
