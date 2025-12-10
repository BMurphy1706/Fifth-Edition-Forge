package com.example.ddraft.db

import androidx.room.TypeConverter
import com.example.ddraft.models.EquipmentItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object Converters {
    private val gson = Gson()

    @TypeConverter
    @JvmStatic
    fun fromEquipmentList(list: List<EquipmentItem>?): String? {
        if (list == null) return null
        val type = object : TypeToken<List<EquipmentItem>>() {}.type
        return gson.toJson(list, type)
    }

    @TypeConverter
    @JvmStatic
    fun toEquipmentList(json: String?): List<EquipmentItem> {
        if (json.isNullOrEmpty()) return emptyList()
        val type = object : TypeToken<List<EquipmentItem>>() {}.type
        return gson.fromJson(json, type) ?: emptyList()
    }
}