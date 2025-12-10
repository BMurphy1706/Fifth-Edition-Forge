package com.example.ddraft.api

import com.example.ddraft.api.responses.ApiListItem
import com.example.ddraft.api.responses.ApiRef
import com.example.ddraft.models.ApiDemoData
import com.example.ddraft.models.SRD.character_Elements.Background
import com.example.ddraft.models.SRD.character_Elements.Race
import com.example.ddraft.models.SRD.class_Elements.Class
import com.example.ddraft.models.SRD.class_Elements.ClassFeature
import com.example.ddraft.models.SRD.item_Elements.Item
import com.example.ddraft.models.SRD.spell_Elements.Spell

interface DataRepositoryImpl {
    suspend fun getDemoDragon(): List<ApiDemoData>
    suspend fun getClassList(): List<ApiListItem>
    suspend fun getClass(toGet: String): Class?
    suspend fun getLevels(toGet: String): List<ApiListItem>
    suspend fun getRacesList(): List<ApiListItem>
    suspend fun getRace(toGet: String): Race?
    suspend fun getBGList(): List<ApiListItem>
    suspend fun getBG(toGet: String): Background?
    suspend fun getSpellList(): List<ApiListItem>
    suspend fun getSpell(toGet: String): Spell?
    suspend fun getCategories(): List<ApiListItem>
    suspend fun getItemList(toGet: String): List<ApiListItem>
    suspend fun getItem(toGet: String): Item?
    suspend fun getFeature(toGet: String): ClassFeature?
}