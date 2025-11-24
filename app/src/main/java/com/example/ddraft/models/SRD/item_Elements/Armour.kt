package com.example.ddraft.models.SRD.item_Elements

class Armour(
    name: String,
    description: String,
    weight: Float,
    costAmount: Int,
    costType: String,

    armourCat: String,
    ac: Int,
    dexBonus: Boolean

):Item(name, description, weight, costAmount, costType)
