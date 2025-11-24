package com.example.ddraft.models.SRD.character_Elements

data class Background(
    val description: String,
    val feature: List<String> //describes the additions like languge, spells and attributes but doesn't auto add them
)
