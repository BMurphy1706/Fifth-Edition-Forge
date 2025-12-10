package com.example.ddraft.viewModels

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ddraft.R
import com.example.ddraft.models.Character
import com.example.ddraft.models.EquipmentItem
import com.example.ddraft.ui.theme.*
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonNull
import com.google.gson.JsonParser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class PortViewModel : ViewModel() {
    private val gson = Gson()

    private val _statusMessage = MutableStateFlow("")
    val statusMessage: StateFlow<String> = _statusMessage.asStateFlow()

    fun clearMessages() {
        _statusMessage.value = ""
    }

    fun exportCharacter(context: Context, uri: Uri, character: Character) {
        viewModelScope.launch {
            try {
                context.contentResolver.openOutputStream(uri)?.use { outputStream ->
                    OutputStreamWriter(outputStream).use { writer ->
                        val jsonString = gson.toJson(character)
                        writer.write(jsonString)
                        writer.flush()
                    }
                    _statusMessage.value = "Exported: ${character.name}"
                    Log.d("PortViewModel", "Character exported: ${character.name} with ${character.selectedEquipment.size} items")
                } ?: throw IllegalArgumentException("Unable to open output stream.")
            } catch (e: Exception) {
                Log.e("PortViewModel", "Export failed", e)
                _statusMessage.value = "Import failed: ${e.message}"
            }
        }
    }

    fun importCharacter(context: Context, uri: Uri, onCharacterImported: (Character) -> Unit) {
        viewModelScope.launch {
            try {
                context.contentResolver.openInputStream(uri)?.use { inputStream ->
                    val reader = InputStreamReader(inputStream)
                    val jsonElement = JsonParser.parseReader(reader)

                    if (jsonElement == null || jsonElement is JsonNull) {
                        throw IllegalArgumentException("File is empty or invalid JSON.")
                    }

                    if (!jsonElement.isJsonObject) {
                        throw IllegalArgumentException("Invalid JSON format.")
                    }

                    val jsonObject = jsonElement.asJsonObject

                    // Parse equipment array
                    val equipmentList = mutableListOf<EquipmentItem>()
                    val equipmentJson = jsonObject.getAsJsonArray("selectedEquipment")
                    if (equipmentJson != null) {
                        for (equip in equipmentJson) {
                            val equipObj = equip.asJsonObject
                            val item = EquipmentItem(
                                index = equipObj.get("index")?.asString ?: "",
                                name = equipObj.get("name")?.asString ?: "",
                                url = equipObj.get("url")?.asString ?: "",
                                quantity = equipObj.get("quantity")?.asInt ?: 1
                            )
                            equipmentList.add(item)
                        }
                    }

                    val importedChar = Character(
                        name = jsonObject.get("name")?.asString ?: "Imported Character",
                        raceName = jsonObject.get("raceName")?.asString ?: "",
                        className = jsonObject.get("className")?.asString ?: "",
                        backgroundName = jsonObject.get("backgroundName")?.asString ?: "",
                        alignment = jsonObject.get("alignment")?.asString ?: "",
                        level = jsonObject.get("level")?.asInt ?: 1,
                        strength = jsonObject.get("strength")?.asInt ?: 8,
                        dexterity = jsonObject.get("dexterity")?.asInt ?: 8,
                        constitution = jsonObject.get("constitution")?.asInt ?: 8,
                        intelligence = jsonObject.get("intelligence")?.asInt ?: 8,
                        wisdom = jsonObject.get("wisdom")?.asInt ?: 8,
                        charisma = jsonObject.get("charisma")?.asInt ?: 8,
                        goldPieces = jsonObject.get("goldPieces")?.asString ?: "0",
                        silverPieces = jsonObject.get("silverPieces")?.asString ?: "0",
                        copperPieces = jsonObject.get("copperPieces")?.asString ?: "0",
                        selectedEquipment = equipmentList,
                        iconRes = jsonObject.get("iconRes")?.asInt ?: R.drawable.search,
                        lightColor = Color(jsonObject.get("lightColorArgb")?.asInt ?: OrangeB.toArgb()),
                        mediumColor = Color(jsonObject.get("mediumColorArgb")?.asInt ?: OrangeM.toArgb()),
                        darkColor = Color(jsonObject.get("darkColorArgb")?.asInt ?: OrangeD.toArgb())
                    )

                    onCharacterImported(importedChar)
                    _statusMessage.value = "Imported: ${importedChar.name}"
                    Log.d("PortViewModel", "Character imported: ${importedChar.name} with ${importedChar.selectedEquipment.size} items")
                } ?: throw IllegalArgumentException("Unable to open input stream.")
            } catch (e: Exception) {
                Log.e("PortViewModel", "Import failed", e)
                _statusMessage.value = "Import failed: ${e.message}"
            }
        }
    }
}