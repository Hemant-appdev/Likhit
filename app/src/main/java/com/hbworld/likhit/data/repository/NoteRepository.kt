package com.hbworld.likhit.data.repository

import com.hbworld.likhit.data.local.NoteEntity
import com.hbworld.likhit.data.local.NoteDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface NoteRepositoryContract {
    suspend fun addNote(title: String, description: String): Long
    fun getAllNotes(): Flow<List<NoteEntity>>
    suspend fun getNoteById(noteId: Long): NoteEntity
}


class NoteRepository @Inject constructor(
    private val noteDao: NoteDao
) : NoteRepositoryContract {
    override suspend fun addNote(title: String, description: String): Long {
        return noteDao.insert(
            NoteEntity(
                title = title,
                description = description,
                updatedAt = System.currentTimeMillis()
            )
        )
    }

    override fun getAllNotes(): Flow<List<NoteEntity>> {
        return noteDao.getAll()
    }

    override suspend fun getNoteById(noteId: Long): NoteEntity {
        return noteDao.getNoteById(noteId)
    }
}