package com.hbworld.likhit.app

import android.content.Context
import androidx.room.Room
import com.hbworld.likhit.data.local.NoteDao
import com.hbworld.likhit.data.local.NoteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideNotesDatabase(@ApplicationContext context: Context): NoteDatabase {
        return Room.databaseBuilder(
            context,
            NoteDatabase::class.java, "likhit-database"
        ).build()
    }

    @Provides
    @Singleton
    fun providesNoteDao(noteDatabase: NoteDatabase): NoteDao {
        return noteDatabase.getNoteDao()
    }

}