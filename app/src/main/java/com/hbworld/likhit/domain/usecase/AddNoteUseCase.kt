package com.hbworld.likhit.domain.usecase

import com.hbworld.likhit.data.local.Note
import com.hbworld.likhit.data.repository.NoteRepository
import javax.inject.Inject

class AddNoteUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    internal suspend fun addNote(title: String, description: String): Long {
        return repository.addNote(
            Note(
                title = title,
                description = description,
                updatedAt = System.currentTimeMillis(),
                id = null
            )
        )
    }


}