package com.example.ddraft.models.SRD.character_Elements

import com.example.ddraft.models.EquipmentItem
import com.example.ddraft.models.SRD.character_Elements.common.Language
import com.example.ddraft.models.SRD.character_Elements.common.Proficiency

data class Background(
    val index: String,
    val name: String,
    val desc: List<String>,
    val skill_proficiencies: List<Proficiency>,
    val languages: List<Language>,
    val starting_equipment: List<EquipmentItem>
)