package com.example.ddraft.models.SRD.class_Elements

import com.example.ddraft.models.SRD.character_Elements.common.AbilityScore
import com.example.ddraft.models.SRD.character_Elements.common.Proficiency

data class Class(
    val index: String,
    val name: String,
    val hit_die: Int,
    val proficiencies: List<Proficiency>,
    val saving_throws: List<AbilityScore>,
    val subclasses: List<Subclass>,
    val features: List<ClassFeature>? = emptyList()
)

data class Subclass(
    val index: String,
    val name: String,
    val url: String
)
