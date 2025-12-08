package com.example.ddraft.api

import com.example.ddraft.api.responses.ApiListItem
import com.example.ddraft.models.ApiDemoData
import com.example.ddraft.models.SRD.class_Elements.Class

interface DataRepositoryImpl {
    suspend fun getDemoDragon(): List<ApiDemoData>
    suspend fun getClassList(): List<ApiListItem>
    suspend fun getClass(toGet: String): Class?
}