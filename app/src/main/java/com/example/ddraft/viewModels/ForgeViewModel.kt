package com.example.ddraft.viewModels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ddraft.api.DataRepository
import com.example.ddraft.api.responses.ApiListItem
import com.example.ddraft.models.SRD.character_Elements.Background
import com.example.ddraft.models.SRD.character_Elements.Race
import com.example.ddraft.models.SRD.class_Elements.Class
import com.example.ddraft.models.SRD.item_Elements.Item
import com.example.ddraft.models.SRD.spell_Elements.Spell
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgeViewModel @Inject constructor(
    private val dataRepo: DataRepository
):ViewModel(){
    //UI
    private val _selectedStep = mutableIntStateOf(0)
    val selectedStep: State<Int> = _selectedStep
    fun onStepChange(newStep: Int){_selectedStep.intValue = newStep}

    private val _classDetailsVisi = mutableStateOf(false)
    val classDetailsVisi: State<Boolean> = _classDetailsVisi
    fun classVisiChange(){_classDetailsVisi.value = !_classDetailsVisi.value }

    private val _bgVisi = mutableStateOf(false)
    val bgVisi: State<Boolean> = _bgVisi
    fun bgVisiChange(){_bgVisi.value = !_bgVisi.value }

    private val _speciesVisi = mutableStateOf(false)
    val speciesVisi: State<Boolean> = _speciesVisi
    fun speciesVisiChange(){_speciesVisi.value = !_speciesVisi.value}

    //Character values
    private val _name = mutableStateOf("My New Character")
    val name: State<String> = _name
    fun onNameChange(newName: String){_name.value = newName}

    private val _classChosen = mutableStateOf<Class?>(null)
    val classChosen: State<Class?> = _classChosen
    fun onClassChoice(){
        _classChosen.value = _classChoice.value
        Log.d("Class Chosen", "${_classChosen.value}")
    }

    private val _lv = mutableIntStateOf(1)
    val lv: State<Int> = _lv
    fun onLvChange(newLv:Int){_lv.value = newLv}

    private val _bg = mutableStateOf<Background?>(value = null)
    val bg:State<Background?> = _bg
    fun onBgChoice(){_bg.value = _bgChoice.value}

    private val _align = mutableStateOf("")
    val align:State<String> = _align
    fun onAlignChange(newAl:String){_align.value = newAl}

    private val _goldPieces = mutableStateOf("0")
    val goldPieces: State<String> = _goldPieces
    fun onGoldChange(newValue: String) { _goldPieces.value = newValue }

    private val _silverPieces = mutableStateOf("0")
    val silverPieces: State<String> = _silverPieces
    fun onSilverChange(newValue: String) { _silverPieces.value = newValue }

    private val _copperPieces = mutableStateOf("0")
    val copperPieces: State<String> = _copperPieces
    fun onCopperChange(newValue: String) { _copperPieces.value = newValue }

    private val _raceChosen = mutableStateOf<Race?>(value = null)
    val raceChosen: State<Race?> = _raceChosen
    fun onRaceChosen(){_raceChosen.value = _raceChoice.value}

    // Point buy states
    private val _pointsLeft = mutableIntStateOf(27)
    val pointsLeft: State<Int> = _pointsLeft

    private val _strScore = mutableIntStateOf(8)
    val strScore: State<Int> = _strScore
    fun onStrChange(newScore: Int) { updateScore("str", newScore) }

    private val _dexScore = mutableIntStateOf(8)
    val dexScore: State<Int> = _dexScore
    fun onDexChange(newScore: Int) { updateScore("dex", newScore) }

    private val _conScore = mutableIntStateOf(8)
    val conScore: State<Int> = _conScore
    fun onConChange(newScore: Int) { updateScore("con", newScore) }

    private val _intScore = mutableIntStateOf(8)
    val intScore: State<Int> = _intScore
    fun onIntChange(newScore: Int) { updateScore("int", newScore) }

    private val _wisScore = mutableIntStateOf(8)
    val wisScore: State<Int> = _wisScore
    fun onWisChange(newScore: Int) { updateScore("wis", newScore) }

    private val _chaScore = mutableIntStateOf(8)
    val chaScore: State<Int> = _chaScore
    fun onChaChange(newScore: Int) { updateScore("cha", newScore) }

    private fun updateScore(stat: String, newScore: Int) {
        val oldScore = when(stat) {
            "str" -> _strScore.intValue
            "dex" -> _dexScore.intValue
            "con" -> _conScore.intValue
            "int" -> _intScore.intValue
            "wis" -> _wisScore.intValue
            "cha" -> _chaScore.intValue
            else -> 8
        }

        val costDiff = pointBuyCost(newScore) - pointBuyCost(oldScore)
        if (_pointsLeft.intValue >= costDiff && newScore <= 15) {
            when(stat) {
                "str" -> _strScore.intValue = newScore
                "dex" -> _dexScore.intValue = newScore
                "con" -> _conScore.intValue = newScore
                "int" -> _intScore.intValue = newScore
                "wis" -> _wisScore.intValue = newScore
                "cha" -> _chaScore.intValue = newScore
            }
            _pointsLeft.intValue -= costDiff
        }
    }

    private fun pointBuyCost(score: Int): Int {
        return when(score) {
            9 -> 1
            10 -> 2
            11 -> 3
            12 -> 4
            13 -> 5
            14 -> 7
            15 -> 9
            else -> 0
        }
    }

    private val _selectedEquipment = mutableStateOf<List<Pair<ApiListItem, Int>>>(emptyList())
    val selectedEquipment: State<List<Pair<ApiListItem, Int>>> = _selectedEquipment

    fun addEquipment(item: ApiListItem, quantity: Int) {
        val current = _selectedEquipment.value.toMutableList()
        val existing = current.find { it.first.index == item.index }

        if (existing != null) {
            // Item exists → increase quantity
            current.remove(existing)
            current.add(Pair(item, existing.second + quantity))
        } else {
            // New item → add with quantity
            current.add(Pair(item, quantity))
        }
        _selectedEquipment.value = current
    }


    //Api values
    private val _classList = mutableStateOf<List<ApiListItem>>(emptyList())
    val classList: State<List<ApiListItem>> = _classList

    private val _classChoice = mutableStateOf<Class?>(value = null)
    val classChoice: State<Class?> = _classChoice

    private val _raceList = mutableStateOf<List<ApiListItem>>(emptyList())
    val raceList: State<List<ApiListItem>> = _raceList

    private val _raceChoice = mutableStateOf<Race?>(value = null)
    val raceChoice: State<Race?> = _raceChoice

    private val _backgrounds = mutableStateOf<List<ApiListItem>>(emptyList())
    val backgrounds: State<List<ApiListItem>> = _backgrounds

    private val _bgChoice = mutableStateOf<Background?>(value = null)
    val bgChoice: State<Background?> = _bgChoice

    private val _spellList = mutableStateOf<List<ApiListItem>>(emptyList())
    val spellList: State<List<ApiListItem>> = _spellList

    private val _spellChoice = mutableStateOf<Spell?>(value = null)
    val spellChoice: State<Spell?> = _spellChoice

    private val _categories = mutableStateOf<List<ApiListItem>>(emptyList())
    val categories:State<List<ApiListItem>> = _categories

    private val _catChoice = mutableStateOf<ApiListItem?>(value = null)
    val catChoice: State<ApiListItem?> = _catChoice
    fun updateCatChoice(category: ApiListItem) {
        _catChoice.value = category
    }

    private val _catList = mutableStateOf<List<ApiListItem>>(emptyList())
    val catList: State<List<ApiListItem>> = _catList

    private val _itemChoice = mutableStateOf<Item?>(value = null)
    val itemChoice: State<Item?> = _itemChoice

    private val _classFeatures = mutableStateOf<List<ApiListItem>>(emptyList())
    val classFeatures: State<List<ApiListItem>> = _classFeatures

    //Api calls
    private fun getClasses(){
        viewModelScope.launch (Dispatchers.IO){
            _classList.value=dataRepo.getClassList()
        }
    }

    fun GetClass(toGet:String){
        viewModelScope.launch (Dispatchers.IO ){
            _classChoice.value = dataRepo.getClass(toGet)
            _classChoice.value?.let { Log.d("Class Out", it.name) }
            // GetFeaturesList(toGet) // Uncomment when ready
        }
    }

    private fun getRaces(){
        viewModelScope.launch(Dispatchers.IO){
            _raceList.value=dataRepo.getRacesList()
        }
    }

    fun GetRace(toGet:String){
        viewModelScope.launch(Dispatchers.IO){
            _raceChoice.value = dataRepo.getRace(toGet)
        }
    }

    private fun getBackgrounds(){
        viewModelScope.launch(Dispatchers.IO){
            _backgrounds.value = dataRepo.getBGList()
            Log.d("BG Import", "${_backgrounds.value.getOrNull(0)}")
        }
    }

    fun GetBG(toGet: String){
        viewModelScope.launch(Dispatchers.IO){
            _bgChoice.value = dataRepo.getBG(toGet)
            Log.d("BG OUT", "${_bgChoice.value}")
        }
    }

    private fun getSpells(){
        viewModelScope.launch(Dispatchers.IO){
            _spellList.value = dataRepo.getSpellList()
        }
    }

    fun GetSpell(toGet:String){
        viewModelScope.launch(Dispatchers.IO){
            _spellChoice.value = dataRepo.getSpell(toGet)
        }
    }

    private fun getCategories(){
        viewModelScope.launch(Dispatchers.IO){
            _categories.value = dataRepo.getCategories()
        }
    }

    fun GetItemsList(toGet: String){
        viewModelScope.launch(Dispatchers.IO){
            _catList.value = dataRepo.getItemList(toGet)
        }
    }

    fun GetItem(toGet: String){
        viewModelScope.launch(Dispatchers.IO){
            _itemChoice.value = dataRepo.getItem(toGet)
        }
    }

    //Init
    init {
        viewModelScope.launch{
            getClasses()
            getRaces()
            getBackgrounds()
            getSpells()
            getCategories()
        }
    }
}