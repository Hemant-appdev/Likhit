package com.hbworld.likhit.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert
    suspend fun insert(noteEntity: NoteEntity): Long

    @Query("SELECT * FROM NoteEntity")
    fun getAll(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM NoteEntity WHERE id = :noteId")
    suspend fun getNoteById(noteId: Long): NoteEntity
}