package com.example.ddraft.viewModels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ddraft.api.DataRepository
import com.example.ddraft.models.SRD.class_Elements.ClassListItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgeViewModel @Inject constructor(
    private val dataRepo: DataRepository
):ViewModel(){

    private val _selectedStep = mutableIntStateOf(0)
    val selectedStep: State<Int> = _selectedStep
    fun onStepChange(newStep: Int){_selectedStep.intValue = newStep}

    //Api calls
    private val _classList = mutableStateOf<List<ClassListItem>>(emptyList())
    val classList: State<List<ClassListItem>> = _classList

    private fun getClasses(){
        viewModelScope.launch (Dispatchers.IO){
            _classList.value=dataRepo.getClassList()
        }
    }

    init {
        getClasses()
    }

    //Character basics
    private val _name = mutableStateOf("My New Character")
    val name: State<String> = _name
    fun onNameChange(newName: String){_name.value = newName}


}