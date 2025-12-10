package com.example.ddraft.models

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "characters")
data class Character(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val name: String = "My New Character",
    val raceName: String = "",
    val className: String = "",
    val backgroundName: String = "",
    val alignment: String = "",
    val level: Int = 1,

    // Ability Scores
    val strength: Int = 8,
    val dexterity: Int = 8,
    val constitution: Int = 8,
    val intelligence: Int = 8,
    val wisdom: Int = 8,
    val charisma: Int = 8,

    // Currency
    val goldPieces: String = "0",
    val silverPieces: String = "0",
    val copperPieces: String = "0",

    // Equipment - Changed to EquipmentItem
    @SerializedName("selectedEquipment")
    val selectedEquipment: List<EquipmentItem> = emptyList(),

    //UI
    val iconRes: Int,
    @SerializedName("lightColorArgb")
    val lightColorArgb: Int,
    @SerializedName("mediumColorArgb")
    val mediumColorArgb: Int,
    @SerializedName("darkColorArgb")
    val darkColorArgb: Int
) {
    val lightColor: Color get() = Color(lightColorArgb)
    val mediumColor: Color get() = Color(mediumColorArgb)
    val darkColor: Color get() = Color(darkColorArgb)

    constructor(
        id: Int = 0,
        name: String = "My New Character",
        raceName: String = "",
        className: String = "",
        backgroundName: String = "",
        alignment: String = "",
        level: Int = 1,
        strength: Int = 8,
        dexterity: Int = 8,
        constitution: Int = 8,
        intelligence: Int = 8,
        wisdom: Int = 8,
        charisma: Int = 8,
        goldPieces: String = "0",
        silverPieces: String = "0",
        copperPieces: String = "0",
        selectedEquipment: List<EquipmentItem> = emptyList(),
        iconRes: Int = 0,
        lightColor: Color = Color.Unspecified,
        mediumColor: Color = Color.Unspecified,
        darkColor: Color = Color.Unspecified
    ) : this(
        id,name, raceName, className, backgroundName, alignment, level,
        strength, dexterity, constitution, intelligence, wisdom, charisma,
        goldPieces, silverPieces, copperPieces, selectedEquipment,
        iconRes, lightColor.toArgb(), mediumColor.toArgb(), darkColor.toArgb()
    )
}