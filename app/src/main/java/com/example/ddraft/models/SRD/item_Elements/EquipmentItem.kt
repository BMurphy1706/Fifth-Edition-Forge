package com.example.ddraft.models

import com.google.gson.annotations.SerializedName

data class EquipmentItem(
    @SerializedName("index")
    val index: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("url")
    val url: String,

    @SerializedName("quantity")
    val quantity: Int
)