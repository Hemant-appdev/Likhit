package com.hbworld.likhit.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao{

    @Insert
    suspend fun insert(note: Note): Long

    @Query("SELECT * FROM Note")
    fun getAll(): Flow<List<Note>>
}