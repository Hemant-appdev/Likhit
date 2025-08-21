package com.hbworld.likhit.data.repository

import com.hbworld.likhit.data.local.Note
import com.hbworld.likhit.data.local.NoteDatabase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface NotesRepositoryContract {
    suspend fun addNote(note: Note): Int
    suspend fun getAllNotes(): Flow<List<Note>>
}


class NotesRepository @Inject constructor(
    val database: NoteDatabase
) : NotesRepositoryContract {
    override suspend fun addNote(note: Note): Int {
        return database.getNoteDao().insert(note)
    }

    override suspend fun getAllNotes(): Flow<List<Note>> {
        return database.getNoteDao().getAll()
    }


}