package com.example.ddraft.api

import android.util.Log
import com.example.ddraft.models.ApiDemoData
import com.example.ddraft.models.SRD.class_Elements.Class
import com.example.ddraft.models.SRD.class_Elements.ClassListItem
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

    override suspend fun getClassList(): List<ClassListItem> {
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
}