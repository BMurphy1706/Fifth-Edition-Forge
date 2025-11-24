package com.example.ddraft.models.SRD.item_Elements

class Weapon(
    name: String,
    description: String,
    weight: Float,
    costAmount: Int,
    costType: String,

    range: List<Int>, //normal, long
    weaponCat: String,
    weaponRange: String,
    dmgType: String,
    dmg: String

):Item(name, description, weight, costAmount, costType)