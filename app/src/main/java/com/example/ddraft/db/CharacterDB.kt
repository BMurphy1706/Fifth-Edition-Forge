package com.example.ddraft.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.ddraft.models.Character

@Database(
    entities = [Character::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class CharacterDB : RoomDatabase() {

    abstract val dao: CharacterDAO
}
