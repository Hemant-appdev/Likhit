package com.hbworld.likhit.data.repository

import com.hbworld.likhit.data.local.Note
import com.hbworld.likhit.data.local.NoteDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface NotesRepositoryContract {
    suspend fun addNote(note: Note): Long
    fun getAllNotes(): Flow<List<Note>>
}


class NotesRepository @Inject constructor(
    private val noteDao: NoteDao
) : NotesRepositoryContract {
    override suspend fun addNote(note: Note): Long {
        return noteDao.insert(note)
    }

    override fun getAllNotes(): Flow<List<Note>> {
        return noteDao.getAll()
    }


}