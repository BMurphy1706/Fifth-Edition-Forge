package com.example.ddraft.di

import android.content.Context
import androidx.room.Room
import com.example.ddraft.db.CharacterDAO
import com.example.ddraft.db.CharacterDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): CharacterDB =
        Room.databaseBuilder(
            context,
            CharacterDB::class.java,
            "character_db"
        ).build()

    @Provides
    fun provideCharacterDao(db: CharacterDB): CharacterDAO = db.dao
}
