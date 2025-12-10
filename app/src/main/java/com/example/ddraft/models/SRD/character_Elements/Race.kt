package com.example.ddraft.models.SRD.character_Elements

import com.example.ddraft.models.SRD.character_Elements.common.AbilityBonus
import com.example.ddraft.models.SRD.character_Elements.common.AbilityScore
import com.example.ddraft.models.SRD.character_Elements.common.Proficiency

data class Race(
    val index: String,
    val name: String,
    val type: String,
    val size: String,
    val speed: Int,
    val ability_bonuses: List<AbilityBonus>,
    val traits: List<Proficiency>
)