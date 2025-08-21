package com.hbworld.likhit.app

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hbworld.likhit.data.local.NoteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface AppModule {

    companion object {

        @Provides
        fun provideNotesDatabase(@ApplicationContext context: Context): RoomDatabase {
            return Room.databaseBuilder(
                context,
                NoteDatabase::class.java, "likhit-database"
            ).build()
        }
    }
}