package com.example.ddraft.api

import ClassesListResponse
import com.example.ddraft.models.ApiDemoData
import com.example.ddraft.models.SRD.class_Elements.ClassListItem
import retrofit2.http.GET

const val BASE_URL = "https://www.dnd5eapi.co/api/2014/"

interface ApiService {
    @GET("monsters/adult-black-dragon/")
    suspend fun getDemoDragon(): ApiDemoData

    @GET("classes")
    suspend fun getClassesList(): ClassesListResponse
}