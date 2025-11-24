package com.example.ddraft.models

data class ApiDemoData(
    val index: String,
    val name: String,
    val size: String,
    val type: String,
    val alignment: String,
    val armor_class: List<ArmorClass>,
    val hit_points: Int,
    val hit_dice: String,
    val hit_points_roll: String,
    val speed: Speed,
    val strength: Int,
    val dexterity: Int,
    val constitution: Int,
    val intelligence: Int,
    val wisdom: Int,
    val charisma: Int,
    val proficiencies: List<ProficiencyWrapper>,
    val damage_vulnerabilities: List<String>,
    val damage_resistances: List<String>,
    val damage_immunities: List<String>,
    val condition_immunities: List<String>,
    val senses: Senses,
    val languages: String,
    val challenge_rating: Int,
    val proficiency_bonus: Int,
    val xp: Int,
    val special_abilities: List<SpecialAbility>,
    val actions: List<Action>,
    val legendary_actions: List<LegendaryAction>,
    val image: String,
    val url: String,
    val updated_at: String,
    val forms: List<Any>,
    val reactions: List<Any>
)

data class ArmorClass(
    val type: String,
    val value: Int
)

data class Speed(
    val walk: String,
    val fly: String?,
    val swim: String?
)

data class ProficiencyWrapper(
    val value: Int,
    val proficiency: Proficiency
)

data class Proficiency(
    val index: String,
    val name: String,
    val url: String
)

data class Senses(
    val blindsight: String,
    val darkvision: String,
    val passive_perception: Int
)

data class SpecialAbility(
    val name: String,
    val desc: String,
    val damage: List<Any> // Could be further detailed if needed
)

data class Action(
    val name: String,
    val desc: String,
    val multiattack_type: String?,
    val damage: List<Damage>,
    val actions: List<SubAction>?,
    val attack_bonus: Int?,
    val dc: DC?
)

data class Damage(
    val damage_type: DamageType,
    val damage_dice: String
)

data class DamageType(
    val index: String,
    val name: String,
    val url: String
)

data class SubAction(
    val action_name: String,
    val count: String,
    val type: String
)

data class DC(
    val dc_type: DCType,
    val dc_value: Int,
    val success_type: String
)

data class DCType(
    val index: String,
    val name: String,
    val url: String
)

data class LegendaryAction(
    val name: String,
    val desc: String,
    val damage: List<Damage>,
    val dc: DC?
)
