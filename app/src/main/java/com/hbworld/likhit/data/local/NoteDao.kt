package com.hbworld.likhit.data.local

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao{

    suspend fun insert(note: Note): Int

    @Query("SELECT * FROM Note")
    suspend fun getAll(): Flow<List<Note>>
}