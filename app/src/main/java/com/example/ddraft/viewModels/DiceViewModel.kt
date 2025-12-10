package com.example.ddraft.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlin.random.Random

@HiltViewModel
class DiceViewModel @Inject constructor() : ViewModel() {

    // Dice selection (d20 default)
    private val _selectedDie = mutableIntStateOf(20)
    val selectedDie: State<Int> = _selectedDie
    fun setDie(sides: Int) { _selectedDie.intValue = sides }

    // Roll mode
    enum class RollMode { NORMAL, ADVANTAGE, DISADVANTAGE }
    private val _rollMode = mutableStateOf(RollMode.NORMAL)
    val rollMode: State<RollMode> = _rollMode
    fun setRollMode(mode: RollMode) { _rollMode.value = mode }

    // Roll results
    private val _rollResult1 = mutableIntStateOf(0)
    val rollResult1: State<Int> = _rollResult1

    private val _rollResult2 = mutableIntStateOf(0)
    val rollResult2: State<Int> = _rollResult2

    private val _finalResult = mutableIntStateOf(0)
    val finalResult: State<Int> = _finalResult

    // Roll history
    private val _rollHistory = mutableStateOf<List<RollResult>>(emptyList())
    val rollHistory: State<List<RollResult>> = _rollHistory

    fun rollDice() {
        val roll1 = Random.nextInt(1, _selectedDie.intValue + 1)
        val roll2 = if (_rollMode.value != RollMode.NORMAL) {
            Random.nextInt(1, _selectedDie.intValue + 1)
        } else 0

        _rollResult1.intValue = roll1
        _rollResult2.intValue = roll2

        val final = when (_rollMode.value) {
            RollMode.NORMAL -> roll1
            RollMode.ADVANTAGE -> maxOf(roll1, roll2)
            RollMode.DISADVANTAGE -> minOf(roll1, roll2)
        }

        // FIXED: Direct access to mutable backing field
        _finalResult.intValue = final

        // Add to history (keep last 10 rolls)
        _rollHistory.value = listOf(
            RollResult(
                die = _selectedDie.intValue,
                mode = _rollMode.value,
                rolls = listOf(roll1, roll2).filter { it > 0 },
                result = final
            )
        ) + _rollHistory.value.take(10)
    }

    fun clearHistory() {
        _rollHistory.value = emptyList()
    }
}

data class RollResult(
    val die: Int,
    val mode: DiceViewModel.RollMode,
    val rolls: List<Int>,
    val result: Int
)
