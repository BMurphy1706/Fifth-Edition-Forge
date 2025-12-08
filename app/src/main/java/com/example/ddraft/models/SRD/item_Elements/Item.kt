package com.example.ddraft.models.SRD.item_Elements

data class Item(
    val index: String,
    val name: String,
    val desc: List<String>,
    val weight: Double?,
    val cost: Cost
)