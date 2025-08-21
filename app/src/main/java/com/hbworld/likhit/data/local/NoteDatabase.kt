package com.hbworld.likhit.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hbworld.likhit.data.local.NoteDao
import com.hbworld.likhit.data.local.Note

@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun getNoteDao(): NoteDao
}