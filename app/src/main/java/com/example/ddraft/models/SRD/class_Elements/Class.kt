package com.example.ddraft.models.SRD.class_Elements

import com.example.ddraft.models.SRD.character_Elements.Skill

data class Class(
    val name: String,
    val lv: Int,
    val hitDie: Int,

    //proficiencies
    val profFrom: List<Skill>,
    val profCount: Int,

)
