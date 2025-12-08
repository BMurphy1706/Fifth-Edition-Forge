package com.example.ddraft.models.SRD.item_Elements

data class Item(
    val index: String,
    val name: String,
    val desc: List<String>,
    val weight: Double?,
    val cost: Cost,

    // Weapon fields
    val damage: Damage?,
    val properties: List<PropertyRef>?,
    val weapon_range: String?,

    // Armor fields
    val armor_category: String?,
    val armor_class: ArmorClass?
)

data class Damage(
    val damage_dice: String,
    val damage_type: DamageType?
)

data class DamageType(
    val index: String,
    val name: String,
    val url: String
)

data class PropertyRef(
    val index: String,
    val name: String,
    val url: String
)

data class ArmorClass(
    val base: Int,
    val dex_bonus: Boolean?
)