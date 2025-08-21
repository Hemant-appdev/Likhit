package com.hbworld.likhit.domain.usecase

import com.hbworld.likhit.data.local.Note
import com.hbworld.likhit.data.repository.NotesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllNotesUseCase @Inject constructor(
    private val repository: NotesRepository
) {

    fun getAllNotes(): Flow<List<Note>> {
        return repository.getAllNotes()
    }
}