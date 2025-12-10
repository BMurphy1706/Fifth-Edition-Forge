package com.example.ddraft.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.ddraft.models.Character
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDAO {

    @Insert
    suspend fun insertCharacter(character: Character)

    @Delete
    suspend fun deleteCharacter(character: Character)

    @Query("SELECT * FROM characters ORDER BY className ASC")
    fun getCharacters(): Flow<List<Character>>
}