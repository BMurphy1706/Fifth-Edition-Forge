package com.example.ddraft.models.SRD.class_Elements

import com.example.ddraft.api.responses.ApiRef

data class ClassLevel(
    val level: Int,
    val ability_score_improvement: Boolean,
    val class_features: List<ApiRef>,
    val features: List<ApiRef>,
    val proficiency_bonus: Int,
    //val spellcasting: Spellcasting?
)
