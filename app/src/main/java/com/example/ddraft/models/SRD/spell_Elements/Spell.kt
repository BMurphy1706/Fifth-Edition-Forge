package com.example.ddraft.models.SRD.spell_Elements

data class Spell(
    val index: String,
    val name: String,
    val desc: List<String>,
    val level: Int,
    val school: School,
    val classes: List<ClassRef>,
    val casting_time: String,
    val range: String,
    val duration: String,
    val concentration: Boolean
)

data class School(
    val index: String,
    val name: String,
    val url: String
)

data class ClassRef(
    val name: String,
    val url: String
)

