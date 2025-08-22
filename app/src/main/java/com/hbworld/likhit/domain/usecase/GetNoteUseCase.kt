package com.hbworld.likhit.domain.usecase

import com.hbworld.likhit.data.local.Note
import com.hbworld.likhit.data.repository.NoteRepository
import javax.inject.Inject

class GetNoteUseCase @Inject constructor(
    private val noteRepository: NoteRepository
) {

    suspend fun getNoteById(noteId: Long): Note {
        return noteRepository.getNoteById(noteId)
    }


}