package com.example.ddraft.viewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ddraft.api.DataRepository
import com.example.ddraft.models.ApiDemoData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.compose.runtime.State
import com.example.ddraft.models.SRD.class_Elements.ClassListItem


@HiltViewModel
class DemoVM @Inject constructor(
    private val demoRepository: DataRepository
): ViewModel() {

    private val _postList = mutableStateOf<List<ApiDemoData>>(emptyList())
    val demoList: State<List<ApiDemoData>> = _postList

    private val _classList = mutableStateOf<List<ClassListItem>>(emptyList())
    val classList: State<List<ClassListItem>> = _classList

    init {
        getDemo()
    }

    private fun getDemo(){
        viewModelScope.launch(Dispatchers.IO) {
            _postList.value = demoRepository.getDemoDragon()
            _classList.value = demoRepository.getClassList()
        }
    }
}