package com.example.ddraft.models.SRD.character_Elements

data class Species(
    val name: String,
    val creatureType: String,
    val size: String,
    val speed: Int,
    val features: List<String>
)
