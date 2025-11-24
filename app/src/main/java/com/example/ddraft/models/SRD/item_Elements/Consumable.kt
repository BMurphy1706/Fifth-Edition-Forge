package com.example.ddraft.models.SRD.item_Elements

import com.example.ddraft.viewModels.SharedVM

class Consumable(
    name: String,
    description: String,
    weight: Float,
    costAmount: Int,
    costType: String,
): Item(name,description, weight, costAmount, costType)