package com.example.ddraft.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositeShader
import com.example.ddraft.R

//Class colours
// Barbarian - Orange tones (Amber Knight)
val BarbarianLight = Color(0xFFFFB550)
val BarbarianMedium = Color(0xFFFF7A30)
val BarbarianDark = Color(0xFFCC4A1F)

// Bard - Purple tones (Violet Dreamer)
val BardLight = Color(0xFFD1A3FF)
val BardMedium = Color(0xFF9C27B0)
val BardDark = Color(0xFF6A0080)

// Cleric - Bright Holy Light (Golden Radiance)
val ClericLight = Color(0xFFFFF3A1)   // Soft golden glow
val ClericMedium = Color(0xFFFFD700)  // Pure gold
val ClericDark = Color(0xFFB8860B)    // Deep golden amber

// Druid - Green tones (Verdant Sage)
val DruidLight = Color(0xFF81D97B)
val DruidMedium = Color(0xFF4CAF50)
val DruidDark = Color(0xFF2E7D32)

// Fighter - Strong Orange-Gold
val FighterLight = Color(0xFFFFD580)
val FighterMedium = Color(0xFFFFAC1C)
val FighterDark = Color(0xFFCC5500)

// Monk - Red tones (Crimson Dawn)
val MonkLight = Color(0xFF80D8FF)
val MonkMedium = Color(0xFF26C6DA)
val MonkDark = Color(0xFF00695C)

// Paladin - Gold tones
val PaladinLight = Color(0xFFFFC000)
val PaladinMedium = Color(0xFFF4BB44)
val PaladinDark = Color(0xFFB87333)

// Ranger - Dark Green (Verdant Sage dark)
val RangerLight = Color(0xFF7CFC00)
val RangerMedium = Color(0xFF4CAF50)
val RangerDark = Color(0xFF2E7D32)

// Rogue - Orange-Red shades
val RogueLight = Color(0xFFFF7F50)
val RogueMedium = Color(0xFFFF4433)
val RogueDark = Color(0xFFB22222)

// Sorcerer - Red-Purple mix
val SorcererLight = Color(0xFFF88379)
val SorcererMedium = Color(0xFFE53935)
val SorcererDark = Color(0xFF6A0080)

// Warlock - Purple tones (Violet Dreamer)
val WarlockLight = Color(0xFFBA55D3)
val WarlockMedium = Color(0xFF9C27B0)
val WarlockDark = Color(0xFF6A0080)

// Wizard - Blue tones (Azure Whisper)
val WizardLight = Color(0xFFADD8E6)
val WizardMedium = Color(0xFF2196F3)
val WizardDark = Color(0xFF0B5BC5)

// In ui.theme file - extend ClassColors
object ClassTheme {
    data class ClassTheme(
        val light: Color,
        val medium: Color,
        val dark: Color,
        val iconRes: Int? = 0  // New field!
    )

    private val colorMap = mapOf(
        "Barbarian" to ClassTheme(BarbarianLight, BarbarianMedium, BarbarianDark, R.drawable.barbarian),
        "Bard" to ClassTheme(BardLight, BardMedium, BardDark, R.drawable.bard),
        "Cleric" to ClassTheme(ClericLight, ClericMedium, ClericDark, R.drawable.cleric),
        "Druid" to ClassTheme(DruidLight, DruidMedium, DruidDark, R.drawable.druid),
        "Fighter" to ClassTheme(FighterLight, FighterMedium, FighterDark, R.drawable.fighter),
        "Monk" to ClassTheme(MonkLight, MonkMedium, MonkDark, R.drawable.monk),
        "Paladin" to ClassTheme(PaladinLight, PaladinMedium, PaladinDark, R.drawable.paladin),
        "Ranger" to ClassTheme(RangerLight, RangerMedium, RangerDark, R.drawable.ranger),
        "Rogue" to ClassTheme(RogueLight, RogueMedium, RogueDark, R.drawable.rogue),
        "Sorcerer" to ClassTheme(SorcererLight, SorcererMedium, SorcererDark, R.drawable.sorcerer),
        "Warlock" to ClassTheme(WarlockLight, WarlockMedium, WarlockDark, R.drawable.warlock),
        "Wizard" to ClassTheme(WizardLight, WizardMedium, WizardDark, R.drawable.wizard)
    )

    fun getTheme(className: String?): ClassTheme? = className?.let { colorMap[it] }
}


//Other colours
val LightBlack = Color(0xFF2C2D2D)

val OrangeB = Color(0xFFFFB550)
val OrangeM = Color(0xFFFF7A30)
val OrangeD = Color(0xFFCC4A1F)

val DeepBlue = Color(0XFF06164C)
val MidnightBlue = Color(0xFF272757)

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)