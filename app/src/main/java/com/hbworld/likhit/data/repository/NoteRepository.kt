package com.hbworld.likhit.data.repository

import com.hbworld.likhit.data.local.Note
import com.hbworld.likhit.data.local.NoteDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface NoteRepositoryContract {
    suspend fun addNote(note: Note): Long
    fun getAllNotes(): Flow<List<Note>>
    suspend fun getNoteById(noteId: Long): Note
}


class NoteRepository @Inject constructor(
    private val noteDao: NoteDao
) : NoteRepositoryContract {
    override suspend fun addNote(note: Note): Long {
        return noteDao.insert(note)
    }

    override fun getAllNotes(): Flow<List<Note>> {
        return noteDao.getAll()
    }

    override suspend fun getNoteById(noteId: Long): Note {
        return noteDao.getNoteById(noteId)
    }
}