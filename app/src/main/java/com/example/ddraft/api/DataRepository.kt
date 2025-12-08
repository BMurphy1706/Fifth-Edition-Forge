package com.example.ddraft.api

import android.util.Log
import com.example.ddraft.api.responses.ApiListItem
import com.example.ddraft.models.ApiDemoData
import com.example.ddraft.models.SRD.character_Elements.Background
import com.example.ddraft.models.SRD.character_Elements.Race
import com.example.ddraft.models.SRD.class_Elements.Class
import com.example.ddraft.models.SRD.item_Elements.Item
import com.example.ddraft.models.SRD.spell_Elements.Spell
import javax.inject.Inject

class DataRepository @Inject constructor(
    private val apiService: ApiService
): DataRepositoryImpl {

    override suspend fun getDemoDragon(): List<ApiDemoData> {
        return try {
            listOf(apiService.getDemoDragon())
        }catch (e: Exception){
            Log.d("Api error", "${e.message}")
            emptyList()
        }
    }

    override suspend fun getClassList(): List<ApiListItem> {
       return try{
           apiService.getClassesList().results
       }catch (e: Exception){
           Log.d("Api error", "${e.message}}")
           emptyList()
       }
    }

    override suspend fun getClass(toGet: String): Class?{
        return try{
            apiService.getClass(toGet)
        }catch (e: Exception){
            Log.d("Api error", "${e.message}")
            null
        }
    }

    override suspend fun getLevels(toGet: String): List<ApiListItem> {
       return try{
           apiService.getLevels(toGet).results
       }catch (e: Exception) {
           Log.d("Api error", "${e.message}")
           emptyList()
       }
    }

    override suspend fun getRacesList(): List<ApiListItem> {
       return try{
           apiService.getRacesList().results
       }catch (e: Exception){
           Log.d("Api error", "${e.message}")
           emptyList()
       }
    }

    override suspend fun getRace(toGet: String): Race? {
       return try {
           apiService.getRace(toGet)
       }catch (e: Exception){
           Log.d("Api error", "${e.message}")
           null
       }
    }

    override suspend fun getBGList(): List<ApiListItem> {
       return try{
           apiService.getBGList().results
       }catch (e: Exception){
           Log.d("Api errro", "${e.message}")
           emptyList()
       }
    }

    override suspend fun getBG(toGet: String): Background? {
       return try{
           apiService.getBG(toGet)
       }catch (e:Exception){
           Log.d("Api error", "${e.message}")
           null
       }
    }

    override suspend fun getSpellList(): List<ApiListItem> {
       return try{
           apiService.getSpellList().results
       }catch (e:Exception){
           Log.d("Api error", "${e.message}")
           emptyList()
       }
    }

    override suspend fun getSpell(toGet: String): Spell? {
       return try {
           apiService.getSpell(toGet)
       }catch (e:Exception){
           Log.d("Api error", "${e.message}")
           null
       }
    }

    override suspend fun getCategories(): List<ApiListItem> {
       return try{
           apiService.getCategories().results
       }catch (e:Exception){
           Log.d("Api error", "${e.message}")
           emptyList()
       }
    }

    override suspend fun getItemList(toGet: String): List<ApiListItem> {
        return try{
            apiService.getItemList(toGet).results
        }catch (e:Exception){
            Log.d("Api error", "${e.message}")
            emptyList()
        }
    }

    override suspend fun getItem(toGet: String): Item? {
       return try {
           apiService.getItem(toGet)
       }catch (e:Exception){
           Log.d("Api error", "${e.message}")
           null
       }
    }
}