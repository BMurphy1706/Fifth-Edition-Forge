package com.example.ddraft.api

import com.example.ddraft.models.ApiDemoData
import com.example.ddraft.models.SRD.class_Elements.Class
import com.example.ddraft.models.SRD.class_Elements.ClassListItem

interface DataRepositoryImpl {
    suspend fun getDemoDragon(): List<ApiDemoData>
    suspend fun getClassList(): List<ClassListItem>
    suspend fun getClass(toGet: String): Class?
}