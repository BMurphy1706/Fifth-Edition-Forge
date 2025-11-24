package com.example.ddraft.models.SRD.character_Elements

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color

data class Character(
    val name: String,
    @DrawableRes val iconId: Int,
    //Move these to class when done
    val lightColor: Color,
    val mediumColor: Color,
    val darkColor: Color,

    //instance of a given class with relevant choices
    /*val background: Background,
    val alignment: Alignment,
    val money: List<Int>, //bp, sp, gp
    val species: Species,
    val scores: List<AbilityScore>,
    val inventory: List<Item>*/
)
