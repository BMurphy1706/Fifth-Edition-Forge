package com.example.ddraft.api

import com.example.ddraft.api.responses.ApiRef
import com.example.ddraft.models.ApiDemoData
import com.example.ddraft.models.SRD.spell_Elements.Spell
import com.example.ddraft.models.SRD.character_Elements.Background
import com.example.ddraft.models.SRD.character_Elements.Race
import com.example.ddraft.models.SRD.class_Elements.Class
import com.example.ddraft.models.SRD.item_Elements.Item
import retrofit2.http.GET
import retrofit2.http.Path

const val BASE_URL = "https://www.dnd5eapi.co/api/2014/"

interface ApiService {
    @GET("monsters/adult-black-dragon/")
    suspend fun getDemoDragon(): ApiDemoData

    @GET("classes")
    suspend fun getClassesList(): ApiRef

    @GET("classes/{toGet}")
    suspend fun getClass(@Path("toGet") toGet:String): Class?
    /*
    @GET("classes/{toGet}/levels")
    suspend fun getLevels(@Path("toGet") toGet:String): ApiRef

    @GET("races")
    suspend fun getRacesList(): ApiRef

    @GET("races/{toGet}")
    suspend fun getRace(@Path("toGet") toGet:String): Race?

    @GET("backgrounds")
    suspend fun getBGList(): ApiRef

    @GET("backgrounds/{toGet}")
    suspend fun getBG(@Path("toGet") toGet:String): Background?

    @GET("spells")
    suspend fun getSpellsList(): ApiRef

    @GET("spells/{toGet}")
    suspend fun getSpell(@Path("toGet") toGet:String): Spell?

    @GET("equipment-categories")
    suspend fun getCategories(): ApiRef

    @GET("equipment-categories/{toGet}")
    suspend fun getItemList(@Path("toGet") toGet:String): ApiRef

    @GET("equipment/{toGet}")
    suspend fun getItem(@Path("toGet") toGet:String): Item?
     */
}